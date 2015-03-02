package org.FRPengine.rendering;

import org.FRPengine.core.FRPDisplay;
import org.FRPengine.rendering.shaders.ComplicatedShader;
import org.FRPengine.rendering.shaders.Shader;

import static org.lwjgl.glfw.GLFW.glfwSwapBuffers;
import static org.lwjgl.opengl.GL11.*;

/**
 * Created by TekMaTek on 26/02/2015.
 */
public class SimpleRenderer {

    private static Mesh cube = MeshUtil.BuildSimpleCube();
    static Shader shader = new ComplicatedShader();

    public static void Render() {
        glClear(GL_COLOR_BUFFER_BIT);
        draw();
        glfwSwapBuffers(FRPDisplay.GetWindow());
    }

    public static void RenderDemo3() {
        glClear(GL_COLOR_BUFFER_BIT);
        drawDemo3();
        glfwSwapBuffers(FRPDisplay.GetWindow());
    }

    public static void init() {
        System.out.println(RenderingUtil.GetOpenGLVersion());
        glClearColor(0.3f, 0.3f, 0.3f, 1.0f);
    }

    public static void draw() {
        shader.draw(cube);
    }

    public static void drawDemo3() {
    }
}