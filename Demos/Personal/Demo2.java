package Personal;

import org.FRPengine.core.*;
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
        Camera.mainCamera.translation = FRPUtil.mapArrowKeysToMovementOf(0.5f)
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
}