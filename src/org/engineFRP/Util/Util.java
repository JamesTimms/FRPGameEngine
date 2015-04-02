package org.engineFRP.Util;

import org.engineFRP.maths.Vector2f;
import org.engineFRP.maths.Vector3f;
import org.engineFRP.rendering.Vertex;
import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.common.Vec2;

import java.util.ArrayList;

/**
 * Created by TekMaTek on 30/03/2015.
 */
public class Util {

    public static int[] toIntArray(ArrayList<Integer> array) {
        Integer[] temp = new Integer[array.size()];
        int[] out = new int[array.size()];
        temp = array.toArray(temp);
        for(int i = 0; i < array.size(); i++) {
            out[i] = temp[i];
        }
        return out;
    }

    public static int[] genIndicies(int length) {
        int[] out = new int[length];
        for(int i = 0; i < length; i++) {
            out[i] = i;
        }
        return out;
    }

    public static Vertex[] polyToVertexArray(PolygonShape poly) {
        Vertex[] verts = new Vertex[poly.getVertices().length];
        for(int i = 0; i < poly.getVertices().length; i++) {
            Vec2 p = poly.getVertices()[i];
            Vec2 n = poly.getVertices()[i];
            verts[i] = new Vertex(new Vector3f(p.x, p.y, 0.0f), new Vector2f(n.x, n.y));
        }
        return verts;
    }

    public static Vector3f vec2ToVector3f(Vec2 vec2){
        return new Vector3f(vec2.x, vec2.y, 0.0f);
    }

    public static Vector3f vec2ToScaledVector3f(Vec2 vec2){
        return new Vector3f(vec2.x / 10.0f, vec2.y / 10.0f, 0.0f);
    }
}
