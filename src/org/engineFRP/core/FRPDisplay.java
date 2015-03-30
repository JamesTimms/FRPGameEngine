package org.engineFRP.core;

import org.engineFRP.FRP.FRPWinSize;
import org.engineFRP.Physics.collision.AABB;
import org.engineFRP.maths.Vector2f;
import org.engineFRP.maths.Vector3f;
import org.lwjgl.glfw.GLFWWindowSizeCallback;
import org.lwjgl.glfw.GLFWvidmode;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GLContext;
import sodium.Cell;
import sodium.Listener;
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
    public static final FRPWinSize winResizeStream = new FRPWinSize();
    private static final String DEFAULT_TITLE = "FRP Game Engine";
    public static Cell<Vector2f> windowSize;
    private static GLFWWindowSizeCallback newCallback;

    private static Long window;//This is an object so it can be null when window fails to load or isn't yet loaded.
    private static Long window2;
    private static Listener exitWindow;

    private static void init() {
        if(glfwInit() != GL_TRUE)
            throw new IllegalStateException("Unable to initialize GLFW");

        glfwDefaultWindowHints();
        glfwWindowHint(GLFW_VISIBLE, GL_FALSE);
        glfwWindowHint(GLFW_RESIZABLE, GL_TRUE);

        window = glfwCreateWindow(DEFAULT_WIDTH, DEFAULT_HEIGHT, DEFAULT_TITLE, NULL, NULL);
//        window2 = glfwCreateWindow(1800, 1400, DEFAULT_TITLE, NULL, NULL);
        if(window == NULL)
            throw new RuntimeException("Failed to create the GLFW window");
        windowSize = winResizeStream
                .map(winSize -> {//TODO: move this function into FRPWinSize.
                    GL11.glViewport(0, 0, winSize.width, winSize.height);
                    return winSize;
                })
                .map(winSize -> new Vector2f(winSize.width, winSize.height))
                .hold(new Vector2f(DEFAULT_WIDTH, DEFAULT_HEIGHT));

        glfwMakeContextCurrent(window);
        GLContext.createFromCurrent();
        System.out.println(glfwGetVersionString());
        glfwSwapInterval(1);
        centreScreen();
        setupCloseWindow();

        glfwSetWindowSizeCallback(FRPDisplay.getWindow(), newCallback = new GLFWWindowSizeCallback() {
            @Override
            public void invoke(long window, int width, int height) {
                winResizeStream.send(new FRPWinSize.Resize(window, width, height));
            }
        });
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
        return new AABB(
                new Stream<Vector3f>().hold(new Vector3f(-0.6f, -0.6f, 0.0f)),
                new Stream<Vector3f>().hold(new Vector3f(0.6f, 0.6f, 0.0f))
        );
    }

    public static void setupCloseWindow() {
        exitWindow = FRPKeyboard.keyEvent
                .filter(key -> key.key == GLFW_KEY_ESCAPE && key.action == GLFW_RELEASE)
                .listen(key -> glfwSetWindowShouldClose(key.window, GL_TRUE));
    }

    public static void destroy() {
        glfwDestroyWindow(window);
    }

    public static boolean shouldWindowClose() {
        return glfwWindowShouldClose(FRPDisplay.window) == GL11.GL_TRUE;
    }
}
