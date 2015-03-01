package Personal;

import org.FRPengine.core.FRPDisplay;
import org.FRPengine.core.FRPKeyboard;
import org.FRPengine.rendering.SimpleRenderer;

/**
 * Created by TekMaTek on 01/03/2015.
 */
public class Demo3 {

    public static void main(String[] args) {
        new Demo3();
    }

    public Demo3() {
        FRPDisplay.Create();
        FRPKeyboard.Create();
        SimpleRenderer.init();
        loop();
    }

    public static void loop() {
        while(!FRPDisplay.shouldWindowClose()) {
            SimpleRenderer.Render();
        }
    }
}
