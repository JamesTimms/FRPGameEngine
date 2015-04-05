package org.engineFRP.FRP;

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

    //TODO: The height width ratio is distorting the glContext.
    public static final int DEFAULT_WIDTH = 600;
    public static final int DEFAULT_HEIGHT = 600;
    public static final FRPWinSize winResizeStream = new FRPWinSize();
    private static final String DEFAULT_TITLE = "FRP Game Engine";
    public static Cell<Vector2f> windowSize;
    private static GLFWWindowSizeCallback newCallback;

    private static Long window;//This is an object so it can be null when window fails to load or isn't yet loaded.
    private static Listener exitWindow;

    private static void init() {
        if(glfwInit() != GL_TRUE)
            throw new IllegalStateException("Unable to initialize GLFW");

        glfwDefaultWindowHints();
        glfwWindowHint(GLFW_VISIBLE, GL_FALSE);
        glfwWindowHint(GLFW_RESIZABLE, GL_TRUE);

        window = glfwCreateWindow(DEFAULT_WIDTH, DEFAULT_HEIGHT, DEFAULT_TITLE, NULL, NULL);
        if(window == NULL)
            throw new RuntimeException("Failed to create the GLFW window");
        windowSize = setupGLContext();

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

    private static Cell<Vector2f> setupGLContext() {
        return winResizeStream
                .map(w -> {
                    //want to have a centered 1:1 openGL context;
                    int smallerSize = w.width < w.height ? w.width : w.height;
                    int offsetY = w.width < w.height ? (w.height - w.width) / 2 : 0;
                    int offsetX = w.width > w.height ? (w.width - w.height) / 2 : 0;
                    System.out.println(w.height + " " + w.width);
                    GL11.glViewport(offsetX, offsetY, smallerSize, smallerSize);
                    return w;
                })
                .map(winSize -> new Vector2f(winSize.width, winSize.height))
                .hold(new Vector2f(DEFAULT_WIDTH, DEFAULT_HEIGHT));
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