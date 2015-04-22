package org.engineFRP.Physics;

import org.jbox2d.callbacks.ContactImpulse;
import org.jbox2d.callbacks.ContactListener;
import org.jbox2d.collision.Manifold;
import org.jbox2d.dynamics.contacts.Contact;
import sodium.StreamSink;
import sodium.Tuple2;

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
    }

    @Override
    public void endContact(Contact contact) {
        end.send(contact);
    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {
        preSolve.send(new Tuple2<>(contact, oldManifold));
    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {
        postSolve.send(new Tuple2<>(contact, impulse));
    }
}
