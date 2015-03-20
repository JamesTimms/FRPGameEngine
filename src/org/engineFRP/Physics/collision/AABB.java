package org.engineFRP.Physics.collision;

import org.engineFRP.maths.Vector3f;
import sodium.Cell;

/**
 * Created by TekMaTek on 08/03/2015.
 */
public class AABB {//TODO: Finish implementing the solution for Z in 3D collisions.

    public Cell<Vector3f> min;
    public Cell<Vector3f> max;
//    static int counter = 0;//DEBUG

    public AABB(Cell<Vector3f> min, Cell<Vector3f> max) {
        this.min = min;
        this.max = max;
    }

    public boolean isCollidingWith(AABB c) {
//        counter++;//DEBUG
        if(max.sample().x < c.min.sample().x || min.sample().x > c.max.sample().x) return false;
        if(max.sample().y < c.min.sample().y || min.sample().y > c.max.sample().y) return false;
//        if(max.sample().z < c.min.sample().z || min.sample().z > c.max.sample().z) return false;
        return true;
    }

    public boolean isOutsideOf(AABB c) {
        return !isCollidingWith(c);
    }

    public Vector3f resolveCollision(AABB otherC) {
        float minX = this.min.sample().x > otherC.min.sample().x
                ? this.min.sample().x : otherC.min.sample().x;
        float maxX = this.max.sample().x < otherC.max.sample().x
                ? this.max.sample().x : otherC.max.sample().x;
        float minY = this.min.sample().y > otherC.min.sample().y
                ? this.min.sample().y : otherC.min.sample().y;
        float maxY = this.max.sample().y < otherC.max.sample().y
                ? this.max.sample().y : otherC.max.sample().y;


        float cminX = this.min.sample().x < otherC.min.sample().x
                ? this.min.sample().x : otherC.min.sample().x;
        float cmaxX = this.max.sample().x > otherC.max.sample().x
                ? this.max.sample().x : otherC.max.sample().x;
        float cminY = this.min.sample().y < otherC.min.sample().y
                ? this.min.sample().y : otherC.min.sample().y;
        float cmaxY = this.max.sample().y > otherC.max.sample().y
                ? this.max.sample().y : otherC.max.sample().y;

        float innerXCentre = (Math.abs(maxX) + Math.abs(minX)) / 2.0f;
        float innerYCentre = (Math.abs(maxY) + Math.abs(minY)) / 2.0f;
        float outerXCentre = (Math.abs(cmaxX) + Math.abs(cminX)) / 2.0f;
        float outerYCentre = (Math.abs(cmaxY) + Math.abs(cminY)) / 2.0f;

        float resolutionX = innerXCentre < outerXCentre ? -Math.abs(maxX - minX) : Math.abs(maxX - minX);
        float resolutionY = innerYCentre < outerYCentre ? -Math.abs(maxY - minY) : Math.abs(maxY - minY);
        float resolutionZ = 0.0f;

        return new Vector3f(resolutionX, resolutionY, resolutionZ);
    }
}