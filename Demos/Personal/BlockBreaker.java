package Personal;

import org.engineFRP.FRP.*;
import org.engineFRP.core.*;
import org.engineFRP.maths.Vector3f;
import org.engineFRP.rendering.MeshUtil;
import org.engineFRP.rendering.shaders.Material;
import org.jbox2d.collision.AABB;
import org.jbox2d.common.Vec2;
import sodium.Listener;
import sodium.Stream;

import static org.lwjgl.glfw.GLFW.*;

/**
 * Created by TekMaTek on 30/03/2015.
 */
public class BlockBreaker implements Game {

    private static final String BLOCK_TEXTURE = "./res/textures/block.png";
    private static final String PADDLE_TEXTURE = "./res/textures/complexCheckers.png";
    private static Listener l;

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
                        new GameObject(new Vector3f(0.37f * i, (0.13f * j) + offsetY, -1.0f), MeshUtil.BuildRectWithTexture(BLOCK_TEXTURE, 0.2f, 0.4f), Material.blue)
                        , "Block" + counter++
                );
            }
        }
        Scene.graph.add(
                new GameObject(new Vector3f(0.0f, -0.8f, -1.0f), MeshUtil.BuildRectWithTexture(PADDLE_TEXTURE, 0.05f, 0.4f), Material.white)
                        .mergeTranslation(FRPUtil.mapArrowKeysToMovementOf(-0.1f))
                        .addStaticPhysics().updateToJbox(), "Paddle"
        );
        Scene.graph.add(
                new GameObject(new Vector3f(0.0f, -0.35f, -1.0f), MeshUtil.BuildCircleWithTexture(PADDLE_TEXTURE, 0.05f), Material.white)
                        .addDynamicPhysics().updateFromJbox(), "Ball"
        );
        JBoxWrapper.setupScreenCollider();

        l = FRPTime.streamDelta(30)
                .listen(delta -> isCollidingWithBat());

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

    public void isCollidingWithBat() {
        GameObject ball = Scene.graph.find("Ball").sample();
        GameObject bat = Scene.graph.find("Paddle").sample();
        Vector3f batPos = bat.transform.translation.sample();
        Vector3f ballPos = ball.transform.translation.sample();
        float xForce = ballPos.x - batPos.x;
        if(AABB.testOverlap(ball.physics.body.getFixtureList().getAABB(0)
                , bat.physics.body.getFixtureList().getAABB(0))) {
            ball.physics.body.applyForceToCenter(new Vec2(xForce, 0.3f));
        }
    }
}