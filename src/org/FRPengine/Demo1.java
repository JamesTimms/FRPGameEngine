package org.FRPengine;

import org.FRPengine.core.*;
import org.FRPengine.rendering.SimpleRenderer;
import sodium.Listener;

import java.util.ArrayList;
import java.util.List;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.GL_TRUE;

/**
 * Created by TekMaTek on 17/02/2015.
 */
public class Demo1 {

    public static List<Listener> allListeners = new ArrayList<>();
    //TODO: Think of better way of storing listeners. These are only here for clean up and so Java GC doesn't remove them.

    public static void main(String[] args) {
        new Demo1();
    }

    public Demo1() {
        ErrorHandling.Init();

        FRPDisplay.Create();
        FRPKeyboard.Create();
        FRPMouse.Create();

        setupCloseWindow();
        printKeyDown();
        printKeyUp();
        printKeySpacebar();
        printMousePress();
        printCursorPosition();

        SimpleRenderer.loop();
        Cleanup();
    }

    public static void Cleanup() {
        FRPMouse.Destroy();
        FRPKeyboard.Destroy();
        FRPDisplay.Destroy();
        ErrorHandling.Destroy();
        for(int i = 0; i < allListeners.size(); i++) {
            allListeners.get(i).unlisten();
            allListeners.set(i, null);
        }
    }

    public static void setupCloseWindow() {
        allListeners.add(FRPKeyboard.keyStream
                .filter(key -> key.key == GLFW_KEY_ESCAPE && key.action == GLFW_RELEASE)
                .listen(key -> glfwSetWindowShouldClose(key.window, GL_TRUE)));
    }

    public static void printKeyDown() {
        allListeners.add(FRPKeyboard.keyStream
                .filter(key -> key.action == GLFW_PRESS)
                .listen(key -> System.out.println("down " + key.key)));

    }

    public static void printKeyUp() {
        allListeners.add(FRPKeyboard.keyStream
                .filter(key -> key.action == GLFW_RELEASE)
                .listen(key -> System.out.println("up " + key.key)));

    }

    public static void printKeySpacebar() {
        allListeners.add(FRPKeyboard.keyStream
                .filter(key -> key.key == GLFW_KEY_SPACE)
                .listen(key -> System.out.println("SPACE BAR!!!")));

    }

    public static void printMousePress() {
        allListeners.add(FRPMouse.keyStream
                .filter(mouse -> mouse.button == GLFW_MOUSE_BUTTON_RIGHT)
                .listen(mouse -> System.out.println("Right mouse button Pressed " + mouse.button)));
    }

    public static void printCursorPosition() {
        allListeners.add(FRPMouse.cursorPosStream
                .filter(x -> Time.readyForFrameRate(Time.ONE_PER_SECOND))
                .listen(cursor -> System.out.println(cursor.position.toString())));
    }

}