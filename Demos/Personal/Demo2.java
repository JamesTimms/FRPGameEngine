package Personal;

import org.engineFRP.FRP.Time;
import org.engineFRP.core.Camera;
import org.engineFRP.core.FRPDisplay;
import org.engineFRP.core.FRPKeyboard;
import org.engineFRP.core.FRPUtil;
import org.engineFRP.maths.Vector3f;
import org.engineFRP.rendering.SimpleRenderer;

import static org.lwjgl.glfw.GLFW.glfwPollEvents;

/**
 * Created by TekMaTek on 26/02/2015.
 */
public class Demo2 {

    public static void main(String[] args) {
        new Demo2();
    }

    public Demo2() {
        FRPDisplay.create();
        FRPKeyboard.create();
        SimpleRenderer.init();
        loop();
    }

    public static void loop() {
        Camera.mainCamera.translation = FRPUtil.mapArrowKeysToMovementOf(0.5f)
                .accum(new Vector3f(0.0f, 0.0f, -10.0f), Vector3f::add);

        Time renderTimer = new Time(60);
        Time frameTimer = new Time(120);
        while(!FRPDisplay.shouldWindowClose()) {
            if(frameTimer.shouldGetFrame()) {
                glfwPollEvents();
            }

            if(renderTimer.shouldGetFrame()) {
                SimpleRenderer.Render();
            }
        }
    }
}