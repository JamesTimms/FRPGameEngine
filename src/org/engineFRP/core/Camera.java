package org.engineFRP.core;

import org.engineFRP.FRP.FRPWinSize;
import org.engineFRP.maths.Matrix4f;
import org.engineFRP.maths.Vector3f;
import sodium.Cell;

/**
 * Created by TekMaTek on 26/01/2015.
 */
public class Camera {

    public static Camera mainCamera = new Camera();

    public float zNear;
    public float zFar;
    public Cell<Integer> width;
    public Cell<Integer> height;
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
        this.setProjection(70.0f, FRPDisplay.winResizeStream, 0.1f, 1000.0f);
        translation = new Cell<>(Vector3f.ZERO);
    }

    public void setProjection(float fieldOfView, FRPWinSize winSize, float zNear, float zFar) {
        this.fieldOfView = (fieldOfView > 1) ? fieldOfView : 1;
        this.width = winSize.width();
        this.height = winSize.height();
        this.zNear = (zNear > 0) ? zNear : 0;
        this.zFar = (zFar > zNear) ? zFar : zFar + 1.0f;
    }

    public Matrix4f GetViewProjection() {
        Matrix4f cameraRotation = new Matrix4f().initCamera(forward, up);
        Matrix4f cameraTranslation = new Matrix4f().initTranslation(
                -translation.sample().x, -translation.sample().y, -translation.sample().z);
        return cameraProjection().mul(cameraRotation.mul(cameraTranslation));
    }

    private Matrix4f cameraProjection() {
        return new Matrix4f().initPerspective(
                this.fieldOfView, height.sample() / width.sample(), this.zNear, this.zFar);
    }
}