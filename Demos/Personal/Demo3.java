package Personal;

import org.FRPengine.core.*;
import org.FRPengine.maths.Vector3f;
import org.FRPengine.rendering.MeshUtil;
import org.FRPengine.rendering.SimpleRenderer;
import org.FRPengine.rendering.shaders.BasicShader;
import org.FRPengine.rendering.shaders.Shader;
import sodium.Stream;
import sodium.StreamSink;

import static org.lwjgl.glfw.GLFW.glfwPollEvents;
import static org.lwjgl.glfw.GLFW.glfwSwapBuffers;
import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.glClear;

/**
 * Created by TekMaTek on 01/03/2015.
 */
public class Demo3 {

    public static void main(String[] args) {
        new Demo3();
    }

    static Time renderTimer = new Time();
    static Time pollTimer = new Time();
    static StreamSink<Integer> frameStream = new StreamSink<>();

    public Demo3() {
        FRPDisplay.create();
        FRPKeyboard.create();
        SimpleRenderer.init();
        loop();
    }

    public void setupTimeLoopDemo() {
        for(Transform transform : sceneMeshes) {
            transform.mergeIntoCellAndAccum(movements());
        }
    }

    public static Stream<Vector3f> movements() {
        return Time.deltaOf(frameStream)
                .map(deltaTime -> new Vector3f(0.1f * deltaTime, 0.0f, 0.0f))
                .merge(FRPUtil.mapArrowKeysToMovementOf(-0.1f));
    }

    public void loop() {
        shader2 = new BasicShader();
        sceneMeshes = new Transform[] {
                new Transform(new Vector3f(0.0f, 0.0f, -1.0f), MeshUtil.BuildSquare()),
                new Transform(new Vector3f(-0.3f, 0.0f, -1.0f), MeshUtil.BuildSquare()),
                new Transform(new Vector3f(0.1f, 0.4f, -1.0f), MeshUtil.BuildSquare()),
                new Transform(new Vector3f(0.3f, 0.0f, -1.0f), MeshUtil.BuildSquare())
        };
        setupTimeLoopDemo();

        while(!FRPDisplay.shouldWindowClose()) {
            if(pollTimer.shouldGetFrame(120)) {
                glfwPollEvents();
                if(renderTimer.shouldGetFrame(Time.THIRTY_PER_SECOND)) {
                    renderDemo3();
                }
                frameStream.send(Time.THIRTY_PER_SECOND);//Sent arbitrarily and doesn't matter when it's sent just as long as it
                //isn't infrequently enough to cause a frame to be missed.
            }
        }
    }

    public static void drawDemo3() {
        for(Transform transform : sceneMeshes) {
            shader2.draw(transform);
        }
    }

    static Shader shader2;
    private static Transform[] sceneMeshes;

    public void renderDemo3() {
        glClear(GL_COLOR_BUFFER_BIT);
        drawDemo3();
        glfwSwapBuffers(FRPDisplay.getWindow());
    }
}