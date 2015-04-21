package org.engineFRP.rendering;

import sodium.Lambda1;

import java.io.File;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL11.GL_REPEAT;

/**
 * Created by TekMaTek on 26/01/2015.
 */
public class Texture {

    public int id;
    public static final String BLANK_TEXTURE = "./res/textures/NoText.png";

    private Texture() {
    }

    public static Texture BuildTexture(int id) {
        Texture newTexture = new Texture();
        newTexture.id = id;

        return newTexture;
    }

    public static Texture BlankTexture() {
        return loadTexture(BLANK_TEXTURE);
    }

    public static Texture loadTexture(String filename) {
        try {
            int id = TextureLoader.loadTexture(TextureLoader.loadImage(new File(filename)));
            return BuildTexture(id);
        } catch(Exception ex) {
            ex.printStackTrace();
            System.exit(1);
        }
        return BlankTexture();
    }

    public void bind() {
        glBindTexture(GL_TEXTURE_2D, id);
    }

    public Texture changeSetting(Lambda1<Integer, Integer> settings) {
        changeTextureSetting(settings, this.id);
        return this;
    }

    public static Integer changeTextureSetting(Lambda1<Integer, Integer> settings, Integer textureID) {
        return settings.apply(textureID);
    }

    public static int RepeatTexture(int texID) {
        glBindTexture(GL_TEXTURE_2D, texID);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_REPEAT);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_REPEAT);
        glBindTexture(GL_TEXTURE_2D, 0);
        return texID;
    }
}
