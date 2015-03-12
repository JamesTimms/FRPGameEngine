package org.engineFRP.rendering;

import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D;
import static org.lwjgl.opengl.GL11.glBindTexture;

/**
 * Created by TekMaTek on 26/01/2015.
 */
public class Texture {

	public int id;

	private Texture() {
	}

	public static Texture BuildTexture( int id ) {
		Texture newTexture = new Texture( );
		newTexture.id = id;

		return newTexture;
	}

	public static Texture NoTexture( ) {
		return BuildTexture( 0 );
	}

	public static Texture loadTexture( String filename ) {
		String[] splitArray = filename.split( "\\." );
		String ext = splitArray[ splitArray.length - 1 ];

		try {
            int id = 0;
//			int id = TextureLoader.getTexture( ext, new FileInputStream( "./resource/textures/" + filename ) )
//								  .getTextureID( );
			return BuildTexture( id );
		} catch( Exception ex ) {
			ex.printStackTrace( );
			System.exit( 1 );
		}

		return null;
	}

	public void bind( ) {
		glBindTexture( GL_TEXTURE_2D, id );
	}
}
