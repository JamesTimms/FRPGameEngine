package Personal;

import org.engineFRP.FRP.FRPTime;
import org.engineFRP.FRP.FRPUtil;
import org.engineFRP.FRP.JBoxWrapper;
import org.engineFRP.Util.MapUtil;
import org.engineFRP.Util.Util;
import org.engineFRP.core.Engine;
import org.engineFRP.core.Game;
import org.engineFRP.core.GameObject;
import org.engineFRP.core.Scene;
import org.engineFRP.maths.Vector3f;
import org.engineFRP.rendering.MeshUtil;
import org.engineFRP.rendering.Texture;
import org.engineFRP.rendering.shaders.Material;
import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.dynamics.*;

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
        groundBox.setAsBox(5.0f, 1.0f);

        GameObject trans = MapUtil.polyToTrans(groundBox)
                .translation(new Vector3f(0.0f, -0.7f, 0.0f))
                .addStaticPhysics()
                .mergeTranslation(FRPUtil.mapArrowKeysToMovementOf(-0.01f))
                .updateToJbox();
        trans.mesh.texture = Texture.loadTexture(STONE_TEXTURE)
                .changeSetting(Texture::RepeatTexture);
        Scene.graph.add(trans);

//        BodyDef bodyDef = new BodyDef();
//        bodyDef.type = BodyType.DYNAMIC;
//        bodyDef.position.set(0.0f, 4.0f);
//        bodyDef.fixedRotation = false;
//        Body body = JBoxWrapper.world.createBody(bodyDef);
//        PolygonShape dynamicBox = new PolygonShape();
//        dynamicBox.setAsBox(1.0f, 1.0f);
//        FixtureDef fixtureDef = new FixtureDef();
//        fixtureDef.shape = dynamicBox;
//        fixtureDef.density = 1.0f;
//        fixtureDef.friction = 0.3f;
//        body.createFixture(fixtureDef);

        GameObject go = new GameObject(
                new Vector3f(0.0f, 0.0f, -1.0f), MeshUtil.BuildSquareWithTexture(BOX_TEXTURE, 0.4f), Material.white)
                .addDynamicPhysics()
                .changeTranslationType(FRPUtil.setVector)
                .updateFromJbox();
//        go.mesh.texture = Texture.loadTexture(BOX_TEXTURE);
        Scene.graph.add(go);

        return Scene.graph;
    }
}