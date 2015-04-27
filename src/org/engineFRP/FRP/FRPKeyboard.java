package org.engineFRP.FRP;

import org.lwjgl.glfw.GLFWKeyCallback;
import sodium.*;

import java.util.*;

import static org.lwjgl.glfw.GLFW.*;

/**
 * Created by TekMaTek on 22/02/2015.
 */
public class FRPKeyboard {

    public static final StreamSink<Key> keyEvent = new StreamSink<>();
    public static final StreamSink<Key> keyEventSmooth = new StreamSink<>();
    private static GLFWKeyCallback keyCallback;

    private static final Cell<Hashtable<Integer, Key>> smoothKeysDownHack = keyEvent
            .accum(new Hashtable<>(), (key, hashtable) -> {
                hashtable.put(key.code, key);
                return hashtable;
            });//Add key events to the hashtable when they happen.

    public static void create() {
        // Setup a code callback. It will be called every time a code is pressed, repeated or released.
        glfwSetKeyCallback(FRPDisplay.getWindow(), keyCallback = new GLFWKeyCallback() {
            @Override
            public void invoke(long window, int key, int scancode, int action, int mods) {
                keyEvent.send(new Key(window, key, scancode, action, mods));
            }
        });
    }

    public static void fakeKeyEvent(Key key) {
        keyEvent.send(key);
    }

    /**
     * lwjgl has a delay between key pressed and key down events. This function makes it seem the delay doesn't exist
     * by re-emitting the event on a different stream between a key pressed and key released event for each key.
     */
    //TODO: Figure out how to implement this with only Sodium streams and using keyEvent stream
    public static void hackedSmoothKeys() {
        //using Java's streams here to re emit every key that is logged as being down.
        smoothKeysDownHack.sample().entrySet().stream()
                .filter(t -> isKeySmoothlyDown(t.getValue().action))
                .forEach(t -> keyEventSmooth.send(t.getValue()));
    }

    public static boolean isKeySmoothlyDown(int keyAction) {
        return keyAction != GLFW_RELEASE;
    }

    public static void Destroy() {
        keyCallback.release();
    }

    public static class Key {
        public long window;
        public int code;
        public int scancode;
        public int action;
        public int mods;

        public Key(long window, int code, int scancode, int action, int mods) {
            this.window = window;
            this.code = code;
            this.scancode = scancode;
            this.action = action;
            this.mods = mods;
        }

        public Key(int code, int action) {
            this(0l, code, 0, action, 0);
        }
    }

    public static boolean isArrowKeyPressed(int key) {
        return key == GLFW_KEY_RIGHT
                || key == GLFW_KEY_LEFT
                || key == GLFW_KEY_UP
                || key == GLFW_KEY_DOWN;
    }
}
