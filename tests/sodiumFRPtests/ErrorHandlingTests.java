package sodiumFRPtests;

import org.FRPengine.core.ErrorHandling;
import org.junit.After;
import org.junit.Test;

import static org.lwjgl.glfw.GLFW.glfwDefaultWindowHints;

/**
 * Created by TekMaTek on 26/02/2015.
 */
public class ErrorHandlingTests {
    @After
    public void tearDown() throws Exception {
        System.gc();
        Thread.sleep(100);
    }
    //TODO: look up how error handling works in lwjgl3
    @Test
    public void testErrorSetup() {
        ErrorHandling.Init();
        glfwDefaultWindowHints();//This line should throw an error.
        ErrorHandling.Destroy();
    }

}
