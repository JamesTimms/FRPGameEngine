package org.engineFRP.collision;

import org.engineFRP.Util.Click;
import org.engineFRP.maths.Vector2f;
import org.engineFRP.rendering.Manafolds.Rect;
import org.engineFRP.rendering.Manafolds.Shape;
import org.junit.After;
import org.junit.Test;

import static junit.framework.TestCase.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Created by TekMaTek on 10/03/2015.
 */
public class PointInPolygonTests {

    @After
    public void tearDown() throws Exception {
        System.gc();
        Thread.sleep(100);
    }

//    @Test
//    public void testPointInPolygon() {
//        Shape shape = new Rect();
//        assertTrue(new Click(new Vector2f(0.04f, -0.02f)).isInPolygon(shape));
//        assertTrue(new Click(new Vector2f(0.02f, 0.0f)).isInPolygon(shape));
//        assertTrue(new Click(new Vector2f(0.0f, 0.0f)).isInPolygon(shape));
//        assertTrue(new Click(new Vector2f(-0.04f, -0.04f)).isInPolygon(shape));
//        assertTrue(new Click(new Vector2f(-0.04999f, -0.04999f)).isInPolygon(shape));
//        assertTrue(new Click(new Vector2f(0.04999f, 0.04999f)).isInPolygon(shape));
//
//        assertFalse(new Click(new Vector2f(0.05f, 0.05f)).isInPolygon(shape));
//        assertFalse(new Click(new Vector2f(-0.05f, -0.05f)).isInPolygon(shape));
//        assertFalse(new Click(new Vector2f(-0.07f, 0.07f)).isInPolygon(shape));
//        assertFalse(new Click(new Vector2f(-0.27f, 11111.07f)).isInPolygon(shape));
//        assertFalse(new Click(new Vector2f(Float.MAX_VALUE, Float.MAX_VALUE)).isInPolygon(shape));
//        assertFalse(new Click(new Vector2f(-11111111111111111.0f, -1111111111111111.0f)).isInPolygon(shape));
//    }
//
//    @Test
//    public void testPointInPolygon2() {
//        Shape shape = new Rect(1.0f);
//        assertTrue(new Click(new Vector2f(0.4f, -0.02f)).isInPolygon(shape));
//        assertTrue(new Click(new Vector2f(0.02f, 0.0f)).isInPolygon(shape));
//        assertTrue(new Click(new Vector2f(0.0f, 0.0f)).isInPolygon(shape));
//        assertTrue(new Click(new Vector2f(-0.4f, -0.04f)).isInPolygon(shape));
//        assertTrue(new Click(new Vector2f(-0.04999f, -0.04999f)).isInPolygon(shape));
//        assertTrue(new Click(new Vector2f(0.4999f, 0.4999f)).isInPolygon(shape));
//
//        assertFalse(new Click(new Vector2f(0.5f, 0.5f)).isInPolygon(shape));
//        assertFalse(new Click(new Vector2f(-0.5f, -0.5f)).isInPolygon(shape));
//        assertFalse(new Click(new Vector2f(-0.7f, 0.7f)).isInPolygon(shape));
//        assertFalse(new Click(new Vector2f(-0.27f, 11111.07f)).isInPolygon(shape));
//        assertFalse(new Click(new Vector2f(Float.MAX_VALUE, Float.MAX_VALUE)).isInPolygon(shape));
//        assertFalse(new Click(new Vector2f(-11111111111111111.0f, -1111111111111111.0f)).isInPolygon(shape));
//    }
}