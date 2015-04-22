package Personal;

import org.engineFRP.FRP.FRPTime;
import org.engineFRP.Physics.JBoxWrapper;
import org.engineFRP.FRP.Time;
import org.engineFRP.Util.MapUtil;
import org.engineFRP.core.Engine;
import org.engineFRP.core.Game;
import org.engineFRP.core.GameObject;
import org.engineFRP.core.Scene;
import org.engineFRP.maths.Vector3f;
import org.engineFRP.rendering.MeshUtil;
import org.engineFRP.rendering.Texture;
import org.engineFRP.rendering.shaders.Material;
import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.FixtureDef;
import sodium.Stream;

/**
 * Created by TekMaTek on 01/03/2015.
 */
public class JBoxDemo implements Game {

    private static final String BOX_TEXTURE = "./res/textures/box.jpg";
    private static final String STONE_TEXTURE = "./res/textures/stone.jpg";

    public static void main(String[] args) {
        Engine.runGame(new JBoxDemo());
    }

    @Override
    public Scene setupScene() {
        PolygonShape groundBox = new PolygonShape();
        groundBox.setAsBox(9.8f, 1.0f);

        PolygonShape s = new PolygonShape();
        s.setAsBox(9.8f, 1.0f);
        //Setup the template body and real body.
        BodyDef bDef = new BodyDef();
        bDef.position.set(new Vec2(0.0f, 0.0f));
        Body body = JBoxWrapper.world.createBody(bDef);
        //Now setup the AABB
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = groundBox;
        fixtureDef.density = 0.1f;
        fixtureDef.friction = 0.1f;
        fixtureDef.restitution = 0.85f;
        body.createFixture(fixtureDef);

        GameObject trans = MapUtil.polyToGameObject(s)
                .translation(new Vector3f(0.0f, 0.0f, 0.0f))
                .addStaticPhysics()
                .updateToJbox(movements());
        trans.mesh.texture = Texture.loadTexture(STONE_TEXTURE)
                .changeSetting(Texture::RepeatTexture);
        Scene.graph.add(trans.name("Floor"));

        GameObject go = new GameObject(
                new Vector3f(0.0f, 0.0f, -1.0f), MeshUtil.BuildSquareWithTexture(BOX_TEXTURE, 0.4f), Material.white)
                .addDynamicPhysics();
        Scene.graph.add(go.name("Box"));

        return Scene.graph;
    }
    public static Stream<Vector3f> movements() {
        return FRPTime.streamDelta(Time.THIRTY_PER_SECOND)
                .map(deltaTime -> {
                    double curTime = Time.getTime();
                    return new Vector3f((float) Math.cos(curTime) / 80.0f, (float) Math.sin(curTime) / 80.0f, 0.0f);
                });
    }
}