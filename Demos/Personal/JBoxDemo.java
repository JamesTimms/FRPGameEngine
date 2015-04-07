package Personal;

import org.engineFRP.FRP.FRPUtil;
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

        GameObject trans = MapUtil.polyToGameObject(groundBox)
                .translation(new Vector3f(0.0f, -0.7f, 0.0f))
                .addStaticPhysics()
                .updateToJbox(FRPUtil.mapArrowKeysToMovementOf(-0.01f));
        trans.mesh.texture = Texture.loadTexture(STONE_TEXTURE)
                .changeSetting(Texture::RepeatTexture);
        Scene.graph.add(trans.name("Floor"));

        GameObject go = new GameObject(
                new Vector3f(0.0f, 0.0f, -1.0f), MeshUtil.BuildSquareWithTexture(BOX_TEXTURE, 0.4f), Material.white)
                .addDynamicPhysics();
        Scene.graph.add(go.name("Box"));

        return Scene.graph;
    }
}