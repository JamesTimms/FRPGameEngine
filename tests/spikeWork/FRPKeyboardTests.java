package spikeWork;

import org.FRPengine.core.FRPDisplay;
import org.FRPengine.core.FRPKeyboard;
import org.junit.After;
import org.junit.Before;

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
    public void destroyDisplayAndKeyboard() {
        FRPKeyboard.Destroy();
        FRPDisplay.Destroy();
    }

//    @Test
//    public void testIsKeyPressedConcept() {
//        List<Integer> keysSent = new ArrayList<Integer>();
//        Listener allListeners = FRPKeyboard.keysPressed.listen(x -> {
//            keysSent.add(x);
//        });
//        FRPKeyboard.keysPressed.send(Keyboard.KEY_V);
//        allListeners.unlisten();
//        assertEquals(Arrays.asList(Keyboard.KEY_V), keysSent);
//    }
//
//    @Test
//    public void testIsKeyHeldConcept() {
//        List<Integer> keysSent = new ArrayList<Integer>();
//        Listener allListeners = FRPKeyboard.keysHeld.listen(x -> {
//            keysSent.add(x);
//        });
//        FRPKeyboard.keysHeld.send(Keyboard.KEY_V);
//        allListeners.unlisten();
//        assertEquals(Arrays.asList(Keyboard.KEY_V), keysSent);
//    }
//
//    @Test
//    public void testIsKeyUp() {
//        List<Integer> keysSent = new ArrayList<Integer>();
//        Listener allListeners = FRPKeyboard.keysUp.listen(x -> {
//            keysSent.add(x);
//        });
//        FRPKeyboard.keysUp.send(Keyboard.KEY_V);
//        allListeners.unlisten();
//        assertEquals(Arrays.asList(Keyboard.KEY_V), keysSent);
//    }
//    //TODO: Create a test that tests the LWJGL's IsKeyDown method. Will need to send fake keypress to system.
}