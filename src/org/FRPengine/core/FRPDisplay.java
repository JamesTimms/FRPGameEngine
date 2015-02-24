package org.FRPengine.core;

import org.FRPengine.Demo1;
import org.lwjgl.glfw.GLFWvidmode;
import org.lwjgl.opengl.GL11;

import java.nio.ByteBuffer;

import static org.lwjgl.glfw.Callbacks.errorCallbackPrint;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.GL_FALSE;
import static org.lwjgl.opengl.GL11.GL_TRUE;
import static org.lwjgl.system.MemoryUtil.NULL;

/**
 * Created by TekMaTek on 17/02/2015.
 */
public final class FRPDisplay {

    private static final int DEFAULT_WIDTH = 800;
    private static final int DEFAULT_HEIGHT = 600;
    private static final String DEFAULT_TITLE = "FRP Game Engine";

    private static Long window;//This is an object so it can be null when window fails to load or isn't yet loaded.

    //Code taken from LWJGL3 guide http://www.lwjgl.org/guide
    private static void init() {

        // Initialize GLFW. Most GLFW functions will not work before doing this.
        if(glfwInit() != GL_TRUE)
            throw new IllegalStateException("Unable to initialize GLFW");

        // Configure our window
        glfwDefaultWindowHints(); // optional, the current window hints are already the default
        glfwWindowHint(GLFW_VISIBLE, GL_FALSE); // the window will stay hidden after creation
        glfwWindowHint(GLFW_RESIZABLE, GL_TRUE); // the window will be resizable

        // Create the window
        window = glfwCreateWindow(DEFAULT_WIDTH, DEFAULT_HEIGHT, DEFAULT_TITLE, NULL, NULL);
        if(window == NULL)
            throw new RuntimeException("Failed to create the GLFW window");

        // Get the resolution of the primary monitor
        ByteBuffer vidmode = glfwGetVideoMode(glfwGetPrimaryMonitor());
        // Center our window
        glfwSetWindowPos(
                window,
                (GLFWvidmode.width(vidmode) - DEFAULT_WIDTH) / 2,
                (GLFWvidmode.height(vidmode) - DEFAULT_HEIGHT) / 2
        );

        // Make the OpenGL context current
        glfwMakeContextCurrent(window);
        // Enable v-sync
        glfwSwapInterval(1);

        // Make the window visible
        glfwShowWindow(window);
    }

    public static long GetWindow() {
        if(window == null) {
            init();
        }
        return window;
    }

    public static void Create() {
        init();
    }

    public static void Destroy() {
        glfwDestroyWindow(window);
    }

    public static boolean shouldWindowClose( ) {
        return glfwWindowShouldClose(FRPDisplay.window) == GL11.GL_TRUE;
    }

}
