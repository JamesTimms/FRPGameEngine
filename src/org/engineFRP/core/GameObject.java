package org.engineFRP.core;

import org.engineFRP.FRP.FRPKeyboard;
import org.engineFRP.FRP.FRPUtil;
import org.engineFRP.Physics.JBoxWrapper;
import org.engineFRP.Util.Util;
import org.engineFRP.rendering.Mesh;
import org.engineFRP.rendering.shaders.Material;
import org.engineFRP.maths.Vector3f;
import org.jbox2d.common.Vec2;
import sodium.Cell;
import sodium.Lambda2;
import sodium.Stream;

import java.util.function.Function;
import java.util.function.IntBinaryOperator;
import java.util.function.IntFunction;
import java.util.function.IntUnaryOperator;

/**
 * Created by TekMaTek on 26/01/2015.
 */
public class GameObject {

    public String name;
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
        this(translation, Vector3f.ZERO, Vector3f.ONE, mesh, Material.white);
    }

    public GameObject(final Vector3f translation, final Mesh mesh, final Material mat) {
        this(translation, Vector3f.ZERO, Vector3f.ONE, mesh, mat);
    }

    public GameObject addStaticPhysics() {
        physics = JBoxWrapper.BuildStaticBody(this, transform.translation.sample(), mesh);
        return this.updateFromJbox();
    }

    public GameObject addDynamicPhysics() {
        physics = JBoxWrapper.BuildDynamicBody(this, transform.translation.sample(), mesh);
        return this.updateFromJbox();
    }

    private GameObject updateFromJbox() {
        this.physics.body.setTransform(Util.Vector3fToVec2(this.transform.translation.sample())
                , this.transform.rotation.sample().z);
        return changeTranslationType(FRPUtil.setVector)
                .mergeTranslationWith(physics.updatePos())
                .mergeRotation(physics.updateRot());
    }

    public GameObject applyForce(Vec2 v) {
        this.physics.applyForceToCenter(v);
        return this;
    }

    public GameObject updateToJbox(Stream<Vector3f> up) {
        physics.updateToJbox(up
                .accum(this.transform.translation.sample(), (curV, newV) -> curV.add(newV))
                .updates()
                .map(Util::Vector3fToVec2));
        return this;
    }

    public GameObject resetJboxPosWith(int glfwKey) {
        physics.updateToJboxZeroForce(FRPKeyboard.keyEvent
                .filter(key -> key.code == glfwKey)
                .map(key -> Vector3f.ZERO)
                .accum(this.transform.translation.sample(), (curV, newV) -> curV.add(newV))
                .updates()
                .map(Util::Vector3fToVec2));
        return this;
    }

    public GameObject name(String name) {
        this.name = name;
        return this;
    }

    public GameObject mergeTranslationWith(final Stream<Vector3f> stream) {
        this.transform.mergeTranslation(stream);
        return this;
    }

    public GameObject mergeRotation(final Stream<Vector3f> stream) {
        this.transform.mergeRotation(stream);
        return this;
    }

    public GameObject mergeScale(final Stream<Vector3f> stream) {
        this.transform.mergeScale(stream);
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

    public GameObject scale(Vector3f vec) {
        this.transform.scale.updateValue(vec);
        return this;
    }

    public <V, T extends GameObject> GameObject apply(Lambda2<V, T, T> thing, V applicative) {
        thing.apply(applicative, (T) this);
        return this;
    }
}