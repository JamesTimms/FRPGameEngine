package org.engineFRP.Physics.collision;

import org.engineFRP.maths.Vector2f;
import org.engineFRP.rendering.Vertex;

/**
 * Created by TekMaTek on 10/03/2015.
 */
public class Click {

    public static boolean isInPolygon(Vertex[] vertices, Vector2f point) {
        //vertices must be in clockwise order.
//        Vertex[] vertices = shape.getVertices();
        int vertCount = vertices.length;
        int low = 0, high = vertCount;
        do {
            int mid = (low + high) / 2;
            if(isPointLeftOfLine(vertices[0].getPos().getXY(), vertices[mid].getPos().getXY(), point)) {
                low = mid;
            } else {
                high = mid;
            }
        } while(low + 1 < high);

        if(low == 0 || high == vertCount) {
            return false;
        }
        return isPointLeftOfLine(vertices[low].getPos().getXY(), vertices[high].getPos().getXY(), point);
    }

    private static boolean isPointLeftOfLine(Vector2f a, Vector2f b, Vector2f point) {
        float result = ((b.getX() - a.getX()) * (point.getY() - a.getY())
                - (b.getY() - a.getY()) * (point.getX() - a.getX()));
        return result > 0;
    }
}
