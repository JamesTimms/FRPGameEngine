package Personal;

import org.engineFRP.FRP.FRPDisplay;
import org.engineFRP.FRP.FRPKeyboard;
import org.engineFRP.FRP.FRPMouse;
import org.engineFRP.FRP.Time;
import org.engineFRP.core.ErrorHandling;
import org.lwjgl.opengl.GLContext;
import sodium.Listener;

import java.util.ArrayList;
import java.util.List;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.glClear;

/**
 * Created by TekMaTek on 17/02/2015.
 */
public class Demo1 {

    private static List<Listener> allListeners = new ArrayList<>();
    private static Time frameTimer;

    public static void main(String[] args) {
        new Demo1();
    }

    public Demo1() {
        ErrorHandling.Create();
        FRPDisplay.create();
        FRPKeyboard.create();
        FRPMouse.create();

        printKeyDown();
        printKeyUp();
        setupTimeIncreaseKeys();
        printMousePress();
        printCursorPosition();

        loop();
        Cleanup();
    }

    public static void loop() {
        while(!FRPDisplay.shouldWindowClose()) {
            GLContext.createFromCurrent();
            glClear(GL_COLOR_BUFFER_BIT);

            glfwSwapBuffers(FRPDisplay.getWindow());
            glfwPollEvents();
        }
    }

    public static void Cleanup() {
        FRPMouse.destroy();
        FRPKeyboard.Destroy();
        FRPDisplay.destroy();
        ErrorHandling.Destroy();
        for(int i = 0; i < allListeners.size(); i++) {
            allListeners.get(i).unlisten();
            allListeners.set(i, null);
        }
    }

    public static void printKeyDown() {
        allListeners.add(FRPKeyboard.keyEvent
                .filter(key -> key.action == GLFW_PRESS)
                .listen(key -> System.out.println("down " + key.key)));

    }

    public static void printKeyUp() {
        allListeners.add(FRPKeyboard.keyEvent
                .filter(key -> key.action == GLFW_RELEASE)
                .listen(key -> System.out.println("up " + key.key)));
    }

    public static void setupTimeIncreaseKeys() {
        allListeners.add(FRPKeyboard.keyEvent
                .filter(key -> key.key == GLFW_KEY_UP)
                .listen(key -> {
                    frameTimer.frameRate += 1;
                    System.out.println(frameTimer.frameRate);
                }));
        allListeners.add(FRPKeyboard.keyEvent
                .filter(key -> key.key == GLFW_KEY_DOWN)
                .listen(key -> {
                    ;
                    frameTimer.frameRate -= 1;
                    System.out.println(frameTimer.frameRate);
                }));
    }

    public static void printMousePress() {
        allListeners.add(FRPMouse.clickStream
                .filter(mouse -> mouse.button == GLFW_MOUSE_BUTTON_RIGHT)
                .listen(mouse -> System.out.println("Right mouse button Pressed " + mouse.button)));
    }

    public static void printCursorPosition() {
        frameTimer = new Time(Time.ONE_PER_SECOND);
        allListeners.add(FRPMouse.cursorPosStream
                .filter(x -> frameTimer.shouldGetFrame())
                .listen(cursor -> System.out.println(cursor.position.toString())));
    }
}