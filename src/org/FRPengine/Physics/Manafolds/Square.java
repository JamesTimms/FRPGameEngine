package org.FRPengine.Physics.Manafolds;

import org.FRPengine.Util.MergeSort;
import org.FRPengine.maths.Vector3f;
import org.FRPengine.rendering.Vertex;

/**
 * Created by TekMaTek on 11/03/2015.
 */
public class Square implements Shape {

    private Vertex[] vertices;

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
        vertices = orderClockwise(vertices);//This is here for point in shape collision stuff.
    }

    public Vertex[] getVertices() {
        return vertices;
    }

    private static Vertex[] orderClockwise(Vertex[] vertices) {
        MergeSort<Vertex> merge = new MergeSort<>();
        return merge.mergeSort(vertices);
    }

}