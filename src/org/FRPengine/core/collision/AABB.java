package org.FRPengine.core.collision;

import org.FRPengine.maths.Vector3f;
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
        if(max.sample().getX() < c.min.sample().getX() || min.sample().getX() > c.max.sample().getX()) return false;
        if(max.sample().getY() < c.min.sample().getY() || min.sample().getY() > c.max.sample().getY()) return false;
//        if(max.sample().getZ() < c.min.sample().getZ() || min.sample().getZ() > c.max.sample().getZ()) return false;
        return true;
    }

    public boolean isOutsideOf(AABB c) {
        return !isCollidingWith(c);
    }

    public Vector3f resolveCollision(AABB otherC) {
        float minX = this.min.sample().getX() > otherC.min.sample().getX()
                ? this.min.sample().getX() : otherC.min.sample().getX();
        float maxX = this.max.sample().getX() < otherC.max.sample().getX()
                ? this.max.sample().getX() : otherC.max.sample().getX();
        float minY = this.min.sample().getY() > otherC.min.sample().getY()
                ? this.min.sample().getY() : otherC.min.sample().getY();
        float maxY = this.max.sample().getY() < otherC.max.sample().getY()
                ? this.max.sample().getY() : otherC.max.sample().getY();


        float cminX = this.min.sample().getX() < otherC.min.sample().getX()
                ? this.min.sample().getX() : otherC.min.sample().getX();
        float cmaxX = this.max.sample().getX() > otherC.max.sample().getX()
                ? this.max.sample().getX() : otherC.max.sample().getX();
        float cminY = this.min.sample().getY() < otherC.min.sample().getY()
                ? this.min.sample().getY() : otherC.min.sample().getY();
        float cmaxY = this.max.sample().getY() > otherC.max.sample().getY()
                ? this.max.sample().getY() : otherC.max.sample().getY();

        float innerXCentre = (Math.abs(maxX) + Math.abs(minX)) / 2.0f;
        float innerYCentre = (Math.abs(maxY) + Math.abs(minY)) / 2.0f;
        float outerXCentre = (Math.abs(cmaxX) + Math.abs(cminX)) / 2.0f;
        float outerYCentre = (Math.abs(cmaxY) + Math.abs(cminY)) / 2.0f;

        float resolutionX = innerXCentre < outerXCentre ? -Math.abs(maxX - minX) : Math.abs(maxX - minX);
        float resolutionY = innerYCentre < outerYCentre ? -Math.abs(maxY - minY) : Math.abs(maxY - minY);
        float resolutionZ = 0.0f;

        return new Vector3f(resolutionX, resolutionY, resolutionZ);
    }

//    public Vector3f resolveCollision2(Vector3f vel, Vector3f angleOfIncident) {
//        return new Vector3f(resolutionX, resolutionY, resolutionZ);
//    }
}