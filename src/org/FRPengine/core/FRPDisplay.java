package org.FRPengine.core;

import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;

/**
 * Created by TekMaTek on 17/02/2015.
 */
public class FRPDisplay {

    private static final int DEFAULT_WIDTH = 800;
    private static final int DEFAULT_HEIGHT = 600;

    public void CreateDisplay() {
        Display.setTitle("My FRP Game Engine");
        try {
            Display.setDisplayMode(new DisplayMode(DEFAULT_WIDTH, DEFAULT_HEIGHT));
            Display.create();
        } catch(LWJGLException ex) {
            ex.printStackTrace();
        }
    }

}
