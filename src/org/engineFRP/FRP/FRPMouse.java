package org.engineFRP.FRP;

import org.engineFRP.maths.MathUtil;
import org.engineFRP.maths.Vector2f;
import org.lwjgl.glfw.GLFWCursorPosCallback;
import org.lwjgl.glfw.GLFWMouseButtonCallback;
import sodium.StreamSink;

import static org.lwjgl.glfw.GLFW.*;

/**
 * Created by TekMaTek on 22/02/2015.
 */
public class FRPMouse {

    public final static StreamSink<Mouse> clickStream = new StreamSink<>();
    public final static StreamSink<Cursor> cursorPosStream = new StreamSink<>();

    private static GLFWMouseButtonCallback mouseCallback;
    private static GLFWCursorPosCallback cursorCallback;

    public static void create() {
        glfwSetMouseButtonCallback(FRPDisplay.getWindow(), mouseCallback = new GLFWMouseButtonCallback() {
            @Override
            public void invoke(long window, int button, int action, int mods) {
                clickStream.send(new Mouse(window, button, action, mods));
            }
        });

        glfwSetCursorPosCallback(FRPDisplay.getWindow(), cursorCallback = new GLFWCursorPosCallback() {
            @Override
            public void invoke(long window, double posX, double posY) {
                cursorPosStream.send(new Cursor(window, posX, posY));
            }
        });
    }

    public static Vector2f screenToWorldSpace(Vector2f screenSpace) {
        Vector2f windowSize = FRPDisplay.windowSize.sample();
        return new Vector2f(
                MathUtil.RangeConvert(0, windowSize.x, -1.0f, 1.0f, screenSpace.x),
                MathUtil.RangeConvert(0, windowSize.y, -1.0f, 1.0f, screenSpace.y)
        );
    }

    public static void destroy() {
        mouseCallback.release();
        cursorCallback.release();
    }

    public static class Mouse {
        public long window;
        public int button;
        public int action;
        public int mods;

        public Mouse(long window, int button, int action, int mods) {
            this.window = window;
            this.button = button;
            this.action = action;
            this.mods = mods;
        }
    }

    public static class Cursor {
        public long window;
        public Vector2f position;

        public Cursor(long window, double posX, double posY) {
            this.window = window;
            this.position = new Vector2f((float) posX, (float) posY);
        }
    }
}