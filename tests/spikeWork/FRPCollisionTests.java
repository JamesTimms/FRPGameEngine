package spikeWork;

import org.engineFRP.Physics.collision.AABB;
import org.engineFRP.maths.Vector3f;
import org.junit.After;
import org.testng.annotations.Test;
import sodium.Stream;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertFalse;
import static junit.framework.TestCase.assertTrue;

/**
 * Created by TekMaTek on 09/03/2015.
 */
public class FRPCollisionTests {
    @After
    public void destroyDisplayAndKeyboard() throws Exception {
        System.gc();
        Thread.sleep(100);
    }

    @Test
    public void testAABBIsColliding() {
        Stream<Vector3f> vectorStream = new Stream<>();
        AABB collider1 = new AABB(
                vectorStream.hold(new Vector3f(-0.1f, -0.1f, 0.0f)),
                vectorStream.hold(new Vector3f(0.1f, 0.1f, 0.0f))
        );
        AABB collider2 = new AABB(
                vectorStream.hold(new Vector3f(-0.11f, -0.11f, 0.0f)),
                vectorStream.hold(new Vector3f(0.2f, 0.2f, 0.0f))
        );
        AABB collider3 = new AABB(
                vectorStream.hold(new Vector3f(0.61f, 0.61f, 0.0f)),
                vectorStream.hold(new Vector3f(0.62f, 0.62f, 0.0f))
        );
        assertTrue(collider1.isCollidingWith(collider2));
        assertTrue(collider2.isCollidingWith(collider1));

        assertFalse(collider3.isCollidingWith(collider1));
        assertFalse(collider1.isCollidingWith(collider3));
        assertFalse(collider2.isCollidingWith(collider3));
    }


//    @Test
//    public void testAABBIsCollidingInvertedMinMax() {
//        Stream<Vector3f> vectorStream = new Stream<>();
//        AABB collider1 = new AABB(
//                vectorStream.hold(new Vector3f(0.1f, 0.1f, 0.0f)),
//                vectorStream.hold(new Vector3f(-0.1f, -0.1f, 0.0f))
//        );
//        AABB collider2 = new AABB(
//                vectorStream.hold(new Vector3f(0.11f, 0.11f, 0.0f)),
//                vectorStream.hold(new Vector3f(-0.2f, -0.2f, 0.0f))
//        );
//        assertTrue(collider1.isCollidingWith(collider2));
//        assertTrue(collider2.isCollidingWith(collider1));
//    }

    @Test
    public void testAABBResolution() {
        Stream<Vector3f> vectorStream = new Stream<>();
        AABB collider1 = new AABB(
                vectorStream.hold(new Vector3f(-0.65f, -0.35f, 0.0f)),
                vectorStream.hold(new Vector3f(-0.2f, -0.1f, 0.0f))
        );
        AABB collider2 = new AABB(
                new Stream<Vector3f>().hold(new Vector3f(-0.6f, -0.6f, 0.0f)),
                new Stream<Vector3f>().hold(new Vector3f(0.6f, 0.6f, 0.0f))
        );
        assertEquals(new Vector3f(0.0f, 0.0f, 0.0f), collider1.resolveCollision(collider2));
    }


    @Test
    public void testAABBResolution2() {
        Stream<Vector3f> vectorStream = new Stream<>();
        AABB collider1 = new AABB(
                vectorStream.hold(new Vector3f(-0.65f, -0.65f, 0.0f)),
                vectorStream.hold(new Vector3f(-0.2f, -0.1f, 0.0f))
        );
        AABB collider2 = new AABB(
                new Stream<Vector3f>().hold(new Vector3f(-0.6f, -0.6f, 0.0f)),
                new Stream<Vector3f>().hold(new Vector3f(0.6f, 0.6f, 0.0f))
        );
        assertEquals(new Vector3f(0.0f, 0.0f, 0.0f), collider1.resolveCollision(collider2));
    }
}