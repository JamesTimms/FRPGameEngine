package org.engineFRP.FRP;

import org.engineFRP.Physics.collision.AABB;
import org.engineFRP.Util.Util;
import org.engineFRP.maths.Vector3f;
import org.engineFRP.rendering.Mesh;
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
    private static ArrayList<JBoxWrapper> allBodies = new ArrayList<>();
    private final StreamSink<Body> jboxUpdateStream = new StreamSink<>();//Really inefficient?
    public Body body;
    Listener updateJBox;//Listeners are bad here but is needed because no other way to update JBox when our transform moves.

    public JBoxWrapper() {
        JBoxWrapper.allBodies.add(this);
        JBoxWrapper.world.setSleepingAllowed(false);//An Init. Here as a cheat. Should move this else where.
    }

    public JBoxWrapper updateToJbox(Stream<Vec2> updateFrom) {
        updateJBox = updateFrom
                .listen(vec2 -> body.setTransform(vec2, 0.0f));
        return this;
    }

    public static void physicsStep(float delta) {
        JBoxWrapper.world.step(delta, 6, 2);//TODO: Look to see if the two numbers at the end here are useful.
        for(JBoxWrapper body : allBodies) {
            body.jboxUpdateStream.send(body.body);//Sort of a pulse for the time stream.
        }
    }

    public static JBoxWrapper BuildStaticBody(Vector3f pos, Mesh mesh) {
        JBoxWrapper phy = new JBoxWrapper();
        //Setup the template body and real body.
        BodyDef bDef = new BodyDef();
        bDef.position.set(Util.Vector3fToVec2(pos));
        phy.body = JBoxWrapper.world.createBody(bDef);
        //Now setup the AABB
        PolygonShape aabb = Util.vertexArrayToPoly(mesh.shape.getVertices());
        phy.body.createFixture(aabb, 0.0f);
        return phy;
    }

    public static JBoxWrapper BuildDynamicBody(Vector3f pos, Mesh mesh) {
        JBoxWrapper phy = new JBoxWrapper();
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
        fixtureDef.density = 1.0f;
        fixtureDef.friction = 0.3f;
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
        JBoxWrapper phy = new JBoxWrapper();
        BodyDef bDef = new BodyDef();
        bDef.position.set(new Vec2(0.0f, 0.0f));
        phy.body = JBoxWrapper.world.createBody(bDef);
        PolygonShape p = new PolygonShape();
        p.setAsBox(-6.0f, -6.0f);//Corner
        p.setAsBox(6.0f, 6.0f);//Corner
        phy.body.createFixture(p, 0.0f);
    }

}