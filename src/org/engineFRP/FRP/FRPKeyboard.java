package org.engineFRP.FRP;

import org.lwjgl.glfw.GLFWKeyCallback;
import sodium.*;
import sodium.Stream;

import java.util.*;

import static org.lwjgl.glfw.GLFW.*;

/**
 * Created by TekMaTek on 22/02/2015.
 */
public class FRPKeyboard {

    public static final StreamSink<Key> keyEvent = new StreamSink<>();
    private static GLFWKeyCallback keyCallback;

//    private static final Cell<Hashtable<Integer, Key>> c = keyEvent
//            .accum(new Hashtable<>(), (key, hashtable) -> {
//                hashtable.put(key.code, key);
//                return hashtable;
//            });//Add key events to the hashtable when they happen.
//
//    //Need to log keys pressed and remit them between press and release.
//    public static Stream<Hashtable<Integer, Key>> keyEventHackPoll =
//            Cell.switchS(new Stream<Stream<Hashtable<Integer, Key>>>()
//                            .hold(FRPTime.stream(FRPTime.THIRTY_PER_SECOND)
//                                            .snapshot(c,
//                                                    (a, b) -> b)
//                                            .filterNotNull()
//                            )
//            );//we want to split the stream to multiple stream based on the number of logged keys.

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

//    public static void remitKeys() {
//        //using Java's streams here to re emit every key that is logged as being down.
//        c.sample().entrySet().stream()
//                .filter(t -> isKeySmoothlyDown(t.getValue().action))
//                .forEach(t -> keyEvent.send(t.getValue()));
//    }

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
