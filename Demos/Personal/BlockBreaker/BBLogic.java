package Personal.BlockBreaker;

import org.engineFRP.FRP.FRPKeyboard;
import org.engineFRP.Physics.JBoxWrapper;
import org.engineFRP.FRP.ListenerArrayList;
import org.engineFRP.Physics.JBoxCollisionListener;
import org.engineFRP.core.GameObject;
import org.engineFRP.core.Scene;
import org.engineFRP.maths.Vector3f;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.contacts.Contact;
import sodium.Stream;

import static org.lwjgl.glfw.GLFW.GLFW_KEY_LEFT;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_RIGHT;
import static org.lwjgl.glfw.GLFW.GLFW_RELEASE;

/**
 * Created by TekMaTek on 22/04/2015.
 */
public class BBLogic {

    public static ListenerArrayList l = new ListenerArrayList();

    public static GameObject bouncyCollisionsWith(String otherGO, GameObject go) {
        BBLogic.l.add(JBoxCollisionListener.end
                .filter(contact -> areObjectsPresent(go, Scene.graph.find(otherGO).sample(), contact))
                .listen(contact -> {
                    GameObject go1 = JBoxWrapper.getGOFromBody(contact.getFixtureA().getBody());
                    GameObject go2 = JBoxWrapper.getGOFromBody(contact.getFixtureB().getBody());
                    GameObject thisGO = go.equals(go1) ? go1 : go2;
                    Vector3f v = go1.transform.translation.sample();
                    Vector3f v2 = go2.transform.translation.sample();
                    float xForce = v2.x - v.x;
                    thisGO.applyForce(new Vec2(xForce / 5.0f, 0.05f));
                }));
        return go;
    }

    protected static boolean areObjectsPresent(GameObject g, GameObject otherGO, Contact contact) {
        GameObject go = JBoxWrapper.getGOFromBody(contact.getFixtureA().getBody());
        GameObject go2 = JBoxWrapper.getGOFromBody(contact.getFixtureB().getBody());
        return (g.equals(go) || g.equals(go2)) &&
                (otherGO.equals(go) || otherGO.equals(go2));
    }

    public static GameObject canBeDestroyedBy(String otherGO, GameObject go) {
        BBLogic.l.add(JBoxCollisionListener.end
                .filter(contact -> areObjectsPresent(Scene.graph.find(otherGO).sample(), go, contact))
                .listen(contact -> {
                    JBoxWrapper.markForDeletion(go.physics.body);
                    Scene.graph.destroy(go);
                }));
        return go;
    }

    public static Stream<Vector3f> paddleMovement(float moveAmount) {
        return FRPKeyboard.keyEvent
                .filter(key -> key.action != GLFW_RELEASE
                        && FRPKeyboard.isArrowKeyPressed(key.key))
                .map(key -> {
                    switch(key.key) {
                        case (GLFW_KEY_RIGHT):
                            return new Vector3f(-moveAmount, 0.0f, 0.0f);
                        case (GLFW_KEY_LEFT):
                            return new Vector3f(moveAmount, 0.0f, 0.0f);
                        default:
                            return Vector3f.ZERO;
                    }
                });
    }
}
