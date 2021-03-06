package org.engineFRP.rendering.Manafolds;

import org.engineFRP.Util.MergeSort;
import org.engineFRP.rendering.Vertex;

/**
 * Created by james on 3/10/15.
 */
public class Shape {

    protected Vertex[] vertices;
    protected int[] drawOrder;

    protected static Vertex[] orderClockwise(Vertex[] vertices) {
        return new MergeSort<Vertex>().mergeSort(vertices);
    }

    public Vertex[] getVertices() {
        return vertices;
    }

    public Vertex[] setVertices(Vertex[] vertices) {
        this.vertices = vertices;
        return this.vertices;
    }

    public Vertex[] resize(float resizeFactor) {
        for(int i = 0; i < this.vertices.length; i++) {
            this.vertices[i].setPosition(this.vertices[i].getPos().mul(resizeFactor));
        }
        return this.vertices;
    }

    public int[] getIndices() {
        return drawOrder;
    }
}