package org.FRPengine.core;

import org.lwjgl.glfw.GLFWKeyCallback;
import sodium.StreamSink;

import static org.lwjgl.glfw.GLFW.*;

/**
 * Created by TekMaTek on 22/02/2015.
 */
public class FRPKeyboard {

    public static final StreamSink<Key> keyEvent = new StreamSink<>();
    private static GLFWKeyCallback keyCallback;

    public static void Create() {
        // Setup a key callback. It will be called every time a key is pressed, repeated or released.
        glfwSetKeyCallback(FRPDisplay.getWindow(), keyCallback = new GLFWKeyCallback() {
            @Override
            public void invoke(long window, int key, int scancode, int action, int mods) {
                keyEvent.send(new Key(window, key, scancode, action, mods));
            }
        });
    }

    public static void fakeKeyEvent(Key key){
        keyEvent.send(key);
    }

    public static void Destroy() {
        keyCallback.release();
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

        public Key(int key, int action) {
            this(0l, key, 0, action, 0);
        }
    }

    public static boolean isArrowKeyPressed(int key) {
        return key == GLFW_KEY_RIGHT
                || key == GLFW_KEY_LEFT
                || key == GLFW_KEY_UP
                || key == GLFW_KEY_DOWN;
    }
}
