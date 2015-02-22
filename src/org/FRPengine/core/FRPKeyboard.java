package org.FRPengine.core;

import org.lwjgl.LWJGLException;
import org.lwjgl.input.Keyboard;
import sodium.StreamSink;

/**
 * Created by TekMaTek on 22/02/2015.
 */
public class FRPKeyboard {

    public final static int NUM_KEYCODES = 256;
    private static boolean[] m_lastKeys = new boolean[NUM_KEYCODES];
    public final static StreamSink<Integer> keysPressed = new StreamSink<>();
    public final static StreamSink<Integer> keysHeld = new StreamSink<>();
    public final static StreamSink<Integer> keysUp = new StreamSink<>();

    public static void Create() {
        if(Keyboard.isCreated()) {
            return;
        }
        try {
            Keyboard.create();
        } catch(LWJGLException e) {
            e.printStackTrace();
        }
    }

    public static void Destroy() {
        Keyboard.destroy();
    }

    public static void Update() {
        for(int i = 0; i < NUM_KEYCODES; i++)
            m_lastKeys[i] = GetKeyPressed(i);
        for(int key = 0; key < 256; key++) {
            if(GetKeyPressed(key)) {
                keysPressed.send(key);
            }
            if(GetKeyHeld(key)) {
                keysHeld.send(key);
            }
            if(GetKeyUp(key)) {
                keysUp.send(key);
            }
        }
    }

    public static boolean GetKeyPressed(int keyCode) {//This needs to be push based.
        return Keyboard.isKeyDown(keyCode);
    }

    public static boolean GetKeyHeld(int keyCode) {
        return GetKeyPressed(keyCode) && !m_lastKeys[keyCode];
    }

    public static boolean GetKeyUp(int keyCode) {
        return !GetKeyPressed(keyCode) && m_lastKeys[keyCode];
    }
}
