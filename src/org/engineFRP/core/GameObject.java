package org.engineFRP.core;

import org.engineFRP.FRP.FRPUtil;
import org.engineFRP.FRP.JBoxWrapper;
import org.engineFRP.Util.Util;
import org.engineFRP.maths.Matrix4f;
import org.engineFRP.maths.Vector3f;
import org.engineFRP.rendering.Mesh;
import org.engineFRP.rendering.shaders.Material;
import sodium.Cell;
import sodium.Lambda2;
import sodium.Stream;

/**
 * Created by TekMaTek on 26/01/2015.
 */
public final class GameObject {

    public final Transform transform;
    public Mesh mesh;
    public Material material;
    public JBoxWrapper physics;

    public GameObject() {
        this(Vector3f.ZERO, Vector3f.ZERO, Vector3f.ZERO, null, Material.BuildMaterial(Vector3f.ZERO, 0.5f, 0.2f, 1.0f));
    }

    private GameObject(final Vector3f position, final Vector3f rotation, final Vector3f scale, final Mesh mesh, final Material mat) {
        this.material = mat;
        this.transform = new Transform(position, rotation, scale);
        this.mesh = mesh;
    }

    public GameObject(final Vector3f translation, final Mesh mesh) {
        this(translation, Vector3f.ZERO, Vector3f.ONE, mesh, Material.BuildMaterial(Vector3f.ZERO, 0.5f, 0.2f, 1.0f));
    }

    public GameObject(final Vector3f translation, final Mesh mesh, final Material mat) {
        this(translation, Vector3f.ZERO, Vector3f.ONE, mesh, mat);
    }

    public GameObject addStaticPhysics() {
        physics = JBoxWrapper.BuildStaticBody(transform.translation.sample(), mesh);
        return this;
    }

    public GameObject addDynamicPhysics() {
        physics = JBoxWrapper.BuildDynamicBody(transform.translation.sample(), mesh);
        return this;
    }

    public GameObject updateFromJbox() {
        return changeTranslationType(FRPUtil.setVector)
                .mergeTranslation(physics.updatePos())
                .mergeRotation(physics.updateRot());
    }

    public GameObject updateToJbox() {
        physics.updateToJbox(transform.translation
                .updateFrom()
                .map(Util::Vector3fToVec2));
        return this;
    }

    public Matrix4f getProjectedTransformation(final Camera camera) {
        return camera.GetViewProjection().mul(transform.getTransformMatrix());
    }

    public GameObject mergeTranslation(final Stream<Vector3f> stream) {
        this.transform.mergeTranslation(stream);
        return this;
    }

    public GameObject mergeRotation(final Stream<Vector3f> stream) {
        this.transform.mergeRotation(stream);
        return this;
    }

    public GameObject changeTranslationType(Lambda2<Cell<Vector3f>, Stream<Vector3f>, Cell<Vector3f>> newType) {
        this.transform.translation.changeResolver(newType);
        return this;
    }

    public GameObject translation(Vector3f vec) {
        this.transform.translation.updateValue(vec);
        return this;
    }

}