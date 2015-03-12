package Personal;

import org.engineFRP.Physics.collision.Click;
import org.engineFRP.core.*;
import org.engineFRP.core.Transform;
import org.engineFRP.maths.Vector3f;
import org.engineFRP.rendering.MeshUtil;
import org.engineFRP.rendering.SimpleRenderer;
import org.engineFRP.rendering.shaders.BasicShader;
import org.engineFRP.rendering.shaders.Shader;
import sodium.*;

import static org.engineFRP.core.FRPMouse.cursorPosStream;
import static org.engineFRP.core.FRPMouse.screenToWorldSpace;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.glClear;

/**
 * Created by TekMaTek on 01/03/2015.
 */
public class Demo4 {

    public static void main(String[] args) {
        new Demo4();
    }

    private static Time renderTimer = new Time();
    private static Time pollTimer = new Time();
    private static StreamSink<Integer> frameStream = new StreamSink<>();

    private static Shader shader2;
    private static Transform[] sceneMeshes;
    private static Cell<Integer> score;

    public Demo4() {
        FRPDisplay.create();
        FRPKeyboard.create();
        FRPMouse.create();
        SimpleRenderer.init();
        loop();
    }

    public void setupScene() {
        for(Transform transform : sceneMeshes) {
            transform.mergeIntoCellAndAccum(movements());
        }
    }

    public static Stream<Vector3f> movements() {
        return Time.deltaOf(frameStream)
                .map(deltaTime -> Vector3f.ZERO)
                .merge(FRPUtil.mapArrowKeysToMovementOf(-0.1f));
    }

    Listener scoreListener;

    public void loop() {
        shader2 = new BasicShader();
        sceneMeshes = new Transform[] {
                new Transform(new Vector3f(0.0f, 0.0f, -1.0f), MeshUtil.BuildSquare())
        };
        setupScene();

        score = FRPMouse.clickStream
                //TODO: Make square move back and forth.
                //TODO: Add a game timer.
//        Optional<FRPMouse.Mouse>
                .filter(mouse -> mouse.button == GLFW_MOUSE_BUTTON_LEFT &&
                        mouse.action == GLFW_PRESS)
                .snapshot(cursorPosStream.hold(null), (click, cursor) -> new Tuple2<>(click, cursor))
                .map(mouse -> new Click(screenToWorldSpace(mouse.b.position))
                        .isInPolygon(sceneMeshes[0].mesh.shape, sceneMeshes[0]))//FIXME: need to make this work better.
                .map(hitShape -> {
                    if(hitShape) {
                        return 1;
                    } else {
                        return -1;
                    }
                })
                .accum(0, (curScore, lastScore) -> curScore + lastScore);

        scoreListener = score
                .updates()
                .listen(System.out::println);


        //Just some thoughts on how to better implement the time loops.
        //While(shouldStillPlayGame){
        //  sleepOrFreeThread(forSmallestTimeTillNextUpdate);//For example sleep for 1/30 of a second.
        //  processNextActionRequired();//Not sure how this will work for simultaneous actions.
        //}
        while(!FRPDisplay.shouldWindowClose()) {
            if(pollTimer.shouldGetFrame(120)) {
                glfwPollEvents();
                if(renderTimer.shouldGetFrame(Time.THIRTY_PER_SECOND)) {
                    renderDemo3();
                }
                frameStream.send(Time.THIRTY_PER_SECOND);
            }
        }
        scoreListener.unlisten();
    }

    public static void drawDemo3() {
        for(Transform transform : sceneMeshes) {
            shader2.draw(transform);
        }
    }

    public void renderDemo3() {
        glClear(GL_COLOR_BUFFER_BIT);
        drawDemo3();
        glfwSwapBuffers(FRPDisplay.getWindow());
    }
}