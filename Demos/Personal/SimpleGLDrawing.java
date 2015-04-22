package Personal;

import org.engineFRP.FRP.FRPDisplay;
import org.engineFRP.rendering.SimpleRenderer;

import static org.lwjgl.glfw.GLFW.glfwPollEvents;
import static org.lwjgl.glfw.GLFW.glfwSwapBuffers;
import static org.lwjgl.opengl.GL11.*;

/**
 * Created by TekMaTek on 06/04/2015.
 */
public class SimpleGLDrawing {

    public static void main(String[] args) {
        new SimpleGLDrawing().testDebugStuff();
    }

    public void testDebugStuff() {
        FRPDisplay.create();
        SimpleRenderer.init();

        while(!FRPDisplay.shouldWindowClose()) {
            glfwPollEvents();
            glClear(GL_COLOR_BUFFER_BIT);
            drawScene();
            glfwSwapBuffers(FRPDisplay.getWindow());
        }
    }

    protected static void render() {
        System.out.println("Hi");
    }

    //Based on http://en.wikibooks.org/wiki/OpenGL_Programming/Basics/2DObjects
    private void drawScene() {
        glColor3f(0.0f, 0.0f, 0.0f); // sets color to black.
        glBegin(GL_TRIANGLE_STRIP); // draw in triangle strips
        glVertex2f(0.0f, 0.075f); // top of the roof
        glVertex2f(-0.05f, 0.025f); // left corner of the roof
        glVertex2f(0.05f, 0.025f); // right corner of the roof
        glVertex2f(-0.05f, -0.05f); // bottom left corner of the house
        glVertex2f(0.05f, -0.05f); //bottom right corner of the house
        glEnd();

        glBegin(GL_LINES); // draw in triangle strips
        glVertex2f(0.0f, 0.75f); // top of the roof
        glVertex2f(-0.5f, 0.25f); // left corner of the roof
//        glVertex2f(0.5f, 0.25f); // right corner of the roof
//        glVertex2f(-0.5f, -0.5f); // bottom left corner of the house
//        glVertex2f(0.5f, -0.5f); //bottom right corner of the house
        glEnd();

        glPointSize(5.0f);
        glBegin(GL_POINTS);
        glVertex2f(0.2f, 0.8f);
        glEnd();
    }
}