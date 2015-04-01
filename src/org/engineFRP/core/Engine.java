package org.engineFRP.core;

import org.engineFRP.FRP.FRPTime;
import org.engineFRP.FRP.Time;
import org.engineFRP.rendering.SimpleRenderer;

import static org.lwjgl.glfw.GLFW.glfwPollEvents;
import static org.lwjgl.glfw.GLFW.glfwSwapBuffers;
import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.glClear;

/**
 * Created by TekMaTek on 22/03/2015.
 */
public class Engine {

    private static boolean hasBeenInitialized = false;
    private static Time renderTimer = new Time(Time.THIRTY_PER_SECOND);
    private static Time pollTimer = new Time(120);
    private static Scene scene;

    private Engine() {
    }

    private static void init() {
        if(!hasBeenInitialized) {
            FRPDisplay.create();
            FRPKeyboard.create();
            FRPMouse.create();
            SimpleRenderer.init();
            hasBeenInitialized = true;
        }
    }

    public static void runGame(Game game) {
        //Just some thoughts on how to better implement the time loops.
        //While(shouldStillPlayGame){
        //  sleepOrFreeThread(forSmallestTimeTillNextUpdate);//For example sleep for 1/30 of a second.
        //  processNextActionRequired();//Not sure how this will work for simultaneous actions.
        //}
        init();
        scene = game.setupScene();
        while(!FRPDisplay.shouldWindowClose()) {
            input();
            FRPTime.pollStreams();//Trigger all FRP streams.
            render();
        }
    }

    private static void input() {
        if(pollTimer.shouldGetFrame()) {
            glfwPollEvents();
        }
    }

    private static void render() {
        if(renderTimer.shouldGetFrame()) {
            glClear(GL_COLOR_BUFFER_BIT);
            scene.drawScene();
            glfwSwapBuffers(FRPDisplay.getWindow());
        }
    }
}