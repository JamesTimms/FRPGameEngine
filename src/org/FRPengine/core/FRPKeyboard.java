package org.FRPengine.core;

import org.lwjgl.glfw.GLFWKeyCallback;
import sodium.CellSink;
import sodium.Stream;
import sodium.StreamSink;

import static org.lwjgl.glfw.GLFW.*;

/**
 * Created by TekMaTek on 22/02/2015.
 */
public class FRPKeyboard {

//    public final static StreamSink<Key> keyStream = new StreamSink<>();
    private static CellSink<Key> keyEvent = new CellSink<>(new Key(0,0,0,0,0));
    private final static StreamSink<Integer> pollStream = new StreamSink<>();

    private static GLFWKeyCallback keyCallback;

    public static void Create() {
        // Setup a key callback. It will be called every time a key is pressed, repeated or released.
        glfwSetKeyCallback(FRPDisplay.GetWindow(), keyCallback = new GLFWKeyCallback() {
            @Override
            public void invoke(long window, int key, int scancode, int action, int mods) {
                System.out.println(key);
                keyEvent.send(new Key(window, key, scancode, action, mods));
                //TODO: Figure out if can merge the key being send upwards?
            }
        });
    }

    public static void pollAtFrameRate(int frameRate) {
        FRPKeyboard.pollStream.send(frameRate);//Deals with previously sent keys and grabs the next keys?
    }

    public static Stream<Key> setupPollStream(Stream<Integer> pollStream){
        return Time.stream(pollStream)
                .snapshot(keyEvent);
    }

    public static void fakeKeyEvent(Key key){
        keyEvent.send(key);
    }

    public static void pollFakeKeyEvent(Key key){
        pollFakeKeyEvent(key, 0);
    }

    public static void pollFakeKeyEvent(Key key, int frameRate){
        fakeKeyEvent(key);
        pollStream.send(frameRate);
    }

    public static Stream<Key> setupPollStream(){
        return setupPollStream( pollStream );
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
