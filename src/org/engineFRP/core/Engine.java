package org.engineFRP.core;

import org.engineFRP.FRP.*;
import org.engineFRP.Physics.JBoxWrapper;
import org.engineFRP.Physics.JBoxDebugDraw;
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

    private Engine() {
    }

    private static void init() {
        if(!hasBeenInitialized) {
            FRPDisplay.create();
            FRPKeyboard.create();
            FRPMouse.create();
            SimpleRenderer.init();
            JBoxWrapper.init();//physics
//            initDebug();//Visual debugging tools for JBox2D.//TODO: Fix the debug drawing bug
            hasBeenInitialized = true;
        }
    }

    public static void initDebug() {
        JBoxDebugDraw jBoxDebugDraw = new JBoxDebugDraw();
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
//            JBoxWrapper.world.drawDebugData();//Disabled due to debug drawing bug.
            scene.drawScene();
            glfwSwapBuffers(FRPDisplay.getWindow());
        }
    }
}