package spikeWork;

import org.engineFRP.FRP.FRPDisplay;
import org.engineFRP.FRP.FRPKeyboard;
import org.engineFRP.FRP.FRPUtil;
import org.engineFRP.maths.Vector3f;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import sodium.Cell;
import sodium.Stream;
import sodium.StreamSink;

import static junit.framework.TestCase.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.lwjgl.glfw.GLFW.*;

/**
 * Created by TekMaTek on 22/02/2015.
 */
public class FRPKeyboardTests {

    @Before
    public void createDisplayAndKeyboard() {
        FRPDisplay.createForTesting();
        FRPKeyboard.create();
    }

    @After
    public void destroyDisplayAndKeyboard() throws Exception {
        FRPKeyboard.Destroy();
        FRPDisplay.destroy();
        System.gc();
        Thread.sleep(100);
    }

    @Test
    public void testKeyStream() {
        Cell<FRPKeyboard.Key> keyPress = FRPKeyboard.keyEvent.hold(null);
        assertEquals(null, keyPress.sample());

        FRPKeyboard.Key key = new FRPKeyboard.Key(GLFW_KEY_SPACE, GLFW_PRESS);
        FRPKeyboard.fakeKeyEvent(key);
        assertEquals(key, keyPress.sample());
    }

    @Test
    public void testKeyStream2() {
        Cell<FRPKeyboard.Key> keyPress = FRPKeyboard.keyEvent.hold(null);
        assertEquals(null, keyPress.sample());

        FRPKeyboard.Key key = new FRPKeyboard.Key(GLFW_KEY_SPACE, GLFW_PRESS);
        FRPKeyboard.fakeKeyEvent(key);
        assertEquals(key, keyPress.sample());
    }

    @Test
    public void testFilterKeyStream() {
        Cell<FRPKeyboard.Key> keyPress = FRPKeyboard.keyEvent
                .filter(key -> key.key == GLFW_KEY_SPACE)
                .hold(null);

        FRPKeyboard.Key key = new FRPKeyboard.Key(GLFW_KEY_RIGHT, GLFW_PRESS);
        FRPKeyboard.fakeKeyEvent(key);
        assertEquals(null, keyPress.sample());

        FRPKeyboard.Key key2 = new FRPKeyboard.Key(GLFW_KEY_SPACE, GLFW_PRESS);
        FRPKeyboard.fakeKeyEvent(key2);
        assertEquals(key2, keyPress.sample());
    }

    @Test
    public void testMapKeyStream() {
        Cell<String> keyPress = FRPKeyboard.keyEvent
                .map(key -> "" + key.action)
                .hold(null);

        FRPKeyboard.Key key = new FRPKeyboard.Key(GLFW_KEY_SPACE, GLFW_PRESS);
        FRPKeyboard.fakeKeyEvent(key);
        assertEquals("" + key.action, keyPress.sample());
    }

    @Test
    public void testArrowMovement() {
        Cell<Vector3f> keyPress = FRPUtil.mapArrowKeysToMovementOf(-0.1f)
                .accum(Vector3f.ZERO, (a, b) -> Vector3f.add(a, b));

        FRPKeyboard.fakeKeyEvent(new FRPKeyboard.Key(GLFW_KEY_UP, GLFW_PRESS));
        assertEquals(0.1f, keyPress.sample().y);

        FRPKeyboard.fakeKeyEvent(new FRPKeyboard.Key(GLFW_KEY_DOWN, GLFW_PRESS));
        assertEquals(0.0f, keyPress.sample().y);

        FRPKeyboard.fakeKeyEvent(new FRPKeyboard.Key(GLFW_KEY_RIGHT, GLFW_PRESS));
        assertEquals(0.1f, keyPress.sample().x);

        FRPKeyboard.fakeKeyEvent(new FRPKeyboard.Key(GLFW_KEY_LEFT, GLFW_PRESS));
        assertEquals(0.0f, keyPress.sample().x);
    }

    @Test
    public void testMultiMergeUndesirableBehaviour() {
        StreamSink<Float> sink = new StreamSink<>();
        Stream<String> s1 = sink
                .map(Object::toString);
        Stream<String> s2 = sink
                .map(thing -> thing.toString() + " hi");
        Cell<Float> floatCell = sink
                .hold(0.0f);
        Cell<String> stringCell = s1
                .merge(s2)
                .hold("");
        Cell<String> stringCell2 = s2
                .merge(s1)
                .hold("");

        /* The stream being merged into another stream overrides the first stream
         * if the source of both streams is the same. This is undesirable behaviour!
         *///The actual solution is below testDoubleKeyPressStream()
        sink.send(0.1f);
        assertEquals(0.1f, floatCell.sample());
        assertEquals("0.1 hi", stringCell.sample());
        assertEquals("0.1", stringCell2.sample());
    }

    @Test
    public void testDoubleKeyPressStream() {
        Cell<Vector3f> testCell = FRPUtil.mapArrowKeysToMovementOf(-0.1f)
                .merge(FRPUtil.mapArrowKeysToMovementOf(-0.1f), (f, s) -> f.add(s))
                .hold(Vector3f.ZERO);
        FRPKeyboard.keyEvent.send(new FRPKeyboard.Key(GLFW_KEY_RIGHT, GLFW_PRESS));
        Vector3f vec = testCell.sample();
        assertTrue(new Vector3f(0.2f, 0.0f, 0.0f).equals(vec));
    }
}