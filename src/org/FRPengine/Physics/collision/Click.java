package org.FRPengine.Physics.collision;

import org.FRPengine.maths.Vector2f;
import org.FRPengine.rendering.Vertex;

/**
 * Created by TekMaTek on 10/03/2015.
 */
public class Click {

    Vector2f point;

    public Click(Vector2f point){
        this.point = point;
    }

    public boolean isInPolygon(Vertex[] vertices) {
        //vertices must be in clockwise order.
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

    private boolean isPointLeftOfLine(Vector2f a, Vector2f b, Vector2f point) {
        float result = ((b.getX() - a.getX()) * (point.getY() - a.getY())
                - (b.getY() - a.getY()) * (point.getX() - a.getX()));
        return result > 0;
    }
}
