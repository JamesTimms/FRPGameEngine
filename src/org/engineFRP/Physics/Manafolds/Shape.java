package org.engineFRP.Physics.Manafolds;

import org.engineFRP.core.Transform;
import org.engineFRP.maths.Vector3f;
import org.engineFRP.rendering.Vertex;

/**
 * Created by james on 3/10/15.
 */
public abstract class Shape {

    protected Vertex[] vertices;
    protected int[] drawOrder;

    public abstract Vertex[] getVertices();

    public abstract int[] getIndices();

    public Vertex[] adjustToTransform(Transform transform) {
        Vertex[] existingVerts = this.getVertices();
        Vertex[] newVerts = new Vertex[existingVerts.length];
        for(int i = 0; i < existingVerts.length; i++) {
            Vector3f copyOfTrans = transform.getTranslation().clone();
            copyOfTrans.setY(-copyOfTrans.getY());
            newVerts[i] = new Vertex(existingVerts[i].getPos().add(copyOfTrans));
        }
        return newVerts;
    }

}
