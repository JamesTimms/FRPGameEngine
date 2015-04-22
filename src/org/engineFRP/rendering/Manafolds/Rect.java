package org.engineFRP.rendering.Manafolds;

import org.engineFRP.rendering.Vertex;
import org.engineFRP.maths.Vector2f;
import org.engineFRP.maths.Vector3f;

/**
 * Created by TekMaTek on 11/03/2015.
 */
public class Rect extends Shape {

    public Rect() {
        this(0.4f);
    }

    public Rect(float size) {
        this(size, size);
    }

    public Rect(float width, float height) {
        float adjWidth = width / 2.0f;
        float adjHeight = height / 2.0f;
        vertices = new Vertex[] {//TODO: the texture coords here may be wrong.
                new Vertex(new Vector3f(-adjWidth, -adjHeight, 0.0f), new Vector2f(0.0f, 1.0f)),//bottom left
                new Vertex(new Vector3f(adjWidth, -adjHeight, 0.0f), new Vector2f(1.0f, 1.0f)),//bottom right
                new Vertex(new Vector3f(-adjWidth, adjHeight, 0.0f), new Vector2f(0.0f, 0.0f)),//top left
                new Vertex(new Vector3f(adjWidth, adjHeight, 0.0f), new Vector2f(1.0f, 0.0f))//top right
        };
        drawOrder = new int[] {0, 1, 3, 1, 2, 3};
        vertices = orderClockwise(vertices);//This is here for point in shape collision stuff.
    }
}