package org.engineFRP.Util;

import org.engineFRP.FRP.FRPMouse;
import org.engineFRP.maths.Vector2f;
import org.engineFRP.rendering.Vertex;

/**
 * Created by TekMaTek on 10/03/2015.
 */
public class Click {

    public static boolean isInPolygon(Vertex[] vertices, Vector2f screenPoint) {
        //vertices must be in clockwise order.
//        Vertex[] vertices = shape.getVertices();
        int vertCount = vertices.length;
        Vector2f worldPoint = FRPMouse.screenToWorldSpace(screenPoint);
        int low = 0, high = vertCount;
        do {
            int mid = (low + high) / 2;
            if(isPointLeftOfLine(vertices[0].getPos().getXY(), vertices[mid].getPos().getXY(), worldPoint)) {
                low = mid;
            } else {
                high = mid;
            }
        } while(low + 1 < high);

        if(low == 0 || high == vertCount) {
            return false;
        }
        return isPointLeftOfLine(vertices[low].getPos().getXY(), vertices[high].getPos().getXY(), worldPoint);
    }

    private static boolean isPointLeftOfLine(Vector2f a, Vector2f b, Vector2f point) {
        float result = ((b.x - a.x) * (point.y - a.y)
                - (b.y - a.y) * (point.x - a.x));
        return result > 0;
    }
}
