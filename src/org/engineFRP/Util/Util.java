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

    public static final float JBOX_SCALE = 10.0f;

    public static int[] toIntArray(ArrayList<Integer> array) {
        Integer[] temp = new Integer[array.size()];
        int[] out = new int[array.size()];
        temp = array.toArray(temp);
        for(int i = 0; i < array.size(); i++) {
            out[i] = temp[i];
        }
        return out;
    }

    public static int[] genIndices(int length) {
        int[] out = new int[length];
        for(int i = 0; i < length; i++) {
            out[i] = i;
        }
        return out;
    }

    public static Vertex[] polyToVertexArray(PolygonShape poly) {
        Vertex[] verts = new Vertex[poly.getVertexCount()];
        for(int i = 0; i < poly.getVertexCount(); i++) {
            Vec2 p = poly.getVertices()[i];
            verts[i] = new Vertex(vec2ToVector3f(p), vec2ToVector2f(p));
        }
        return verts;
    }

    public static PolygonShape vertexArrayToPoly(Vertex[] verts) {
        PolygonShape poly = new PolygonShape();
        Vec2[] v = new Vec2[verts.length];
        for(int i = 0; i < verts.length; i++) {
            v[i] = Vector3fToVec2(verts[i].getPos());
        }
        poly.set(v, verts.length);
        return poly;
    }

    public static Vector3f vec2ToVector3f(Vec2 vec2) {
        return vec2ToVector3f(vec2, JBOX_SCALE);
    }

    public static Vector3f vec2ToVector3f(Vec2 vec2, float scale) {
        return new Vector3f(vec2.x / scale, vec2.y / scale, 0.0f);
    }

    public static Vector2f vec2ToVector2f(Vec2 vec2) {
        return vec2ToVector2f(vec2, JBOX_SCALE);
    }

    public static Vector2f vec2ToVector2f(Vec2 vec2, float scale) {
        return new Vector2f(vec2.x / scale, vec2.y / scale);
    }

    public static Vec2 Vector3fToVec2(Vector3f pos) {
        return Vector3fToVec2(pos, JBOX_SCALE);
    }

    public static Vec2 Vector3fToVec2(Vector3f pos, float scale) {
        return new Vec2(pos.x * scale, pos.y * scale);
    }
}
