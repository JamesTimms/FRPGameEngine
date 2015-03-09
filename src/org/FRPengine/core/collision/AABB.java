package org.FRPengine.core.collision;

import org.FRPengine.maths.Vector3f;
import sodium.Cell;

/**
 * Created by TekMaTek on 08/03/2015.
 */
public class AABB {

    public Cell<Vector3f> min;
    public Cell<Vector3f> max;
    static int counter = 0;//DEBUG

    public AABB(Cell<Vector3f> min, Cell<Vector3f> max) {
        this.min = min;
        this.max = max;
    }

    public boolean isCollidingWith(AABB c) {
        System.out.println(counter++);//DEBUG
        if(this.max.sample().getX() < c.min.sample().getX() || this.min.sample().getX() > c.max.sample().getX()) return false;
        if(this.max.sample().getY() < c.min.sample().getY() || this.min.sample().getY() > c.max.sample().getY()) return false;
//        if(this.max.sample().getZ() < c.min.sample().getZ() || this.min.sample().getZ() > c.max.sample().getZ()) return false;
        return true;
    }

    public boolean isOutsideOf(AABB c) {
        return !isCollidingWith(c);
    }
}
