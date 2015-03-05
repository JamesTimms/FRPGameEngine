package Personal;

import org.FRPengine.core.*;
import org.FRPengine.maths.Vector3f;
import org.FRPengine.rendering.MeshUtil;
import org.FRPengine.rendering.SimpleRenderer;
import org.FRPengine.rendering.shaders.BasicShader;
import org.FRPengine.rendering.shaders.Shader;
import sodium.Listener;
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
        move2Timer = new Time();

        sceneMeshes[1].translation = timePulse
                .filter(time -> time == moveTimer)
                .filter(time -> time.shouldGetFrame(Time.THIRTY_PER_SECOND))
                .map(time -> {
                    final float MOVE_AMT = 0.05f;
                    return new Vector3f(MOVE_AMT * time.getDeltaTime(), 0.0f, 0.0f);
                })
//                .snapshot(sceneMeshes[1].translation)//Merging with the key pressed Stream basically.
                .merge(FRPUtil.mapArrowKeysToMovementOf(-0.1f))
                .accum(new Vector3f(-0.7f, 0.2f, 0.0f), (frame, total) -> {
                    return total.add(frame);
                });
//                .updates()
//                .merge(FRPUtil.mapArrowKeysToMovementOf(-0.01f))
//                .hold(new Vector3f(-0.5f, -0.2f, 0.0f));

//        sceneMeshes[1].translation =
//                sceneMeshes[1].translation
//                .updates()
//                .merge(FRPUtil.mapArrowKeysToMovementOf(-0.1f))
//                        .hold(Vector3f.ZERO);
//                FRPUtil.setupMovement(sceneMeshes[1], 1);

//        pollFunction = timePulse
//                .filter(time -> time == frameTimer)
//                .filter(time -> time.shouldGetFrame(120))
//                .listen(time -> glfwPollEvents());
//        Listener renderTimer = timePulse
//                .filter(moveTimer -> moveTimer.shouldGetFrame(120))
//                .listen( moveTimer -> renderDemo3());
    }

    public void loop() {
        shader2 = new BasicShader();
        sceneMeshes = new Transform[] {
                new Transform(new Vector3f(0.0f, 0.0f, -1.0f), MeshUtil.BuildSquare()),
                new Transform(new Vector3f(-0.3f, 0.0f, -1.0f), MeshUtil.BuildSquare())
//                new Transform(new Vector3f(0.1f, 0.4f, -1.0f), MeshUtil.BuildSquare()),
//                new Transform(new Vector3f(0.3f, 0.0f, -1.0f), MeshUtil.BuildSquare())
        };

        int i = 0;
        for(Transform transform : sceneMeshes) {
//            transform.translation = FRPUtil.setupMovement(transform, i);
            i++;
        }//TODO: figure out how to iterate over sceneMeshes with an operation like above.
        setupTimeLoopDemo();

        while(!FRPDisplay.shouldWindowClose()) {
            if(frameTimer.shouldGetFrame(120)) {
                glfwPollEvents();
            }
//            timePulse.send(frameTimer);
            if(renderTimer.shouldGetFrame(Time.THIRTY_PER_SECOND)) {
                renderDemo3();
            }
            if(move2Timer.shouldGetFrame(Time.THIRTY_PER_SECOND)) {
//                for(Transform transform : sceneMeshes) {
//                sceneMeshes[1].setTranslation(new Vector3f(0.1f * moveTimer.getDeltaTime(), 0.0f, 0.0f));
//                }
            }
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

    public void renderDemo3() {
        glClear(GL_COLOR_BUFFER_BIT);
        drawDemo3();
        glfwSwapBuffers(FRPDisplay.GetWindow());
    }
}