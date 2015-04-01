package org.engineFRP.Physics.Manafolds;

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
        final int SEGMENTS = 40;
        ArrayList<Vertex> verts = new ArrayList<>();
        ArrayList<Integer> index = new ArrayList<>();
        for(int ii = 0; ii < SEGMENTS; ii++) {
            float theta = 2.0f * 3.1415926f * (float) ii / (float) SEGMENTS;//get the current angle

            float x = radius * (float) Math.cos(theta);//calculate the x component
            float y = radius * (float) Math.sin(theta);//calculate the y component

            verts.add(new Vertex(new Vector3f(x, y, 0.0f)));
            index.add(ii);
        }
        vertices = new Vertex[verts.size()];
        vertices = verts.toArray(vertices);
        drawOrder = Util.toIntArray(index);
    }
}