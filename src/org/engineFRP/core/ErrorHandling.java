package org.engineFRP.core;

import org.lwjgl.glfw.Callbacks;
import org.lwjgl.glfw.GLFWErrorCallback;

import static org.lwjgl.glfw.GLFW.*;

/**
 * Created by TekMaTek on 26/02/2015.
 */
public class ErrorHandling {

    public static GLFWErrorCallback errorCallback;

    public static void Create() {
        // Setup an error callback. The default implementation
        // will print the error message in System.err.
        glfwSetErrorCallback(errorCallback = Callbacks.errorCallbackPrint(System.err));
    }

    public static void Destroy() {
        glfwTerminate();
        errorCallback.release();
    }
}
