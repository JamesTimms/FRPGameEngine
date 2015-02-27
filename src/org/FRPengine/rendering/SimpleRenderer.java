package org.FRPengine.rendering;

import org.FRPengine.core.FRPDisplay;
import org.FRPengine.rendering.shaders.BasicShader;
import org.lwjgl.opengl.GLContext;

import static org.lwjgl.glfw.GLFW.glfwPollEvents;
import static org.lwjgl.glfw.GLFW.glfwSwapBuffers;
import static org.lwjgl.opengl.GL11.*;

/**
 * Created by TekMaTek on 26/02/2015.
 */
public class SimpleRenderer {

    private static Mesh cube = MeshUtil.BuildSimpleCube();
    static BasicShader shader = new BasicShader();

    public static void loop() {
        while(!FRPDisplay.shouldWindowClose()) {
            GLContext.createFromCurrent();
            glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT); // clear the framebuffer

            glfwSwapBuffers(FRPDisplay.GetWindow()); // swap the color buffers
            glfwPollEvents();
        }
    }

    public static void loop2() {
        while(!FRPDisplay.shouldWindowClose()) {
            glfwPollEvents();
            glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT); // clear the framebuffer

            glfwSwapBuffers(FRPDisplay.GetWindow()); // swap the color buffers
            RenderSimpleSquare();
        }
    }

    public static void init() {
        System.out.println(RenderingUtil.GetOpenGLVersion());
        glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
        glEnable(GL_DEPTH_TEST);
    }

    public static void RenderSimpleSquare() {
        shader.Bind();
//        shader.updateUniforms(Material.WhiteNoTexture());
        cube.draw();
    }
}