package org.FRPengine.core;

import org.lwjgl.glfw.GLFWvidmode;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GLContext;

import java.nio.ByteBuffer;

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
//    static GLFWFramebufferSizeCallback fbCallback;

    //Code taken from LWJGL3 guide http://www.lwjglTests.org/guide
    private static void init() {

        if(glfwInit() != GL_TRUE)
            throw new IllegalStateException("Unable to initialize GLFW");

        glfwDefaultWindowHints();
        glfwWindowHint(GLFW_OPENGL_PROFILE, GLFW_OPENGL_CORE_PROFILE);
        glfwWindowHint(GLFW_OPENGL_FORWARD_COMPAT, GL_TRUE);
        glfwWindowHint(GLFW_CONTEXT_VERSION_MAJOR, 4);
        glfwWindowHint(GLFW_CONTEXT_VERSION_MINOR, 3);
        glfwWindowHint(GLFW_VISIBLE, GL_FALSE);
        glfwWindowHint(GLFW_RESIZABLE, GL_TRUE);

        window = glfwCreateWindow(DEFAULT_WIDTH, DEFAULT_HEIGHT, DEFAULT_TITLE, NULL, NULL);
        if(window == NULL)
            throw new RuntimeException("Failed to create the GLFW window");

        glfwMakeContextCurrent(window);
        GLContext.createFromCurrent();
        glfwSwapInterval(1);
        CentreScreen();

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

    public static void CentreScreen() {
        // Get the resolution of the primary monitor
        ByteBuffer vidmode = glfwGetVideoMode(glfwGetPrimaryMonitor());
        // Center our window
        glfwSetWindowPos(
                window,
                (GLFWvidmode.width(vidmode) - DEFAULT_WIDTH) / 2,
                (GLFWvidmode.height(vidmode) - DEFAULT_HEIGHT) / 2
        );

    }

    public static void Destroy() {
        glfwDestroyWindow(window);
    }

    public static boolean shouldWindowClose() {
        return glfwWindowShouldClose(FRPDisplay.window) == GL11.GL_TRUE;
    }
}
