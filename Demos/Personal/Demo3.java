package Personal;

import org.FRPengine.core.FRPDisplay;
import org.FRPengine.core.FRPKeyboard;
import org.FRPengine.core.Time;
import org.FRPengine.core.Transform;
import org.FRPengine.maths.Vector3f;
import org.FRPengine.rendering.MeshUtil;
import org.FRPengine.rendering.SimpleRenderer;
import org.FRPengine.rendering.shaders.BasicShader;
import org.FRPengine.rendering.shaders.Shader;

import static org.lwjgl.glfw.GLFW.glfwPollEvents;
import static org.lwjgl.glfw.GLFW.glfwSwapBuffers;
import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.glClear;

/**
 * Created by TekMaTek on 01/03/2015.
 */
public class Demo3 {

    public static void main(String[] args) {
        new Demo3();
    }

    static Time frameTimer = new Time();
    static Time renderTimer = new Time();
    static Time moveTimer = new Time();

    public Demo3() {
        FRPDisplay.Create();
        FRPKeyboard.Create();
        SimpleRenderer.init();
        loop();
    }

    public void loop() {
        shader2 = new BasicShader();
        sceneMeshes = new Transform(
                new Vector3f(-0.5f, 0.0f, 0.0f),
                MeshUtil.BuildSquare());

        sceneMeshes.translation = sceneMeshes.translation.value()
                .merge(FRPKeyboard.mapArrowKeysToMovementOf(-0.1f))
                .accum(new Vector3f(0.0f, 0.0f, 0.0f), Vector3f::add);

        sceneMeshes.setTranslation(new Vector3f(0.7f, 0.0f, 0.0f));


        while(!FRPDisplay.shouldWindowClose()) {
            if(frameTimer.shouldGetFrame(120)) {
                glfwPollEvents();
            }
            if(renderTimer.shouldGetFrame(Time.THIRTY_PER_SECOND)) {
                RenderDemo3();
            }
            if(moveTimer.shouldGetFrame(1)) {
                sceneMeshes.setTranslation(new Vector3f(0.02f, 0.0f, 0.0f));
            }
        }
    }

    public static void drawDemo3() {
//        for( Transform transform : sceneMeshes){
        shader2.draw(sceneMeshes);
//        }
    }

    static Shader shader2;
    private static Transform sceneMeshes;

    public void RenderDemo3() {
        glClear(GL_COLOR_BUFFER_BIT);
        drawDemo3();
        glfwSwapBuffers(FRPDisplay.GetWindow());
    }
}