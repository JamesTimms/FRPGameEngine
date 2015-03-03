package org.FRPengine.rendering;

import org.FRPengine.maths.Vector2f;
import org.FRPengine.maths.Vector3f;


/**
 * Created by TekMaTek on 01/02/2015.
 */
public class MeshUtil {

    public static Mesh BuildSimpleCube() {
        Vertex[] vertices = new Vertex[] {
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

        return new Mesh(vertices, indices, true);
    }

    public static Mesh BuildTriangle() {
        final float SIZE = 0.5f;
        return new Mesh(
                new Vertex[] {
                        new Vertex(new Vector3f(-SIZE, -SIZE, 0.0f)),
                        new Vertex(new Vector3f(SIZE, -SIZE, 0.0f)),
                        new Vertex(new Vector3f(0, SIZE, 0.0f))
                }, new int[] {0, 1, 2}
        );
    }

    public static Mesh BuildSquare() {
        final float SIZE = 0.05f;
        return new Mesh(
                new Vertex[] {
                        new Vertex(new Vector3f(-SIZE, -SIZE, 0.0f)),//bottom left
                        new Vertex(new Vector3f(SIZE, -SIZE, 0.0f)),//bottom right
                        new Vertex(new Vector3f(-SIZE, SIZE, 0.0f)),//top left
                        new Vertex(new Vector3f(SIZE, SIZE, 0.0f))//top right
                }, new int[] {0, 1, 2, 0, 2, 3,}
        );
    }
}
