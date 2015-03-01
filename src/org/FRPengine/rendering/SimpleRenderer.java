package org.FRPengine.rendering;

import org.FRPengine.core.FRPDisplay;
import org.FRPengine.core.Time;
import org.FRPengine.maths.Vector3f;
import org.FRPengine.rendering.shaders.BasicShader;
import org.FRPengine.rendering.shaders.Material;
import sodium.Listener;
import sodium.StreamSink;

import static org.lwjgl.glfw.GLFW.glfwPollEvents;
import static org.lwjgl.glfw.GLFW.glfwSwapBuffers;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL20.*;

/**
 * Created by TekMaTek on 26/02/2015.
 */
public class SimpleRenderer {

    private static Mesh cube = MeshUtil.BuildSimpleCube();
    static BasicShader shader = new BasicShader();
    private static Listener renderLoopListener;

    private static int vbo;
    private static int size;
    
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

//        RenderSimpleSquare();
        draw();

        glfwSwapBuffers(FRPDisplay.GetWindow());
        glfwPollEvents();
    }

    public static void init() {
        System.out.println(RenderingUtil.GetOpenGLVersion());
        glClearColor(0.0f, 1.0f, 1.0f, 1.0f);
        vbo = glGenBuffers();
        size = 0;

        Vertex[] data = new Vertex[] {
                new Vertex(new Vector3f(-1.0f, -1.0f, 0.0f)),
                new Vertex(new Vector3f(1.0f, -1.0f, 0.0f)),
                new Vertex(new Vector3f(0.0f, 1.0f, 0.0f))
        };
        addVertices(data);
    }

    public static void draw() {
        shader.Bind();
        shader.updateUniforms(Material.WhiteNoTexture());
        glEnableVertexAttribArray(0);

        glBindBuffer(GL_ARRAY_BUFFER, vbo);
        glVertexAttribPointer(0, 3, GL_FLOAT, false, Vertex.SIZE * 4, 0);
        glDrawArrays(GL_TRIANGLES, 0, size);

        glDisableVertexAttribArray(0);
    }

    public static void addVertices(Vertex[] vertices) {
        size = vertices.length * Vertex.SIZE;
        glBindBuffer(GL_ARRAY_BUFFER, vbo);
        glBufferData(GL_ARRAY_BUFFER, RenderingUtil.createFlippedBuffer(vertices), GL_STATIC_DRAW);
    }
}