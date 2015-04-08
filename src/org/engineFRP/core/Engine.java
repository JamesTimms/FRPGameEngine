package org.engineFRP.core;

import org.engineFRP.FRP.*;
import org.engineFRP.rendering.JBoxDebugDraw;
import org.engineFRP.rendering.SimpleRenderer;
import org.jbox2d.callbacks.DebugDraw;

import static org.lwjgl.glfw.GLFW.glfwPollEvents;
import static org.lwjgl.glfw.GLFW.glfwSwapBuffers;
import static org.lwjgl.opengl.GL11.*;

/**
 * Created by TekMaTek on 22/03/2015.
 */
public class Engine {

    private static boolean hasBeenInitialized = false;
    private static Time renderTimer = new Time(Time.THIRTY_PER_SECOND);
    private static Time pollTimer = new Time(120);
    private static Time physics = new Time(30);
    private static Scene scene;
    private static JBoxDebugDraw jBoxDebugDraw;

    private Engine() {
    }

    private static void init() {
        if(!hasBeenInitialized) {
            FRPDisplay.create();
            FRPKeyboard.create();
            FRPMouse.create();
            SimpleRenderer.init();
            JBoxWrapper.init();//physics
            initDebug();//Visual debugging tools for JBox2D.
            hasBeenInitialized = true;
        }
    }

    public static void initDebug() {
        jBoxDebugDraw = new JBoxDebugDraw();
//        jBoxDebugDraw.setFlags(DebugDraw.e_wireframeDrawingBit);
        jBoxDebugDraw.setFlags(DebugDraw.e_aabbBit);
//        jBoxDebugDraw.setFlags(DebugDraw.e_centerOfMassBit);
//        jBoxDebugDraw.setFlags(DebugDraw.e_dynamicTreeBit);
//        jBoxDebugDraw.setFlags(DebugDraw.e_jointBit);
//        jBoxDebugDraw.setFlags(DebugDraw.e_pairBit);
//        jBoxDebugDraw.setFlags(DebugDraw.e_shapeBit);
        JBoxWrapper.world.setDebugDraw(jBoxDebugDraw);
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
            physics();
        }
    }

    private static void input() {
        if(pollTimer.shouldGetFrame()) {
            glfwPollEvents();
        }
    }

    private static void physics() {
        if(physics.shouldGetFrame()) {
            JBoxWrapper.physicsStep(physics.getDeltaTime());
        }
    }

    private static void render() {
        if(renderTimer.shouldGetFrame()) {
            glClear(GL_COLOR_BUFFER_BIT);
            JBoxWrapper.world.drawDebugData();
            scene.drawScene();
            glfwSwapBuffers(FRPDisplay.getWindow());
        }
    }
}