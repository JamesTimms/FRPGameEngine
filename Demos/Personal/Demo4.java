package Personal;

import org.engineFRP.FRP.FRPTime;
import org.engineFRP.FRP.Time;
import org.engineFRP.Physics.collision.Click;
import org.engineFRP.core.FRPDisplay;
import org.engineFRP.core.FRPKeyboard;
import org.engineFRP.core.FRPMouse;
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

    private static Time renderTimer = new Time(Time.THIRTY_PER_SECOND);
    private static Time pollTimer = new Time(120);

    private static Shader shader2;
    private static Transform[] sceneMeshes;

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
        return FRPTime.streamDelta(Time.THIRTY_PER_SECOND)
                .map(deltaTime -> {
                    double curTime = Time.getTime() / Time.SECOND;
                    return new Vector3f((float) Math.sin(curTime) / 80.0f, (float) Math.sin(curTime) / 80.0f, 0.0f);
                });
//                .merge(FRPUtil.mapArrowKeysToMovementOf(-0.1f));
    }

    Listener scoreListener;

    public void loop() {
        shader2 = new BasicShader();
        sceneMeshes = new Transform[] {
                new Transform(new Vector3f(0.0f, 0.0f, -1.0f), MeshUtil.BuildSquare())
        };
        setupScene();

        Cell<Integer> score = FRPMouse.clickStream
                .filter(mouse -> mouse.button == GLFW_MOUSE_BUTTON_LEFT &&
                        mouse.action == GLFW_PRESS)
                .snapshot(cursorPosStream.hold(null), (click, cursor) -> new Tuple2<>(click, cursor))
                .map(mouse -> new Click(screenToWorldSpace(mouse.b.position))
                        .isInPolygon(sceneMeshes[0].mesh.shape, sceneMeshes[0]))//FIXME: need to make this work better.
                .map(hitShape -> {
                    if(hitShape) {
                        sceneMeshes[0].mesh.resize(0.80f);
                        return 1;
                    } else {
                        sceneMeshes[0].mesh.resize(1.25f);
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
            if(pollTimer.shouldGetFrame()) {
                glfwPollEvents();
                if(renderTimer.shouldGetFrame()) {
                    renderDemo3();
                }
                FRPTime.pollStreams();
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