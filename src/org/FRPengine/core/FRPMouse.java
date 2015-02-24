package org.FRPengine.core;

import org.FRPengine.maths.Vector2f;
import org.lwjgl.glfw.GLFWCursorPosCallback;
import org.lwjgl.glfw.GLFWMouseButtonCallback;
import sodium.StreamSink;

import java.awt.*;

import static org.lwjgl.glfw.GLFW.glfwCreateCursor;
import static org.lwjgl.glfw.GLFW.glfwSetCursorPosCallback;
import static org.lwjgl.glfw.GLFW.glfwSetMouseButtonCallback;

/**
 * Created by TekMaTek on 22/02/2015.
 */
public class FRPMouse {

    public final static StreamSink<Mouse> keyStream = new StreamSink<>();
    public final static StreamSink<Cursor> cursorPosStream = new StreamSink<>();

    private static GLFWMouseButtonCallback mouseCallback;
    private static GLFWCursorPosCallback cursorCallback;

    public static void Create() {
//        glfwCreateCursor(null, 0, 0);

        glfwSetMouseButtonCallback(FRPDisplay.GetWindow(), mouseCallback = new GLFWMouseButtonCallback() {
            @Override
            public void invoke(long window, int button, int action, int mods) {
                keyStream.send(new Mouse(window, button, action, mods));
            }
        });

        glfwSetCursorPosCallback(FRPDisplay.GetWindow(), cursorCallback = new GLFWCursorPosCallback() {
            @Override
            public void invoke(long window, double posX, double posY) {
                cursorPosStream.send( new Cursor(window, posX, posY));
            }
        });
    }

    public static void Destroy() {
        mouseCallback.release();
    }

    public static class Mouse {
        public long window;
        public int button;
        public int action;
        public int mods;

        public Mouse(long window, int button, int action, int mods){
            this.window = window;
            this.button = button;
            this.action = action;
            this.mods = mods;
        }
    }

    public static class Cursor{
        public long window;
        public Vector2f position;

        public Cursor( long window, double posX, double posY ){
            this.window = window;
            this.position = new Vector2f((float)posX, (float)posY);
        }
    }
}