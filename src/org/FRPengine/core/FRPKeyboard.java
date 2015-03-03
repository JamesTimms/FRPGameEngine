package org.FRPengine.core;

import org.FRPengine.maths.Vector3f;
import org.lwjgl.glfw.GLFWKeyCallback;
import sodium.Stream;
import sodium.StreamSink;

import static org.lwjgl.glfw.GLFW.*;

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

    public static Stream<Vector3f> mapArrowKeysToMovementOf(float moveAmount) {
        return FRPKeyboard.keyStream
                .filter(key -> key.action != GLFW_RELEASE
                        && FRPKeyboard.isArrowKeyPressed(key.key))
                .map(key -> {
                    switch(key.key) {
                        case (GLFW_KEY_RIGHT):
                            return new Vector3f(-moveAmount, 0.0f, 0.0f);
                        case (GLFW_KEY_LEFT):
                            return new Vector3f(moveAmount, 0.0f, 0.0f);
                        case (GLFW_KEY_UP):
                            return new Vector3f(0.0f, -moveAmount, 0.0f);
                        case (GLFW_KEY_DOWN):
                            return new Vector3f(0.0f, moveAmount, 0.0f);
                        default:
                            return Vector3f.ZERO;
                    }
                });
    }

    public static boolean isArrowKeyPressed(int key) {
        return key == GLFW_KEY_RIGHT
                || key == GLFW_KEY_LEFT
                || key == GLFW_KEY_UP
                || key == GLFW_KEY_DOWN;
    }
}
