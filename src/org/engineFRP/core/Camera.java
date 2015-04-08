package org.engineFRP.core;

import org.engineFRP.FRP.CellUpdater;
import org.engineFRP.FRP.FRPDisplay;
import org.engineFRP.FRP.FRPUtil;
import org.engineFRP.maths.Matrix4f;
import org.engineFRP.maths.Vector3f;

/**
 * Created by TekMaTek on 26/01/2015.
 */
public class Camera {

    public static Camera mainCamera = new Camera();
    public static final int PERSPECTIVE = 0;
    public static final int ORTHOGRAPHIC = 1;

    public float zNear;
    public float zFar;
    public int width;
    public int height;
    public float fieldOfView;

    public Vector3f forward = new Vector3f(0.0f, 0.0f, 1.0f);
    public Vector3f up = new Vector3f(0.0f, 1.0f, 0.0f);
    public Vector3f yAxis = new Vector3f(0.0f, 1.0f, 0.0f);
    public CellUpdater<Vector3f> translation;
    private Vector3f rotation;
    private Vector3f scale;
    public int cameraMode = ORTHOGRAPHIC;

    public Camera() {
        if(mainCamera == null) {
            mainCamera = this;
        }
        this.setProjection(60.0f, FRPDisplay.DEFAULT_WIDTH, FRPDisplay.DEFAULT_HEIGHT, 0.1f, 1000.0f);
        translation = new CellUpdater<>(FRPUtil.addVectors, new Vector3f(0.0f, 0.0f, -1.0f));
    }

    public void setProjection(float fieldOfView, int width, int height, float zNear, float zFar) {
        this.fieldOfView = (fieldOfView > 1) ? fieldOfView : 1;
        this.width = width;
        this.height = height;
        this.zNear = (zNear > 0) ? zNear : 0;
        this.zFar = (zFar > zNear) ? zFar : zFar + 1.0f;
    }

    public Matrix4f GetProjection() {
        Matrix4f cameraRotation = new Matrix4f().initCamera(forward, up);
        Matrix4f cameraTranslation = new Matrix4f().initTranslation(
                -translation.sample().x, -translation.sample().y, -translation.sample().z);
        Matrix4f projection = ((cameraMode == PERSPECTIVE) ?
                cameraPerspectiveProjection() :
                cameraOrthographicProjection())
                .mul(cameraRotation.mul(cameraTranslation));
        return projection;
    }

    private Matrix4f cameraPerspectiveProjection() {
        return new Matrix4f().initPerspective(
                this.fieldOfView, (float) height / (float) width, this.zNear, this.zFar);
    }

    private Matrix4f cameraOrthographicProjection() {//TODO: Need to figure out how to implement this correctly.
        return new Matrix4f().initOrthographic(-1.0f, 1.0f,
                -1.0f, 1.0f, -1.0f, 1.0f);
    }
}