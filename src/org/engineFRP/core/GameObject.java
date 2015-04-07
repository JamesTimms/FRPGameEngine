package org.engineFRP.core;

import org.engineFRP.FRP.FRPKeyboard;
import org.engineFRP.FRP.FRPTime;
import org.engineFRP.FRP.FRPUtil;
import org.engineFRP.FRP.JBoxWrapper;
import org.engineFRP.Physics.JBoxCollisionListener;
import org.engineFRP.Util.Util;
import org.engineFRP.maths.Matrix4f;
import org.engineFRP.maths.Vector3f;
import org.engineFRP.rendering.Mesh;
import org.engineFRP.rendering.shaders.Material;
import org.jbox2d.collision.AABB;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.contacts.Contact;
import sodium.Cell;
import sodium.Lambda2;
import sodium.Listener;
import sodium.Stream;

import java.util.ArrayList;
import java.util.Objects;

/**
 * Created by TekMaTek on 26/01/2015.
 */
public final class GameObject {

    public String name;
    public final Transform transform;
    public Mesh mesh;
    public Material material;
    public JBoxWrapper physics;
    public ArrayList<Listener> l = new ArrayList<>();

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
                .mergeTranslation(physics.updatePos())
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
                .filter(key -> key.key == glfwKey)
                .map(key -> Vector3f.ZERO)
                .accum(this.transform.translation.sample(), (curV, newV) -> curV.add(newV))
                .updates()
                .map(Util::Vector3fToVec2));
        return this;
    }

    public Matrix4f getProjectedTransformation(final Camera camera) {
        return camera.GetViewProjection().mul(transform.getTransformMatrix());
    }

    public GameObject name(String name) {
        this.name = name;
        return this;
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

    public GameObject bouncyCollisionsWith(String otherGO) {
        l.add(JBoxCollisionListener.end
                .filter(contact -> isBall(contact, Scene.graph.find(otherGO).sample()))
                .listen(contact -> {
                    GameObject go = JBoxWrapper.getGOFromBody(contact.getFixtureA().getBody());
                    GameObject go2 = JBoxWrapper.getGOFromBody(contact.getFixtureB().getBody());
                    GameObject thisGO = this.equals(go) ? go : go2;
                    Vector3f v = go.transform.translation.sample();
                    Vector3f v2 = go2.transform.translation.sample();
                    float xForce = v2.x - v.x;
                    thisGO.applyForce(new Vec2(xForce / 5.0f, 0.05f));
                }));
        return this;
    }

    private boolean isBall(Contact contact, GameObject otherGO) {
        GameObject go = JBoxWrapper.getGOFromBody(contact.getFixtureA().getBody());
        GameObject go2 = JBoxWrapper.getGOFromBody(contact.getFixtureB().getBody());
        return (this.equals(go) || this.equals(go2)) &&
                (otherGO.equals(go) || otherGO.equals(go2));
    }

    public GameObject canBeDestroyedBy(String otherGO) {
        l.add(JBoxCollisionListener.end
                .filter(contact -> isBall(contact, this))
                .listen(contact -> {
                    JBoxWrapper.markForDeletion(this.physics.body);
                    Scene.graph.destroy(this);
                }));
        return this;
    }
}