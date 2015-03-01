package Personal;

import org.FRPengine.core.Camera;
import org.FRPengine.core.FRPDisplay;
import org.FRPengine.core.FRPKeyboard;
import org.FRPengine.maths.Vector3f;
import org.FRPengine.rendering.SimpleRenderer;
import sodium.Listener;
import sodium.StreamSink;

import static org.lwjgl.glfw.GLFW.GLFW_KEY_LEFT;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_RIGHT;
import static org.lwjgl.glfw.GLFW.GLFW_PRESS;

/**
 * Created by TekMaTek on 26/02/2015.
 */
public class Demo2 {

    public static void main(String[] args) {
        new Demo2();
    }

    public Demo2() {
        FRPDisplay.Create();
        FRPKeyboard.Create();
        SimpleRenderer.init();
        loop();
    }

//    private static Listener renderLoopListener;
    public static void loop() {
        StreamSink<Long> renderLoop = new StreamSink<>();
//        renderLoopListener = renderLoop
//                .filter(Time::readyForFrameRate)
//                .listen(cursor -> Render());
        Listener listen = FRPKeyboard.keyStream
                .filter(key -> key.key == GLFW_KEY_RIGHT && key.action == GLFW_PRESS)
                .listen(x -> {
                    Camera.mainCamera.translation = Camera.mainCamera.translation
                            .add(new Vector3f(1.0f, 0.0f, 0.0f));
                });
        Listener listen2 = FRPKeyboard.keyStream
                .filter(key -> key.key == GLFW_KEY_LEFT && key.action == GLFW_PRESS)
                .listen(x -> {
                    Camera.mainCamera.translation = Camera.mainCamera.translation
                            .add(new Vector3f(-1.0f, 0.0f, 0.0f));
                });

        while(!FRPDisplay.shouldWindowClose()) {
            SimpleRenderer.Render();
//            renderLoop.send(Time.TEN_PER_SECOND);
        }
//        renderLoopListener.unlisten();
        listen.unlisten();
        listen2.unlisten();
    }
}