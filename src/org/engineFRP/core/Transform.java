package org.engineFRP.core;

import org.engineFRP.FRP.CellUpdater;
import org.engineFRP.maths.Vector3f;
import org.engineFRP.FRP.FRPUtil;
import org.engineFRP.maths.Matrix4f;
import sodium.Cell;
import sodium.Lambda2;
import sodium.Stream;

/**
 * Created by TekMaTek on 04/04/2015.
 */
public class Transform {

    public final CellUpdater<Vector3f> translation;
    public final CellUpdater<Vector3f> rotation;
    public final CellUpdater<Vector3f> scale;

    public static final Vector3f yAxis = new Vector3f(0.0f, 1.0f, 0.0f);
    private Vector3f forward = new Vector3f(0.0f, 0.0f, 1.0f);
    private Vector3f up = new Vector3f(0.0f, 1.0f, 0.0f);

    public Transform(final Vector3f position, final Vector3f rotation, final Vector3f scale) {
        this.translation = new CellUpdater<>(FRPUtil.addVectors, position);
        this.rotation = new CellUpdater<>(FRPUtil.addVectors, rotation);
        this.scale = new CellUpdater<>(FRPUtil.addVectors, scale);
    }

    public Matrix4f getTransformMatrix() {
        final Vector3f _rotation = rotation.sample();
        final Vector3f _scale = scale.sample();
        Matrix4f translationMat =
                new Matrix4f().initTranslation(
                        translation.sample().x, translation.sample().y, translation.sample().z);
        Matrix4f rotationMat =
                new Matrix4f().initRotation(_rotation.x, _rotation.y, _rotation.z);
        Matrix4f scaleMat =
                new Matrix4f().initScale(_scale.x, _scale.y, _scale.z);

        return Camera.mainCamera.GetProjection().mul(translationMat.mul(rotationMat.mul(scaleMat)));
    }

    public Transform mergeTranslation(final Stream<Vector3f> stream) {
        this.translation.merge(stream);
        return this;
    }

    public Transform mergeRotation(final Stream<Vector3f> stream) {
        this.rotation.merge(stream);
        return this;
    }

    public Transform mergeScale(Stream<Vector3f> stream) {
        this.scale.merge(stream);
        return this;
    }

    public Transform changeTranslationType(Lambda2<Cell<Vector3f>, Stream<Vector3f>, Cell<Vector3f>> newType) {
        this.translation.changeResolver(newType);
        return this;
    }

    public Transform translation(Vector3f vec) {
        this.translation.updateValue(vec);
        return this;
    }

    public Vector3f translation(GameObject gameObject) {
        return translation.sample();
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
