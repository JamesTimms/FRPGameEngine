package org.FRPengine.core;

import org.FRPengine.core.collision.AABB;
import org.FRPengine.maths.Vector3f;
import org.lwjgl.glfw.GLFWvidmode;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GLContext;
import sodium.Stream;

import java.nio.ByteBuffer;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.GL_FALSE;
import static org.lwjgl.opengl.GL11.GL_TRUE;
import static org.lwjgl.system.MemoryUtil.NULL;

/**
 * Created by TekMaTek on 17/02/2015.
 */
public final class FRPDisplay {

    public static final int DEFAULT_WIDTH = 800;
    public static final int DEFAULT_HEIGHT = 600;
    private static final String DEFAULT_TITLE = "FRP Game Engine";

    private static Long window;//This is an object so it can be null when window fails to load or isn't yet loaded.

    private static void init() {
        if(glfwInit() != GL_TRUE)
            throw new IllegalStateException("Unable to initialize GLFW");

        glfwDefaultWindowHints();
        glfwWindowHint(GLFW_VISIBLE, GL_FALSE);
        glfwWindowHint(GLFW_RESIZABLE, GL_TRUE);

        window = glfwCreateWindow(DEFAULT_WIDTH, DEFAULT_HEIGHT, DEFAULT_TITLE, NULL, NULL);
        if(window == NULL)
            throw new RuntimeException("Failed to create the GLFW window");

        glfwMakeContextCurrent(window);
        GLContext.createFromCurrent();
        System.out.println(glfwGetVersionString());
        glfwSwapInterval(1);
        centreScreen();

    }

    public static long getWindow() {
        if(window == null) {
            init();
        }
        return window;
    }

    public static void create() {
        init();
        glfwShowWindow(window);
    }

    public static void createForTesting() {
        init();
    }


    private static void centreScreen() {
        ByteBuffer vidmode = glfwGetVideoMode(glfwGetPrimaryMonitor());
        glfwSetWindowPos(
                window,
                (GLFWvidmode.width(vidmode) - DEFAULT_WIDTH) / 2,
                (GLFWvidmode.height(vidmode) - DEFAULT_HEIGHT) / 2
        );
    }

    public static AABB setupScreenCollider() {
//        return new AABB(Vector3f.ZERO, new Vector3f(DEFAULT_WIDTH / 2.0f, DEFAULT_HEIGHT / 2.0f, 0.0f));
        return new AABB(
                new Stream<Vector3f>().hold(new Vector3f(-1.0f, -1.0f, 0.0f)),
                new Stream<Vector3f>().hold(new Vector3f(1.0f, 1.0f, 0.0f))
        );
    }

    public static void destroy() {
        glfwDestroyWindow(window);
    }

    public static boolean shouldWindowClose() {
        return glfwWindowShouldClose(FRPDisplay.window) == GL11.GL_TRUE;
    }
}
