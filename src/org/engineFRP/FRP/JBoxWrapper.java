package org.engineFRP.FRP;

import org.engineFRP.Util.Util;
import org.engineFRP.maths.Vector3f;
import org.engineFRP.rendering.Mesh;
import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.*;
import sodium.Listener;
import sodium.Stream;

/**
 * Created by TekMaTek on 04/04/2015.
 */
public class JBoxWrapper {

    private static final Vec2 gravity = new Vec2(0.0f, -9.8f);
    public static final World world = new World(gravity);
    private final Listener l;
    public Body body;

    public JBoxWrapper() {
        JBoxWrapper.world.setSleepingAllowed(false);
        l = FRPTime.streamDelta(60)
                .listen(delta -> JBoxWrapper.world.step(delta, 6, 2));
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
        bDef.fixedRotation = false;
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

    public Stream<Vector3f> Update() {
        return FRPTime.streamDelta(60)
                .map(delta -> body.getPosition())
                .map(Util::vec2ToVector3f);
    }

    public Stream<Vector3f> Update2() {
        return FRPTime.streamDelta(60)
                .map(delta -> body.getTransform().q.getAngle())
                .map(angle -> new Vector3f(0.0f, 0.0f, angle));
    }
}