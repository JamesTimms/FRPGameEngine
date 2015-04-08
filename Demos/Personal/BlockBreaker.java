package Personal;

import org.engineFRP.FRP.*;
import org.engineFRP.rendering.JBoxDebugDraw;
import org.engineFRP.core.*;
import org.engineFRP.maths.Vector3f;
import org.engineFRP.rendering.MeshUtil;
import org.engineFRP.rendering.shaders.Material;
import sodium.Listener;
import sodium.Stream;

import java.util.ArrayList;

import static org.lwjgl.glfw.GLFW.*;

/**
 * Created by TekMaTek on 30/03/2015.
 */
public class BlockBreaker implements Game {

    private static final String BLOCK_TEXTURE = "./res/textures/block.png";
    private static final String PADDLE_TEXTURE = "./res/textures/complexCheckers.png";

    private static final String PADDLE_GO = "Paddle";
    private static ArrayList<Listener> l = new ArrayList<>();

    public static void main(String[] args) {
        Engine.runGame(new BlockBreaker());
    }

    @Override
    public Scene setupScene() {
        final float offsetY = 0.5f;
        int counter = 0;
        for(int i = -1; i < 2; i++) {
            for(int j = -1; j < 2; j++) {
                Scene.graph.add(
                        new GameObject(new Vector3f(0.37f * i, (0.13f * j) + offsetY, -1.0f), MeshUtil.BuildRectWithTexture(BLOCK_TEXTURE, 0.4f, 0.2f), Material.blue)
                                .addStaticPhysics()
                                .name("Block" + counter++)
                                .canBeDestroyedBy("Ball")
                );
            }
        }
        Scene.graph.add(
                new GameObject(new Vector3f(0.0f, -0.8f, -1.0f), MeshUtil.BuildRectWithTexture(PADDLE_TEXTURE, 0.4f, 0.05f), Material.white)
                        .name(PADDLE_GO)
                        .addStaticPhysics()
                        .updateToJbox(paddleMovement(-0.1f))
        );
        Scene.graph.add(
                new GameObject(new Vector3f(0.0f, 0.0f, -1.0f), MeshUtil.BuildCircleWithTexture(PADDLE_TEXTURE, 0.05f), Material.white)
                        .name("Ball")
                        .addDynamicPhysics()
                        .bouncyCollisionsWith(PADDLE_GO)
                        .resetJboxPosWith(GLFW_KEY_R)
        );
        JBoxWrapper.setupScreenCollider();

        return Scene.graph;
    }

    public static Stream<Vector3f> paddleMovement(float moveAmount) {
        return FRPKeyboard.keyEvent
                .filter(key -> key.action != GLFW_RELEASE
                        && FRPKeyboard.isArrowKeyPressed(key.key))
                .map(key -> {
                    switch(key.key) {
                        case (GLFW_KEY_RIGHT):
                            return new Vector3f(-moveAmount, 0.0f, 0.0f);
                        case (GLFW_KEY_LEFT):
                            return new Vector3f(moveAmount, 0.0f, 0.0f);
                        default:
                            return Vector3f.ZERO;
                    }
                });
    }
}