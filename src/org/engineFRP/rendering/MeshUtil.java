package org.engineFRP.rendering;

import org.engineFRP.Physics.Manafolds.Circle;
import org.engineFRP.Physics.Manafolds.Rect;
import org.engineFRP.Physics.Manafolds.Triangle;
import org.engineFRP.Util.Util;
import org.engineFRP.maths.Vector2f;
import org.engineFRP.maths.Vector3f;
import org.engineFRP.rendering.shaders.CircleShader;
import org.engineFRP.rendering.shaders.CubeShader;
import org.engineFRP.rendering.shaders.SquareShader;

import static org.lwjgl.opengl.GL11.GL_TRIANGLES;
import static org.lwjgl.opengl.GL11.GL_TRIANGLE_FAN;


/**
 * Created by TekMaTek on 01/02/2015.
 */
public class MeshUtil {

    public static Mesh BuildSimpleCube() {
        Vertex[] vertices = new Vertex[]{
                new Vertex(
                        new Vector3f(-1.0f, -1.0f, -1.0f),
                        new Vector2f(0.0f, 0.0f),
                        Vector3f.ZERO),
                new Vertex(
                        new Vector3f(1.0f, -1.0f, -1.0f),
                        new Vector2f(0.0f, 0.0f),
                        Vector3f.ZERO),
                new Vertex(
                        new Vector3f(1.0f, 1.0f, -1.0f),
                        new Vector2f(0.0f, 0.0f),
                        Vector3f.ZERO),
                new Vertex(
                        new Vector3f(-1.0f, 1.0f, -1.0f),
                        new Vector2f(0.0f, 0.0f),
                        Vector3f.ZERO),

                new Vertex(
                        new Vector3f(-1.0f, -1.0f, 1.0f),
                        new Vector2f(0.0f, 0.0f),
                        Vector3f.ZERO),
                new Vertex(
                        new Vector3f(1.0f, -1.0f, 1.0f),
                        new Vector2f(0.0f, 0.0f),
                        Vector3f.ZERO),
                new Vertex(
                        new Vector3f(1.0f, 1.0f, 1.0f),
                        new Vector2f(0.0f, 0.0f),
                        Vector3f.ZERO),
                new Vertex(
                        new Vector3f(-1.0f, 1.0f, 1.0f),
                        new Vector2f(0.0f, 0.0f),
                        Vector3f.ZERO),

                new Vertex(
                        new Vector3f(-1.0f, -1.0f, -1.0f),
                        new Vector2f(0.0f, 0.0f),
                        Vector3f.ZERO),
                new Vertex(
                        new Vector3f(-1.0f, 1.0f, -1.0f),
                        new Vector2f(0.0f, 0.0f),
                        Vector3f.ZERO),
                new Vertex(
                        new Vector3f(-1.0f, 1.0f, 1.0f),
                        new Vector2f(0.0f, 0.0f),
                        Vector3f.ZERO),
                new Vertex(
                        new Vector3f(-1.0f, -1.0f, 1.0f),
                        new Vector2f(0.0f, 0.0f),
                        Vector3f.ZERO),

                new Vertex(
                        new Vector3f(1.0f, -1.0f, -1.0f),
                        new Vector2f(0.0f, 0.0f),
                        Vector3f.ZERO),
                new Vertex(
                        new Vector3f(1.0f, 1.0f, -1.0f),
                        new Vector2f(0.0f, 0.0f),
                        Vector3f.ZERO),
                new Vertex(
                        new Vector3f(1.0f, 1.0f, 1.0f),
                        new Vector2f(0.0f, 0.0f),
                        Vector3f.ZERO),
                new Vertex(
                        new Vector3f(1.0f, -1.0f, 1.0f),
                        new Vector2f(0.0f, 0.0f),
                        Vector3f.ZERO),

                new Vertex(
                        new Vector3f(-1.0f, -1.0f, -1.0f),
                        new Vector2f(0.0f, 0.0f),
                        Vector3f.ZERO),
                new Vertex(
                        new Vector3f(-1.0f, -1.0f, 1.0f),
                        new Vector2f(0.0f, 0.0f),
                        Vector3f.ZERO),
                new Vertex(
                        new Vector3f(1.0f, -1.0f, 1.0f),
                        new Vector2f(0.0f, 0.0f),
                        Vector3f.ZERO),
                new Vertex(
                        new Vector3f(1.0f, -1.0f, -1.0f),
                        new Vector2f(0.0f, 0.0f),
                        Vector3f.ZERO),

                new Vertex(
                        new Vector3f(-1.0f, 1.0f, -1.0f),
                        new Vector2f(0.0f, 0.0f),
                        Vector3f.ZERO),
                new Vertex(
                        new Vector3f(-1.0f, 1.0f, 1.0f),
                        new Vector2f(0.0f, 0.0f),
                        Vector3f.ZERO),
                new Vertex(
                        new Vector3f(1.0f, 1.0f, 1.0f),
                        new Vector2f(0.0f, 0.0f),
                        Vector3f.ZERO),
                new Vertex(
                        new Vector3f(1.0f, 1.0f, -1.0f),
                        new Vector2f(0.0f, 0.0f),
                        Vector3f.ZERO)
        };

        int indices[] = {
                0, 1, 2,
                0, 2, 3,

                4, 5, 6,
                4, 6, 7,

                8, 9, 10,
                8, 10, 11,

                12, 13, 14,
                12, 14, 15,

                16, 17, 18,
                16, 18, 19,

                20, 21, 22,
                20, 22, 23
        };
        return new Mesh(vertices, indices, null, true, new CubeShader());
    }

    public static Mesh BuildTriangle() {
        return new Mesh(new Triangle());
    }

    public static Mesh BuildSquare() {
        return new Mesh(new Rect());
    }

    public static Mesh BuildSquareWithTexture(String filename) {
        return new Mesh(new Rect(), Texture.loadTexture(filename), new SquareShader());
    }

    public static Mesh BuildSquareWithTexture(String filename, float size) {
        return new Mesh(new Rect(size), Texture.loadTexture(filename), new SquareShader());
    }

    public static Mesh BuildRectWithTexture(String filename, float height, float width) {
        return new Mesh(new Rect(height, width), Texture.loadTexture(filename), new SquareShader());
    }

    public static Mesh BuildRect(float height, float width) {
        return new Mesh(new Rect(height, width), Texture.NoTexture(), new SquareShader());
    }

    public static Mesh BuildCircleWithTexture(String filename, float radius) {
        return new Mesh(new Circle(radius), Texture.loadTexture(filename), new CircleShader());
    }

    public static Mesh scaledSquare(String filename, float size) {
        return new Mesh(new Rect(size), Texture.loadTexture(filename), new SquareShader());
    }
}
