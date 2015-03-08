package org.FRPengine.core.collision;

import org.FRPengine.maths.Vector3f;

/**
 * Created by TekMaTek on 08/03/2015.
 */
public class AABB {

    public Vector3f min;
    public Vector3f max;
    static int counter = 0;//DEBUG

    public AABB(Vector3f min, Vector3f max) {
        this.min = min;
        this.max = max;
    }

    public boolean isColliding(AABB otherCollider) {
        System.out.println(counter++);//DEBUG
        if(this.max.getX() < otherCollider.min.getX() || this.min.getX() > otherCollider.max.getX()) return false;
        if(this.max.getY() < otherCollider.min.getY() || this.min.getY() > otherCollider.max.getY()) return false;
        if(this.max.getZ() < otherCollider.min.getZ() || this.min.getZ() > otherCollider.max.getZ()) return false;
        return true;
    }
}
