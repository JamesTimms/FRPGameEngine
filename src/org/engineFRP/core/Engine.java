package org.engineFRP.core;

import org.engineFRP.FRP.FRPTime;
import org.engineFRP.rendering.SimpleRenderer;

/**
 * Created by TekMaTek on 22/03/2015.
 */
public class Engine {

    private static Engine gameEngine;

    private Engine(Game game) {
        FRPDisplay.create();
        FRPKeyboard.create();
        FRPMouse.create();
        SimpleRenderer.init();
        runGame(game);
    }

    private static void runGame(Game game) {
        //Just some thoughts on how to better implement the time loops.
        //While(shouldStillPlayGame){
        //  sleepOrFreeThread(forSmallestTimeTillNextUpdate);//For example sleep for 1/30 of a second.
        //  processNextActionRequired();//Not sure how this will work for simultaneous actions.
        //}
        game.setupScene();
        while(!FRPDisplay.shouldWindowClose()) {
            game.input();
            FRPTime.pollStreams();
            game.render();
        }
    }

    public static final void StartEngine(Game game) throws Exception {
        if(gameEngine == null) {
            gameEngine = new Engine(game);
        } else {
            throw new Exception("Can't start multiple games with this game engine.");
        }
    }
}