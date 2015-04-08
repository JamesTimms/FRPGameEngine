package org.engineFRP.Physics;

import org.engineFRP.FRP.JBoxWrapper;
import org.engineFRP.core.GameObject;
import org.engineFRP.maths.Vector3f;
import org.jbox2d.callbacks.ContactImpulse;
import org.jbox2d.callbacks.ContactListener;
import org.jbox2d.collision.Manifold;
import org.jbox2d.collision.WorldManifold;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.contacts.Contact;
import sodium.StreamSink;
import sodium.Tuple2;

import java.util.Objects;

/**
 * Created by TekMaTek on 07/04/2015.
 */
public class JBoxCollisionListener implements ContactListener {

    public static StreamSink<Contact> begin = new StreamSink<>();
    public static StreamSink<Contact> end = new StreamSink<>();
    public static StreamSink<Tuple2<Contact, Manifold>> preSolve = new StreamSink<>();
    public static StreamSink<Tuple2<Contact, ContactImpulse>> postSolve = new StreamSink<>();

    @Override
    public void beginContact(Contact contact) {
        begin.send(contact);
//        GameObject go = JBoxWrapper.getGOFromBody(contact.getFixtureA().getBody());
//        GameObject go2 = JBoxWrapper.getGOFromBody(contact.getFixtureB().getBody());
//        if(isBall(go, go2)) {
//            WorldManifold m = new WorldManifold();
//            contact.getWorldManifold(m);
//        } else if(isBall(go2, go)) {
//            WorldManifold m = new WorldManifold();
//            contact.getWorldManifold(m);
//            Vector3f v = go.transform.translation.sample();
//            Vector3f v2 = go2.transform.translation.sample();
//            float xForce = v2.x - v.x;
//            go2.physics.accumForce(new Vec2(xForce / 5.0f, 0.05f));
//        }
    }

    @Override
    public void endContact(Contact contact) {
        end.send(contact);
//        GameObject go = JBoxWrapper.getGOFromBody(contact.getFixtureA().getBody());
//        GameObject go2 = JBoxWrapper.getGOFromBody(contact.getFixtureB().getBody());
//        if(isBall(go, go2)) {
//            go.physics.applyForce();
//        } else if(isBall(go2, go)) {
//            go2.physics.applyForce();
//        }
    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {
        preSolve.send(new Tuple2<>(contact, oldManifold));
    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {
        postSolve.send(new Tuple2<>(contact, impulse));
    }

    private static boolean isBall(GameObject go, GameObject go2) {
        return go != null &&
                (Objects.equals(go.name, "Ball") && Objects.equals(go2.name, "Paddle"));
    }
}
