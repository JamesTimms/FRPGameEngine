package Personal.BlockBreaker;

import org.engineFRP.core.Engine;
import org.engineFRP.core.Game;
import org.engineFRP.core.GameObject;
import org.engineFRP.core.Scene;
import org.engineFRP.Physics.JBoxWrapper;
import org.engineFRP.maths.Vector3f;
import org.engineFRP.rendering.MeshUtil;
import org.engineFRP.rendering.shaders.Material;

import static org.lwjgl.glfw.GLFW.*;

/**
 * Created by TekMaTek on 30/03/2015.
 */
public class BlockBreaker implements Game {

    private static final String BLOCK_TEXTURE = "./res/textures/block.png";
    private static final String PADDLE_TEXTURE = "./res/textures/box.jpg";

    private static final String PADDLE_GO = "Paddle";

    public static void main(String[] args) {
        Engine.runGame(new BlockBreaker());
    }

    @Override
    public Scene setupScene() {
        final float offsetY = 0.5f;
        int counter = 0;
        for(int i = -3; i < 4; i++) {
            for(int j = -3; j < 4; j++) {
                Scene.graph.add(
                        new GameObject(new Vector3f(0.175f * i, (0.065f * j) + offsetY, -1.0f),
                                MeshUtil.BuildRectWithTexture(BLOCK_TEXTURE, 0.2f, 0.1f), Material.blue)
                                .addStaticPhysics()
                                .name("Block" + counter++)
                                .apply(BBLogic::canBeDestroyedBy, "Ball")
                );
            }
        }
        Scene.graph.add(
                new GameObject(new Vector3f(0.0f, -0.8f, -1.0f),
                        MeshUtil.BuildRectWithTexture(PADDLE_TEXTURE, 0.3f, 0.025f), Material.white)
                        .name(PADDLE_GO)
                        .addStaticPhysics()
                        .updateToJbox(BBLogic.paddleMovement(-0.1f))
        );
        Scene.graph.add(
                new GameObject(new Vector3f(0.0f, 0.0f, -1.0f), MeshUtil.BuildCircle(0.028f), Material.white)
                        .name("Ball")
                        .addDynamicPhysics()
                        .apply(BBLogic::bouncyCollisionsWith, PADDLE_GO)
                        .resetJboxPosWith(GLFW_KEY_R)
        );
        JBoxWrapper.setupScreenCollider();

        return Scene.graph;
    }
}


