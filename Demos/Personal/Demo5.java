package Personal;

import org.engineFRP.FRP.FRPTime;
import org.engineFRP.FRP.FRPUtil;
import org.engineFRP.FRP.Time;
import org.engineFRP.core.*;
import org.engineFRP.maths.Vector3f;
import org.engineFRP.rendering.MeshUtil;
import sodium.Stream;

import static org.lwjgl.glfw.GLFW.glfwPollEvents;
import static org.lwjgl.glfw.GLFW.glfwSwapBuffers;
import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.glClear;

/**
 * Created by TekMaTek on 01/03/2015.
 */
public class Demo5 implements Game {

    private static final String TEXT_FILE = "./res/textures/grad.png";

    private static SceneGraph scene;
    private static Time renderTimer = new Time(Time.THIRTY_PER_SECOND);
    private static Time pollTimer = new Time(120);

    public static void main(String[] args) {
        try {
            Engine.StartEngine(new Demo5());
        } catch(Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }

    @Override
    public void setupScene() {
        scene = new SceneGraph(
                new Transform[] {
                        new Transform(new Vector3f(0.0f, 0.0f, -1.0f), MeshUtil.BuildSquareWithTexture(TEXT_FILE))
                                .mergeTranslation(movements())
                                .mergeTranslation(FRPUtil.mapArrowKeysToMovementOf(-0.1f))
                });
    }

    @Override
    public void input() {
        if(pollTimer.shouldGetFrame()) {
            glfwPollEvents();
        }
    }

    @Override
    public void render() {
        if(renderTimer.shouldGetFrame()) {
            glClear(GL_COLOR_BUFFER_BIT);
            scene.drawScene();
            glfwSwapBuffers(FRPDisplay.getWindow());
        }
    }

    public static Stream<Vector3f> movements() {
        return FRPTime.streamDelta(Time.THIRTY_PER_SECOND)
                .map(deltaTime -> {
                    double curTime = Time.getTime();
                    return new Vector3f((float) Math.sin(curTime) / 80.0f, (float) Math.sin(curTime) / 80.0f, 0.0f);
                });
    }
}