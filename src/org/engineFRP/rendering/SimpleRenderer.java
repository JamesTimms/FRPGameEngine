package org.engineFRP.rendering;

import org.engineFRP.core.Camera;
import org.engineFRP.maths.Vector3f;

import static org.lwjgl.opengl.GL11.*;

/**
 * Created by TekMaTek on 26/02/2015.
 */
public class SimpleRenderer {

    public static void init() {
        System.out.println(RenderingUtil.GetOpenGLVersion());
        glClearColor(0.1686f, 0.1686f, 0.1686f, 1.0f);

        glEnable(GL_BLEND);
        glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
    }
}