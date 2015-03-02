package Personal;

import org.FRPengine.core.Camera;
import org.FRPengine.core.FRPDisplay;
import org.FRPengine.core.FRPKeyboard;
import org.FRPengine.core.Time;
import org.FRPengine.maths.Vector3f;
import org.FRPengine.rendering.SimpleRenderer;

import static org.lwjgl.glfw.GLFW.*;

/**
 * Created by TekMaTek on 26/02/2015.
 */
public class Demo2 {

    public static void main(String[] args) {
        new Demo2();
    }
    
    public Demo2() {
        FRPDisplay.Create();
        FRPKeyboard.Create();
        SimpleRenderer.init();
        loop();
    }

    public static void loop() {
        Camera.mainCamera.translation = FRPKeyboard.keyStream
                .filter(key -> key.action != GLFW_RELEASE
                        && isArrowKeyPressed(key.key))
                .map(key -> {
                    final float UNIT = 0.5f;
                    switch(key.key) {
                        case (GLFW_KEY_RIGHT):
                            return new Vector3f(-UNIT, 0.0f, 0.0f);
                        case (GLFW_KEY_LEFT):
                            return new Vector3f(UNIT, 0.0f, 0.0f);
                        case (GLFW_KEY_UP):
                            return new Vector3f(0.0f, -UNIT, 0.0f);
                        case (GLFW_KEY_DOWN):
                            return new Vector3f(0.0f, UNIT, 0.0f);
                        default:
                            return Vector3f.ZERO;
                    }
                })
                .accum(new Vector3f(0.0f, 0.0f, -10.0f), (currentPos, movement) -> currentPos.add(movement));

        Time renderTimer = new Time();
        Time frameTimer = new Time();
        while(!FRPDisplay.shouldWindowClose()) {
            if(frameTimer.shouldGetFrame(120)) {
                glfwPollEvents();
            }

            if(renderTimer.shouldGetFrame(60)) {
                SimpleRenderer.Render();
            }
        }
    }

    public static boolean isArrowKeyPressed(int key) {
        return key == GLFW_KEY_RIGHT
                || key == GLFW_KEY_LEFT
                || key == GLFW_KEY_UP
                || key == GLFW_KEY_DOWN;
    }
}