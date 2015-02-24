package org.FRPengine.core;

import org.lwjgl.glfw.GLFWMouseButtonCallback;
import sodium.StreamSink;

import static org.lwjgl.glfw.GLFW.glfwSetMouseButtonCallback;

/**
 * Created by TekMaTek on 22/02/2015.
 */
public class FRPMouse {

    public final static StreamSink<Mouse> keyStream = new StreamSink<>();

    private static GLFWMouseButtonCallback mouseCallback;

    public static void Create() {
        // Setup a key callback. It will be called every time a key is pressed, repeated or released.
        glfwSetMouseButtonCallback(FRPDisplay.GetWindow(), mouseCallback = new GLFWMouseButtonCallback() {
            @Override
            public void invoke(long window, int button, int action, int mods) {
                keyStream.send(new Mouse(window, button, action, mods));
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

        public Mouse( long window, int button, int action, int mods){
            this.window = window;
            this.button = button;
            this.action = action;
            this.mods = mods;
        }
    }
}