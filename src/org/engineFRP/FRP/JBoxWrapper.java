package org.engineFRP.FRP;

import org.engineFRP.Util.Util;
import org.engineFRP.maths.Vector3f;
import org.engineFRP.rendering.Mesh;
import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.*;

/**
 * Created by TekMaTek on 04/04/2015.
 */
public class JBoxWrapper {

    private static final Vec2 gravity = new Vec2(0.0f, -9.8f);
    public static final World world = new World(gravity);
    public Body body;

    public JBoxWrapper() {
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
}