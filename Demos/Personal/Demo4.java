package Personal;

import org.engineFRP.FRP.FRPTime;
import org.engineFRP.FRP.Time;
import org.engineFRP.Physics.collision.Click;
import org.engineFRP.core.*;
import org.engineFRP.maths.Vector3f;
import org.engineFRP.rendering.MeshUtil;
import org.engineFRP.rendering.SimpleRenderer;
import org.engineFRP.rendering.shaders.Shader;
import org.engineFRP.rendering.shaders.SquareShader;
import sodium.Cell;
import sodium.Listener;
import sodium.Stream;
import sodium.Tuple2;

import java.util.Dictionary;
import java.util.Enumeration;
import java.util.Hashtable;

import static org.engineFRP.core.FRPMouse.clickStream;
import static org.engineFRP.core.FRPMouse.cursorPosStream;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.glClear;

/**
 * Created by TekMaTek on 01/03/2015.
 */
public class Demo4 {

    private final static String TEXT_FILE = "./res/textures/grids.png";
    private static Time renderTimer = new Time(Time.THIRTY_PER_SECOND);
    private static Time pollTimer = new Time(120);

    private static Shader shader2;
    private static Transform[] sceneTransforms;

    public Demo4() {
        setupScene();

        Cell<Integer> score = shapeClickStream();
        Listener scoreListener = score
                .updates()
                .listen(System.out::println);

        gameLoop();

        scoreListener.unlisten();
    }

    public void setupScene() {
        shader2 = new SquareShader();
        sceneTransforms = new Transform[] {
                new Transform(new Vector3f(0.0f, 0.0f, -1.0f), MeshUtil.BuildSquareWithTexture(TEXT_FILE))
                        .mergeTranslation(movements())
                        .mergeTranslation(FRPUtil.mapArrowKeysToMovementOf(-0.1f))
        };
    }

    private static Cell<Integer> shapeClickStream() {
        return clickStream
                .filter(mouse -> mouse.button == GLFW_MOUSE_BUTTON_LEFT &&
                        mouse.action == GLFW_PRESS)
                .snapshot(cursorPosStream.hold(null), (click, cursor) -> new Tuple2<>(click, cursor))
                .map(mouse -> {
                    Dictionary<Transform, Boolean> hits = new Hashtable<>();
                    for(Transform t : sceneTransforms) {
                        hits.put(t,
                                Click.isInPolygon(t.addPosAndFlipY(), mouse.b.position));
                    }
                    return hits;
                })
                .map(hitShape -> {
                    int scoreThisClick = 0;
                    Enumeration<Transform> keys = hitShape.keys();
                    while(keys.hasMoreElements()) {
                        Transform t = keys.nextElement();
                        if(hitShape.get(t)) {
                            t.mesh.resize(0.80f);
                            scoreThisClick++;
                        } else {
                            t.mesh.resize(1.25f);
                            scoreThisClick--;
                        }
                    }
                    return scoreThisClick;
                })
                .accum(0, (curScore, lastScore) -> curScore + lastScore);
    }

    public void gameLoop() {
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
    }

    public static Stream<Vector3f> movements() {
        return FRPTime.streamDelta(Time.THIRTY_PER_SECOND)
                .map(deltaTime -> {
                    double curTime = Time.getTime();
                    return new Vector3f((float) Math.sin(curTime) / 80.0f, (float) Math.sin(curTime) / 80.0f, 0.0f);
                });
    }

    public void renderDemo3() {
        glClear(GL_COLOR_BUFFER_BIT);
        drawDemo3();
        glfwSwapBuffers(FRPDisplay.getWindow());
    }

    public static void drawDemo3() {
        for(Transform transform : sceneTransforms) {
            shader2.draw(transform);
        }
    }

    public static void main(String[] args) {
        FRPDisplay.create();
        FRPKeyboard.create();
        FRPMouse.create();
        SimpleRenderer.init();
        new Demo4();
    }
}