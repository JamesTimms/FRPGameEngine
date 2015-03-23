package org.engineFRP.rendering;

import org.engineFRP.core.FRPDisplay;
import org.junit.Before;
import org.junit.Test;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertTrue;
import static org.junit.Assert.assertFalse;
import static org.lwjgl.opengl.GL11.*;

public class TextureTests {

    private static final String RES_PATH = "./res/textures/";
    private static final String TEXTURE_FILE = "checkers.png";

    @Before
    public void createDisplay() {
        FRPDisplay.createForTesting();
    }

    @Test
    public void testBuildTexture() {
        Texture testTexture = Texture.BuildTexture(0);
        Texture testTexture2 = Texture.BuildTexture(1);
        Texture testTexture3 = Texture.BuildTexture(0);
        Texture testTexture4 = Texture.BuildTexture(-44);

        assertEquals(0, testTexture.id);
        assertEquals(1, testTexture2.id);
        assertEquals(0, testTexture3.id);
        assertEquals(-44, testTexture4.id);
    }


    @Test
    public void testNoTexture() {
        Texture testTexture = Texture.NoTexture();
        assertEquals(0, testTexture.id);
    }

    @Test
    public void testLoadTexture() {
        Texture testTexture = Texture.loadTexture(RES_PATH + TEXTURE_FILE);
        Texture testTexture2 = Texture.loadTexture(RES_PATH + TEXTURE_FILE);
        assertEquals(1, testTexture.id);
        assertEquals(2, testTexture2.id);
    }

    @Test
    public void testBind() {
        assertTrue(glGetInteger(GL_TEXTURE_BINDING_2D) == 0);
        Texture testTexture = Texture.loadTexture(RES_PATH + TEXTURE_FILE);
        Texture testTexture2 = Texture.loadTexture(RES_PATH + TEXTURE_FILE);
        assertFalse(glGetInteger(GL_TEXTURE_BINDING_2D) == 1);
        assertTrue(glGetInteger(GL_TEXTURE_BINDING_2D) == 0);

        testTexture.bind();
        assertTrue(glGetInteger(GL_TEXTURE_BINDING_2D) == 1);
        testTexture2.bind();
        assertTrue(glGetInteger(GL_TEXTURE_BINDING_2D) == 2);
    }
}