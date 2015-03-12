package org.engineFRP.Physics.Manafolds;

import org.engineFRP.Util.MergeSort;
import org.engineFRP.maths.Vector3f;
import org.engineFRP.rendering.Vertex;

/**
 * Created by TekMaTek on 11/03/2015.
 */
public class Triangle extends Shape {

    public Triangle() {
        this(0.5f);
    }

    public Triangle(float size) {
        vertices = new Vertex[] {
                new Vertex(new Vector3f(-size, -size, 0.0f)),
                new Vertex(new Vector3f(size, -size, 0.0f)),
                new Vertex(new Vector3f(0, size, 0.0f))
        };
        drawOrder = new int[] {0, 1, 2};
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
