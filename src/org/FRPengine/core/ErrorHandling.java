package org.FRPengine.core;

import org.lwjgl.glfw.GLFWErrorCallback;

import static org.lwjgl.glfw.Callbacks.errorCallbackPrint;
import static org.lwjgl.glfw.GLFW.glfwSetErrorCallback;
import static org.lwjgl.glfw.GLFW.glfwTerminate;

/**
 * Created by TekMaTek on 26/02/2015.
 */
public class ErrorHandling {

    public static GLFWErrorCallback errorCallback;

    public static void Init() {
        // Setup an error callback. The default implementation
        // will print the error message in System.err.
        glfwSetErrorCallback(errorCallback = errorCallbackPrint(System.err));
    }

    public static void Destroy() {
        glfwTerminate();
        errorCallback.release();
    }
}
