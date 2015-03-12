package org.engineFRP.Physics.Manafolds;

import org.engineFRP.Util.MergeSort;
import org.engineFRP.maths.Vector3f;
import org.engineFRP.rendering.Vertex;

/**
 * Created by TekMaTek on 11/03/2015.
 */
public class Square extends Shape {

    public Square() {
        this(0.1f);
    }

    public Square(float size) {
        float adjSize = size / 2.0f;
        vertices = new Vertex[] {
                new Vertex(new Vector3f(-adjSize, -adjSize, 0.0f)),//bottom left
                new Vertex(new Vector3f(adjSize, -adjSize, 0.0f)),//bottom right
                new Vertex(new Vector3f(-adjSize, adjSize, 0.0f)),//top left
                new Vertex(new Vector3f(adjSize, adjSize, 0.0f))//top right
        };
        drawOrder = new int[] {0, 1, 3, 1, 2, 3};
        vertices = orderClockwise(vertices);//This is here for point in shape collision stuff.
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