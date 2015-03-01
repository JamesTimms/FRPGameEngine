package org.FRPengine.rendering;

import org.FRPengine.core.FRPDisplay;
import org.FRPengine.core.Time;
import org.FRPengine.rendering.shaders.BasicShader;
import sodium.Listener;
import sodium.StreamSink;

import static org.lwjgl.glfw.GLFW.glfwPollEvents;
import static org.lwjgl.glfw.GLFW.glfwSwapBuffers;
import static org.lwjgl.opengl.GL11.*;

/**
 * Created by TekMaTek on 26/02/2015.
 */
public class SimpleRenderer {

    //    private static Mesh cube = MeshUtil.BuildSimpleCube();
    private static Mesh triangle = MeshUtil.BuildTriangle();
    static BasicShader shader = new BasicShader();
    private static Listener renderLoopListener;


    public static void loop() {
        StreamSink<Long> renderLoop = new StreamSink<>();

        renderLoopListener = renderLoop
                .filter(Time::readyForFrameRate)
                .listen(cursor -> Render());
        while(!FRPDisplay.shouldWindowClose()) {
            renderLoop.send(Time.TEN_PER_SECOND);
        }
        renderLoopListener.unlisten();
    }

    public static void Render() {
        glfwPollEvents();
        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

        draw();

        glfwSwapBuffers(FRPDisplay.GetWindow());
    }

    public static void init() {
        System.out.println(RenderingUtil.GetOpenGLVersion());
        glClearColor(0.0f, 1.0f, 1.0f, 1.0f);
    }

    public static void draw() {
        shader.draw(triangle);
    }
}