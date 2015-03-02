package spikeWork;

import org.FRPengine.core.FRPDisplay;
import org.FRPengine.core.FRPKeyboard;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import sodium.Cell;

import static org.lwjgl.glfw.GLFW.*;
import static org.testng.AssertJUnit.assertEquals;

/**
 * Created by TekMaTek on 22/02/2015.
 */
public class FRPKeyboardTests {

    @Before
    public void createDisplayAndKeyboard() {
        FRPDisplay.Create();
        FRPKeyboard.Create();
    }
    
    @After
    public void destroyDisplayAndKeyboard() throws Exception {
        FRPKeyboard.Destroy();
        FRPDisplay.Destroy();
        System.gc();
        Thread.sleep(100);
    }

    @Test
    public void testKeyStream() {
        Cell<FRPKeyboard.Key> keyPress = FRPKeyboard.keyStream.hold(null);
        assertEquals(null, keyPress.sample());

        FRPKeyboard.Key key = new FRPKeyboard.Key(GLFW_KEY_SPACE, GLFW_PRESS);
        FRPKeyboard.keyStream.send(key);
        assertEquals(key, keyPress.sample());
    }

    @Test
    public void testFilterKeyStream() {
        Cell<FRPKeyboard.Key> keyPress = FRPKeyboard.keyStream
                .filter(key -> key.key == GLFW_KEY_SPACE)
                .hold(null);

        FRPKeyboard.Key key = new FRPKeyboard.Key(GLFW_KEY_RIGHT, GLFW_PRESS);
        FRPKeyboard.keyStream.send(key);
        assertEquals(null, keyPress.sample());

        FRPKeyboard.Key key2 = new FRPKeyboard.Key(GLFW_KEY_SPACE, GLFW_PRESS);
        FRPKeyboard.keyStream.send(key2);
        assertEquals(key2, keyPress.sample());
    }

    @Test
    public void testMapKeyStream() {
        Cell<String> keyPress = FRPKeyboard.keyStream
                .map(key -> "" + key.action)
                .hold(null);

        FRPKeyboard.Key key = new FRPKeyboard.Key(GLFW_KEY_SPACE, GLFW_PRESS);
        FRPKeyboard.keyStream.send(key);
        assertEquals("" + key.action, keyPress.sample());
    }
}