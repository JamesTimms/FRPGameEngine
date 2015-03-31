package org.engineFRP.Physics.Manafolds;

import org.engineFRP.Util.MergeSort;
import org.engineFRP.Util.Util;
import org.engineFRP.maths.Vector3f;
import org.engineFRP.rendering.Vertex;

import java.util.ArrayList;

/**
 * Created by james on 3/10/15.
 */
public class Circle extends Shape {

    public Circle() {
        this(0.5f);
    }

    public Circle(float radius) {
        final int segments = 20;
        float theta = (float) (2 * 3.1415926 / (float) segments);
        float tangential_factor = (float) Math.cos(theta);//calculate the tangential factor

        float radial_factor = (float) Math.cos(theta);//calculate the radial factor
        float x = radius;
        float y = 0;
        ArrayList<Vertex> verts = new ArrayList<>();
        ArrayList<Integer> index = new ArrayList<>();
        for(int i = 0; i < segments; i++) {
            verts.add(new Vertex(new Vector3f(x, y, -1.0f)));
            index.add(i);
            float tx = -y;
            float ty = x;
            x += tx * tangential_factor;
            y += ty * tangential_factor;
            y *= radial_factor;
            x *= radial_factor;
        }
        vertices = new Vertex[verts.size()];
        vertices = verts.toArray(vertices);
        drawOrder = Util.toIntArray(index);
        orderClockwise(vertices);
    }

    private static Vertex[] orderClockwise(Vertex[] vertices) {
        return new MergeSort<Vertex>().mergeSort(vertices);
    }
}