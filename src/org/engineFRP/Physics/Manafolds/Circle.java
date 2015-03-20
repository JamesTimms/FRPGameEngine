package org.engineFRP.Physics.Manafolds;

import org.engineFRP.maths.Vector2f;

/**
 * Created by james on 3/10/15.
 */
public class Circle {
    float radius;
    Vector2f pos;

    public boolean isCircleColliding(Circle otherCircle) {
        float r = this.radius + otherCircle.radius;
        r *= r;
        return r < Math.pow(this.pos.x + otherCircle.pos.x, 2.0d)
                + Math.pow(this.pos.y + otherCircle.pos.y, 2.0d);
    }
}
