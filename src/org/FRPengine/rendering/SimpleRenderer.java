package org.FRPengine.rendering;

import org.FRPengine.core.FRPDisplay;
import org.FRPengine.core.Transform;
import org.FRPengine.maths.Vector3f;
import org.FRPengine.rendering.shaders.ComplicatedShader;
import org.FRPengine.rendering.shaders.Shader;

import static org.lwjgl.glfw.GLFW.glfwSwapBuffers;
import static org.lwjgl.opengl.GL11.*;

/**
 * Created by TekMaTek on 26/02/2015.
 */
public class SimpleRenderer {

    private static Transform cube = new Transform(Vector3f.ZERO, MeshUtil.BuildSimpleCube());
    static Shader shader;

    public static void Render() {
        shader = new ComplicatedShader();
        glClear(GL_COLOR_BUFFER_BIT);
        draw();
        glfwSwapBuffers(FRPDisplay.GetWindow());
    }

    public static void init() {
        System.out.println(RenderingUtil.GetOpenGLVersion());
        glClearColor(0.3f, 0.3f, 0.3f, 1.0f);
    }

    public static void draw() {
        shader.draw(cube);
    }
}