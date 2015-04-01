package org.engineFRP.core;

import org.engineFRP.FRP.CellUpdater;
import org.engineFRP.FRP.FRPUtil;
import org.engineFRP.maths.Matrix4f;
import org.engineFRP.maths.Vector3f;
import org.engineFRP.rendering.Mesh;
import org.engineFRP.rendering.Vertex;
import org.engineFRP.rendering.shaders.Material;
import sodium.Cell;
import sodium.Lambda2;
import sodium.Stream;

/**
 * Created by TekMaTek on 26/01/2015.
 */
public final class Transform {

    //TODO: clean up the constructors in this class.
    public static final Vector3f yAxis = new Vector3f(0.0f, 1.0f, 0.0f);
    private final CellUpdater<Vector3f> translation;
    private final CellUpdater<Vector3f> rotation;
    private final CellUpdater<Vector3f> scale;
    public Mesh mesh;
    public Material material;

    private Vector3f forward = new Vector3f(0.0f, 0.0f, 1.0f);
    private Vector3f up = new Vector3f(0.0f, 1.0f, 0.0f);

    public Transform() {
        this(Vector3f.ZERO, Vector3f.ZERO, Vector3f.ZERO, null, Material.BuildMaterial(Vector3f.ZERO, 0.5f, 0.2f, 1.0f));
    }

    private Transform(final Vector3f position, final Vector3f rotation, final Vector3f scale, final Mesh mesh, final Material mat) {
        this.material = mat;
        this.translation = new CellUpdater<>(FRPUtil.addVectors, position);
        this.rotation = new CellUpdater<>(FRPUtil.addVectors, rotation);
        this.scale = new CellUpdater<>(FRPUtil.addVectors, scale);
        this.mesh = mesh;
    }

    public Transform(final Vector3f translation, final Mesh mesh) {
        this(translation, Vector3f.ZERO, Vector3f.ONE, mesh, Material.BuildMaterial(Vector3f.ZERO, 0.5f, 0.2f, 1.0f));
    }

    public Transform(final Vector3f translation, final Mesh mesh, final Material mat) {
        this(translation, Vector3f.ZERO, Vector3f.ONE, mesh, mat);
    }

    public Vertex[] addPosAndFlipY() {//FIXME: Make this method cleaner
        Vertex[] existingVerts = this.mesh.shape.getVertices();
        Vertex[] newVerts = new Vertex[existingVerts.length];
        for(int i = 0; i < existingVerts.length; i++) {
            Vector3f copyOfTrans = this.translation.sample().clone();
            copyOfTrans.y = -copyOfTrans.y;
            newVerts[i] = new Vertex(existingVerts[i].getPos().add(copyOfTrans));
        }
        return newVerts;
    }

    public Matrix4f getProjectedTransformation(final Camera camera) {
        return camera.GetViewProjection().mul(getTransformMatrix());
    }

    public Matrix4f getTransformMatrix() {
        final Vector3f _rotation = this.rotation.sample();
        final Vector3f _scale = this.scale.sample();
        Matrix4f translationMat =
                new Matrix4f().initTranslation(
                        translation.sample().x, translation.sample().y, translation.sample().z);
        Matrix4f rotationMat =
                new Matrix4f().initRotation(_rotation.x, _rotation.y, _rotation.z);
        Matrix4f scaleMat =
                new Matrix4f().initScale(_scale.x, _scale.y, _scale.z);

        return translationMat.mul(rotationMat.mul(scaleMat));
    }

    public Transform mergeTranslation(final Stream<Vector3f> stream) {
        this.translation.merge(stream);
        return this;
    }

    public Transform changeTranslationType(Lambda2<Cell<Vector3f>, Stream<Vector3f>, Cell<Vector3f>> newType) {
        translation.changeResolver(newType);
        return this;
    }

    public Vector3f translation() {
        return this.translation.sample();
    }

    public Vector3f getLeft() {
        Vector3f left = forward.cross(up);
        left.normalized();
        return left;
    }

    public Vector3f getRight() {
        Vector3f left = up.cross(forward);
        left.normalized();
        return left;
    }

    public void rotateX(final float angle) {
        Vector3f hAxis = yAxis.cross(forward).normalized();

        forward = forward.rotate(hAxis, angle).normalized();

        up = forward.cross(hAxis).normalized();
    }

    public void rotateY(final float angle) {
        Vector3f hAxis = yAxis.cross(forward).normalized();

        forward = forward.rotate(yAxis, angle).normalized();

        up = forward.cross(hAxis).normalized();
    }
}