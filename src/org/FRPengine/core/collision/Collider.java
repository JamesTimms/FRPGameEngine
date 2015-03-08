package org.FRPengine.core.collision;

import org.FRPengine.maths.Vector3f;

/**
 * Created by TekMaTek on 08/03/2015.
 */
public class Collider {

    public Vector3f pos;
    public AABB boundBox;

    public Collider(Vector3f pos, AABB boundBox){
        this.pos = pos;
        this.boundBox = boundBox;
    }

}
