package org.FRPengine.core;

import org.FRPengine.maths.Matrix4f;
import org.FRPengine.maths.Vector3f;
import sodium.Cell;

/**
 * Created by TekMaTek on 26/01/2015.
 */
public class Camera {

    public static Camera mainCamera = new Camera();

    public float zNear;
    public float zFar;
    public float width;
    public float height;
    public float fieldOfView;

    public Vector3f forward = new Vector3f(0.0f, 0.0f, 1.0f);
    public Vector3f up = new Vector3f(0.0f, 1.0f, 0.0f);
    public Vector3f yAxis = new Vector3f(0.0f, 1.0f, 0.0f);
    public Cell<Vector3f> translation;
    private Vector3f rotation;
    private Vector3f scale;

    public Camera() {
        if(mainCamera == null) {
            mainCamera = this;
        }
        this.setProjection(70.0f, FRPDisplay.DEFAULT_WIDTH, FRPDisplay.DEFAULT_HEIGHT, 0.1f, 1000.0f);
    }

    private Matrix4f cameraProjection() {
        return new Matrix4f().initPerspective(
                this.fieldOfView, this.height / this.width, this.zNear, this.zFar);
    }

    public Matrix4f GetViewProjection() {
        Matrix4f cameraRotation = new Matrix4f().initCamera(forward, up);
        Matrix4f cameraTranslation = new Matrix4f().initTranslation(
                -translation.sample().getX(), -translation.sample().getY(),
                -translation.sample().getZ());

        return cameraProjection().mul(cameraRotation.mul(cameraTranslation));
    }

    public void setProjection(float fieldOfView, float width, float height, float zNear, float zFar) {
        this.fieldOfView = (fieldOfView > 1) ? fieldOfView : 1;
        this.width = width;
        this.height = height;
        this.zNear = (zNear > 0) ? zNear : 0;
        this.zFar = (zFar > zNear) ? zFar : zFar + 1.0f;
    }
}