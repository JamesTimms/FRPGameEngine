package Personal;

import org.engineFRP.FRP.FRPTime;
import org.engineFRP.FRP.FRPUtil;
import org.engineFRP.FRP.Time;
import org.engineFRP.Util.Click;
import org.engineFRP.FRP.FRPDisplay;
import org.engineFRP.FRP.FRPKeyboard;
import org.engineFRP.FRP.FRPMouse;
import org.engineFRP.core.GameObject;
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

import static org.engineFRP.FRP.FRPMouse.clickStream;
import static org.engineFRP.FRP.FRPMouse.cursorPosStream;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.glClear;

/**
 * Created by TekMaTek on 01/03/2015.
 */
public class Demo4 {

    private static final String TEXT_FILE = "./res/textures/grad.png";

    private static GameObject[] sceneGameObjects;
    private static Time renderTimer = new Time(Time.THIRTY_PER_SECOND);
    private static Time pollTimer = new Time(120);
    private static Shader shader2;


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
        sceneGameObjects = new GameObject[]{
                new GameObject(new Vector3f(0.0f, 0.0f, -1.0f), MeshUtil.BuildSquareWithTexture(TEXT_FILE))
                        .mergeTranslationWith(movements())
                        .mergeTranslationWith(FRPUtil.mapArrowKeysToMovementOf(-0.1f))
        };
    }

    private static Cell<Integer> shapeClickStream() {
        return clickStream
                .filter(mouse -> mouse.button == GLFW_MOUSE_BUTTON_LEFT &&
                        mouse.action == GLFW_PRESS)
                .snapshot(cursorPosStream.hold(null), (click, cursor) -> new Tuple2<>(click, cursor))
                .map(mouse -> {
                    Dictionary<GameObject, Boolean> hits = new Hashtable<>();
                    for(GameObject t : sceneGameObjects) {
                        hits.put(t,
                                Click.isInPolygon(t.mesh.addPosAndFlipY(t.transform), mouse.b.position));
                    }
                    return hits;
                })
                .map(hitShape -> {
                    int scoreThisClick = 0;
                    Enumeration<GameObject> keys = hitShape.keys();
                    while(keys.hasMoreElements()) {
                        GameObject t = keys.nextElement();
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
                .accum(0.0f, (total, delta) -> total += delta)
                .updates()
                .map(total -> new Vector3f((float) Math.sin(0.75f * total) / 80.0f,
                        (float) Math.cos(0.75f * total) / 80.0f, 0.0f));
    }

    public void renderDemo3() {
        glClear(GL_COLOR_BUFFER_BIT);
        drawDemo3();
        glfwSwapBuffers(FRPDisplay.getWindow());
    }

    public static void drawDemo3() {
        for(GameObject gameObject : sceneGameObjects) {
            shader2.draw(gameObject);
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