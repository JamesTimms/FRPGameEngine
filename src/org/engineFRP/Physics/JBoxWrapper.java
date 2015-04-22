package org.engineFRP.Physics;

import org.engineFRP.rendering.Mesh;
import org.engineFRP.FRP.FRPTime;
import org.engineFRP.Util.Util;
import org.engineFRP.core.GameObject;
import org.engineFRP.core.Scene;
import org.engineFRP.maths.Vector3f;
import org.engineFRP.rendering.MeshUtil;
import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.*;
import sodium.Listener;
import sodium.Stream;
import sodium.StreamSink;

import java.util.ArrayList;

/**
 * Created by TekMaTek on 04/04/2015.
 */
public class JBoxWrapper {

    private static final Vec2 gravity = new Vec2(0.0f, -9.8f);
    public static final World world = new World(gravity);
    private static ArrayList<JBoxWrapper> allWrappers = new ArrayList<>();
    private final StreamSink<Body> jboxUpdateStream = new StreamSink<>();//Really inefficient?
    public Body body;
    public GameObject go;
    private static ArrayList<Listener> l = new ArrayList<>();//Listeners are bad here but is needed because no other way to update JBox when our transform moves.
    private static JBoxCollisionListener lis = new JBoxCollisionListener();
    private static ArrayList<Body> bodiesToDelete = new ArrayList<>();
    private static boolean hasBeenInitialized = false;

    public JBoxWrapper(GameObject go) {
        JBoxWrapper.allWrappers.add(this);
        this.go = go;
    }

    public static void init() {
        if(!hasBeenInitialized) {
            JBoxWrapper.world.setSleepingAllowed(false);
            world.setContactListener(lis);
            l.add(FRPTime.streamDelta(30)
                    .filter(delta -> bodiesToDelete.size() > 0)
                    .listen(delta -> bodiesToDelete.forEach(world::destroyBody)));//TODO: Sometimes Sodium Callback error here.
            hasBeenInitialized = true;
        }
    }

    public JBoxWrapper updateToJbox(Stream<Vec2> updateFrom) {
        l.add(updateFrom
                .listen(vec2 -> body.setTransform(vec2, 0.0f)));
        return this;
    }

    public JBoxWrapper updateToJboxZeroForce(Stream<Vec2> updateFrom) {
        l.add(updateFrom
                .listen(vec2 -> {
                    this.body.m_force.setZero();
                    this.body.m_linearVelocity.setZero();
                    this.body.setTransform(vec2, 0.0f);
                }));
        return this;
    }

    public static GameObject getGOFromBody(Body body) {
        for(JBoxWrapper jbox : allWrappers) {
            if(jbox.body.equals(body)) {
                return jbox.go;
            }
        }
        return null;
    }

    Vec2 force = new Vec2(0.0f, 0.0f);

    public void applyForce() {
        this.body.applyForceToCenter(force);
        force.set(0.0f, 0.0f);
    }

    public void accumForce(Vec2 newForce) {
        force = force.add(newForce);
    }

    public static void physicsStep(float delta) {
        JBoxWrapper.world.step(delta, 6, 2);//TODO: Look to see if the two numbers at the end here are useful.
        for(JBoxWrapper body : allWrappers) {
            body.jboxUpdateStream.send(body.body);//Sort of a pulse for the time stream.
        }
    }

    public static JBoxWrapper BuildStaticBody(GameObject go, Vector3f pos, Mesh mesh) {
        JBoxWrapper phy = new JBoxWrapper(go);
        //Setup the template body and real body.
        BodyDef bDef = new BodyDef();
        bDef.position.set(Util.Vector3fToVec2(pos));//good
        phy.body = JBoxWrapper.world.createBody(bDef);
        //Now setup the AABB
        PolygonShape aabb = Util.vertexArrayToPoly(mesh.shape.getVertices());
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = aabb;
        fixtureDef.density = 0.1f;
        fixtureDef.friction = 0.1f;
        fixtureDef.restitution = 0.85f;
        phy.body.createFixture(fixtureDef);
        return phy;
    }

    public static JBoxWrapper BuildDynamicBody(GameObject go, Vector3f pos, Mesh mesh) {
        JBoxWrapper phy = new JBoxWrapper(go);
        //Setup the template body and real body.
        BodyDef bDef = new BodyDef();
        bDef.type = BodyType.DYNAMIC;
        bDef.position.set(Util.Vector3fToVec2(pos));
        bDef.fixedRotation = true;
        phy.body = JBoxWrapper.world.createBody(bDef);
        //Now setup the AABB
        PolygonShape aabb = Util.vertexArrayToPoly(mesh.shape.getVertices());
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = aabb;
        fixtureDef.density = 0.1f;
        fixtureDef.friction = 0.1f;
        fixtureDef.restitution = 0.85f;
        phy.body.createFixture(fixtureDef);
        return phy;
    }

    public Stream<Vector3f> updatePos() {
        return FRPTime.streamDelta(60)
                .map(delta -> body.getPosition())
                .map(Util::vec2ToVector3f);
    }

    public Stream<Vector3f> updateRot() {
        return FRPTime.streamDelta(60)
                .map(delta -> body.getTransform().q.getAngle())
                .map(angle -> new Vector3f(0.0f, 0.0f, angle));
    }

    public static void setupScreenCollider() {
        Scene.graph.add(new GameObject(new Vector3f(0.0f, 1.08f, 0.0f), MeshUtil.BuildRect(2.0f, 0.2f))
                .addStaticPhysics().name("wallTop"));
        Scene.graph.add(new GameObject(new Vector3f(0.0f, -1.08f, 0.0f), MeshUtil.BuildRect(2.0f, 0.2f))
                .addStaticPhysics().name("wallBot"));


        Scene.graph.add(new GameObject(new Vector3f(1.08f, 0.0f, 0.0f), MeshUtil.BuildRect(0.2f, 2.0f))
                .addStaticPhysics().name("wallRight"));
        Scene.graph.add(new GameObject(new Vector3f(-1.08f, 0.0f, 0.0f), MeshUtil.BuildRect(0.2f, 2.0f))
                .addStaticPhysics().name("wallLeft"));
    }

    public void applyForceToCenter(Vec2 v) {
        this.body.applyForceToCenter(v);
    }

    public static void markForDeletion(Body body) {
        bodiesToDelete.add(body);
    }
}