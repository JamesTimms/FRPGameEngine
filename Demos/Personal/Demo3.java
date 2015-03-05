package Personal;

import org.FRPengine.core.*;
import org.FRPengine.maths.Vector3f;
import org.FRPengine.rendering.MeshUtil;
import org.FRPengine.rendering.SimpleRenderer;
import org.FRPengine.rendering.shaders.BasicShader;
import org.FRPengine.rendering.shaders.Shader;
import sodium.Cell;
import sodium.Listener;
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

    private Time moveTimer;
    private Time move2Timer;

    public static void main(String[] args) {
        new Demo3();
    }

    static Time frameTimer = new Time();
    static Time renderTimer = new Time();
    static StreamSink<Time> timePulse = new StreamSink<>();

    public Demo3() {
        FRPDisplay.Create();
        FRPKeyboard.Create();
        SimpleRenderer.init();
        loop();
    }

    public Listener moverFunction;
    public Listener pollFunction;

    public void setupTimeLoopDemo() {
        moveTimer = new Time();
        for(Transform transform : sceneMeshes) {
            transform.MergeIntoCellAndAccum(movements(moveTimer, transform.translation));
        }
    }

    public static Stream<Vector3f> movements(Time timer, Cell<Vector3f> existingTransform){
        return timePulse
                .filter(time -> time == timer)
                .filter(time -> time.shouldGetFrame(Time.THIRTY_PER_SECOND))
                .map(time -> new Vector3f(0.01f * time.getDeltaTime(), 0.0f, 0.0f))
                .merge(FRPUtil.mapArrowKeysToMovementOf(-0.1f))
                .merge(existingTransform.updates());
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
            if(frameTimer.shouldGetFrame(120)) {
                glfwPollEvents();
            }
            if(renderTimer.shouldGetFrame(Time.THIRTY_PER_SECOND)) {
                renderDemo3();
            }
//            timePulse.send(moveTimer);//Sent arbitrarily and doesn't matter when it's sent just as long as it
            //isn't infrequently enough to cause a frame to be missed.
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
        glfwSwapBuffers(FRPDisplay.GetWindow());
    }
}