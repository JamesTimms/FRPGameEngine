package org.engineFRP.Physics.collision;

import org.engineFRP.maths.Vector3f;

/**
 * Created by TekMaTek on 08/03/2015.
 */
public class Collider {

    public Vector3f pos;
    public AABB boundBox;
    public float restitution;

    public Collider(Vector3f pos, AABB boundBox){
        this.pos = pos;
        this.boundBox = boundBox;
    }

}
