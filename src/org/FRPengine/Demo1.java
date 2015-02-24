package org.FRPengine;

import org.FRPengine.core.FRPDisplay;
import org.FRPengine.core.FRPKeyboard;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.opengl.GLContext;
import sodium.Listener;

import java.util.ArrayList;
import java.util.List;

import static org.lwjgl.glfw.Callbacks.errorCallbackPrint;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;

/**
 * Created by TekMaTek on 17/02/2015.
 */
public class Demo1 {
    public static List<Listener> allListeners = new ArrayList<>();
    //TODO: Think of better way of storing listeners. These are only here for clean up and so Java GC doesn't remove them.

    public static void main(String[] args) {
        new Demo1();
    }

    public static GLFWErrorCallback errorCallback;//TODO: Move to error handling.

    public Demo1() {
        InitErrorHandling();
        FRPDisplay.Create();
        FRPKeyboard.Create();
        setupCloseWindow();
        printKeyDown();
        printKeyUp();
        printKeySpacebar();

        loop();
        Cleanup();
    }

    public static void Cleanup() {
        FRPKeyboard.Destroy();
        FRPDisplay.Destroy();
        KillErrorHandling();
        for(int i = 0; i < allListeners.size(); i++) {
            allListeners.get(i).unlisten();
            allListeners.set(i, null);
        }
    }

    public static void loop() {
        while(!FRPDisplay.shouldWindowClose()) {
            GLContext.createFromCurrent();
            glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT); // clear the framebuffer

            glfwSwapBuffers(FRPDisplay.GetWindow()); // swap the color buffers

            // Poll for window events. The key callback above will only be
            // invoked during this call.
            glfwPollEvents();
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

    public static void InitErrorHandling() {
        // Setup an error callback. The default implementation
        // will print the error message in System.err.
        glfwSetErrorCallback(Demo1.errorCallback = errorCallbackPrint(System.err));//TODO: move this
    }

    public static void KillErrorHandling() {
        glfwTerminate();
        errorCallback.release();//TODO: move this also
    }

}