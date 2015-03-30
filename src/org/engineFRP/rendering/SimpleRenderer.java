package org.engineFRP.rendering;

import org.engineFRP.core.FRPDisplay;
import org.engineFRP.rendering.shaders.CubeShader;
import org.engineFRP.rendering.shaders.Shader;

import static org.lwjgl.glfw.GLFW.glfwSwapBuffers;
import static org.lwjgl.opengl.GL11.*;

/**
 * Created by TekMaTek on 26/02/2015.
 */
public class SimpleRenderer {

//    private static Transform cube = new Transform(Vector3f.ZERO, MeshUtil.BuildSimpleCube());
    static Shader shader;

    public static void Render() {
        shader = new CubeShader();
        glClear(GL_COLOR_BUFFER_BIT);
        draw();
        glfwSwapBuffers(FRPDisplay.getWindow());
    }

    public static void init() {
        System.out.println(RenderingUtil.GetOpenGLVersion());
        glClearColor(0.3f, 0.3f, 0.3f, 1.0f);

        glEnable (GL_BLEND);
        glBlendFunc (GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
    }

    public static void draw() {
//        shader.draw(cube);
    }
}