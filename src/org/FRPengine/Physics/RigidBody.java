package org.FRPengine.Physics;

import org.FRPengine.Physics.Manafolds.Shape;
import org.FRPengine.maths.Vector2f;

/**
 * Created by james on 3/10/15.
 */
public class RigidBody {
    Shape shape;

    // Linear components
    Vector2f position;
    Vector2f velocity;
    float acceleration;

    // Angular components
    float orientation;// radians
    float angularVelocity;
    float torque;
}
