package sodiumFRPtests;

import org.junit.After;

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
//    @Test(expected=Exception.class)
//    public void testErrorSetup() {
//        ErrorHandling.create();
//        glfwDefaultWindowHints();//This line should throw an error.
//        ErrorHandling.destroy();
//    }

}
