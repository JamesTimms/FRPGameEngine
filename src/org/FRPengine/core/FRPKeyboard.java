package org.FRPengine.core;

import org.lwjgl.glfw.GLFWKeyCallback;
import sodium.StreamSink;

import static org.lwjgl.glfw.GLFW.glfwSetKeyCallback;

/**
 * Created by TekMaTek on 22/02/2015.
 */
public class FRPKeyboard {

    public final static StreamSink<Key> keyStream = new StreamSink<>();

    private static GLFWKeyCallback keyCallback;

    public static void Create() {
        // Setup a key callback. It will be called every time a key is pressed, repeated or released.
        glfwSetKeyCallback(FRPDisplay.GetWindow(), keyCallback = new GLFWKeyCallback() {
            @Override
            public void invoke(long window, int key, int scancode, int action, int mods) {
                keyStream.send(new Key(window, key, scancode, action, mods));
            }
        });
    }

    public static void Destroy() {
        keyCallback.release();
    }

    public static void Subscribe() {

    }

    public static class Key {
        public long window;
        public int key;
        public int scancode;
        public int action;
        public int mods;

        public Key(long window, int key, int scancode, int action, int mods) {
            this.window = window;
            this.key = key;
            this.scancode = scancode;
            this.action = action;
            this.mods = mods;
        }
    }
}
