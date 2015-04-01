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
        this(0.05f);
    }

    //improved Algorithm from http://slabode.exofire.net/circle_draw.shtml
    public Circle(float radius) {
        final float SEGMENTS = 100.0f;
        float theta = 2.0f * 3.1415926f / SEGMENTS;
        float tFactor = (float) Math.tan(theta);
        float rFactor = (float) Math.cos(theta);

        float x = radius;
        float y = 0.0f;

        ArrayList<Vertex> verts = new ArrayList<>();
        ArrayList<Integer> index = new ArrayList<>();
        for(int ii = 0; ii < SEGMENTS; ii++) {
            verts.add(new Vertex(new Vector3f(x, y, 0.0f)));
            index.add(ii);

            float tx = -y;
            float ty = x;

            x += tx * tFactor;
            y += ty * tFactor;

            x *= rFactor;
            y *= rFactor;
        }
        vertices = new Vertex[verts.size()];
        vertices = verts.toArray(vertices);
        drawOrder = Util.toIntArray(index);
    }
}