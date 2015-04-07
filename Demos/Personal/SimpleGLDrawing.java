package Personal;

import org.engineFRP.FRP.FRPDisplay;
import org.engineFRP.FRP.FRPKeyboard;
import org.engineFRP.FRP.FRPMouse;
import org.engineFRP.core.Engine;
import org.engineFRP.core.Game;
import org.engineFRP.core.Scene;
import org.engineFRP.rendering.SimpleRenderer;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL11.glEnd;
import static org.lwjgl.opengl.GL11.glVertex3f;

/**
 * Created by TekMaTek on 06/04/2015.
 */
public class SimpleGLDrawing {

    public static void main(String[] args) {
        new SimpleGLDrawing().stuff();
    }

    public void stuff() {
        FRPDisplay.create();
        FRPKeyboard.create();
        FRPMouse.create();
        SimpleRenderer.init();

//        glColor3i((int) color.x, (int) color.y, (int) color.z);
        while(!FRPDisplay.shouldWindowClose()){
            glBegin(GL_LINE);
            glVertex3f(0.0f, 0.0f, 0.0f);
            glVertex3f(1.0f, 1.0f, 0.0f);
            glEnd();
        }
    }
}
