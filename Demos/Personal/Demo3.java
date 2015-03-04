package Personal;

import org.FRPengine.core.*;
import org.FRPengine.maths.Vector3f;
import org.FRPengine.rendering.MeshUtil;
import org.FRPengine.rendering.SimpleRenderer;
import org.FRPengine.rendering.shaders.BasicShader;
import org.FRPengine.rendering.shaders.Shader;
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

        sceneMeshes[1].translation = timePulse
                .filter(time -> time == moveTimer)
                .filter(time -> time.shouldGetFrame(Time.THIRTY_PER_SECOND))
                .map(time -> {
//                    for (Transform transform : sceneMeshes) {
//                        transform.setTranslation(new Vector3f(0.02f * time.getDeltaTime(), 0.0f, 0.0f));
//                    }
                    return new Vector3f(0.02f * time.getDeltaTime(), 0.0f, 0.0f);
                })
                .merge(sceneMeshes[1].translation.updates())//how to merge this into all streams? Also how to not reference sceneMeshses
                .hold(sceneMeshes[1].translation.sample());
//        pollFunction = timePulse
//                .filter(time -> time == frameTimer)
//                .filter(time -> time.shouldGetFrame(120))
//                .listen(time -> glfwPollEvents());
//        Listener renderTimer = timePulse
//                .filter(moveTimer -> moveTimer.shouldGetFrame(120))
//                .listen( moveTimer -> RenderDemo3());
    }

    public void loop() {
        shader2 = new BasicShader();
        sceneMeshes = new Transform[] {
                new Transform(new Vector3f(0.0f, 0.0f, -1.0f), MeshUtil.BuildSquare()),
                new Transform(new Vector3f(-0.3f, 0.0f, -1.0f), MeshUtil.BuildSquare()),
                new Transform(new Vector3f(0.1f, 0.4f, -1.0f), MeshUtil.BuildSquare()),
                new Transform(new Vector3f(0.3f, 0.0f, -1.0f), MeshUtil.BuildSquare())
        };

        int i = 0;
        for(Transform transform : sceneMeshes) {
            transform.translation = FRPUtil.SetupMovement(transform, i);
            i++;
        }//TODO: figure out how to iterate over sceneMeshes with an operation like above.
        sceneMeshes[1].setTranslation(new Vector3f(0.0f, -0.5f, 0.0f));

        setupTimeLoopDemo();

        while(!FRPDisplay.shouldWindowClose()) {
            if(frameTimer.shouldGetFrame(120)) {
                glfwPollEvents();
            }
//            timePulse.send(frameTimer);
            if(renderTimer.shouldGetFrame(Time.THIRTY_PER_SECOND)) {
                RenderDemo3();
            }
//            if(moveTimer.shouldGetFrame(Time.THIRTY_PER_SECOND)) {
//                for(Transform transform : sceneMeshes) {
//                    transform.setTranslation(new Vector3f(0.02f, 0.0f, 0.0f));
//                }
//            }
            timePulse.send(moveTimer);//Sent arbitrarily and doesn't matter when it's sent just as long as it
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

    public void RenderDemo3() {
        glClear(GL_COLOR_BUFFER_BIT);
        drawDemo3();
        glfwSwapBuffers(FRPDisplay.GetWindow());
    }
}