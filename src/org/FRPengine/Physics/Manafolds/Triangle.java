package org.FRPengine.Physics.Manafolds;

import org.FRPengine.Util.MergeSort;
import org.FRPengine.maths.Vector3f;
import org.FRPengine.rendering.Vertex;

/**
 * Created by TekMaTek on 11/03/2015.
 */
public class Triangle implements Shape {

    private Vertex[] vertices;
    private int[] drawOrder = new int[] {0, 1, 2};

    public Triangle() {
        this(0.5f);
    }

    public Triangle(float size) {
        vertices = new Vertex[] {
                new Vertex(new Vector3f(-size, -size, 0.0f)),
                new Vertex(new Vector3f(size, -size, 0.0f)),
                new Vertex(new Vector3f(0, size, 0.0f))
        };
        orderClockwise(vertices);
    }

    public Vertex[] getVertices() {
        return vertices;
    }

    public int[] getIndices() {
        return drawOrder;
    }

    private static Vertex[] orderClockwise(Vertex[] vertices) {
        return new MergeSort<Vertex>().mergeSort(vertices);
    }
}
