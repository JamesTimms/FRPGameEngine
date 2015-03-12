package org.engineFRP.rendering;

import org.engineFRP.maths.Matrix4f;
import org.engineFRP.maths.Vector3f;
import org.lwjgl.BufferUtils;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;

import static org.lwjgl.opengl.GL11.*;

/**
 * Created by TekMaTek on 27/10/2014.
 */
public class RenderingUtil {

	protected static FloatBuffer createFloatBuffer( int size ) {
		return BufferUtils.createFloatBuffer(size);
	}

	protected static IntBuffer createIntBuffer( int size ) {
		return BufferUtils.createIntBuffer(size);
	}

	public static FloatBuffer createFlippedBuffer( Vertex[] vertices ) {
		FloatBuffer buffer = createFloatBuffer( vertices.length * Vertex.SIZE );

		for( int i = 0; i < vertices.length; i++ ) {
			buffer.put( vertices[ i ].getPos().getX( ) );
			buffer.put( vertices[ i ].getPos().getY( ) );
			buffer.put( vertices[ i ].getPos().getZ( ) );
			buffer.put( vertices[ i ].getTexCoord( ).getX( ) );
			buffer.put( vertices[ i ].getTexCoord( ).getY( ) );
			buffer.put( vertices[ i ].getNormal( ).getX( ) );
			buffer.put( vertices[ i ].getNormal( ).getY( ) );
			buffer.put( vertices[ i ].getNormal( ).getZ( ) );
		}

		buffer.flip( );
		return buffer;
	}

	public static IntBuffer createFlippedBuffer( int... values ) {
		IntBuffer buffer = createIntBuffer( values.length );
		buffer.put( values );
		buffer.flip( );
		return buffer;
	}

	public static void setClearColor( Vector3f colour ) {
		glClearColor( colour.getX( ), colour.getY( ), colour.getZ( ), 1.0f );
	}

	public static void setTexture( boolean enabled ) {
		if( enabled ) {
			glEnable( GL_TEXTURE_2D );
		} else {
			glDisable( GL_TEXTURE_2D );
		}
	}

	public static FloatBuffer createFlippedBuffer( Matrix4f matrix ) {
		FloatBuffer buffer = createFloatBuffer( 4 * 4 );

		for( int i = 0; i < 4; i++ ) {
			for( int j = 0; j < 4; j++ ) {
				buffer.put( matrix.get( i, j ) );
			}
		}
		buffer.flip( );
		return buffer;
	}

	public static String GetOpenGLVersion( ) {
		return "OpenGL Version: " + glGetString( GL_VERSION );
	}

	public static String[] removeEmptyString( String[] tokens ) {
		ArrayList< String > result = new ArrayList< String >( );

		for( int i = 0; i < tokens.length; i++ ) {
			if( !tokens[ i ].equals( "" ) )
				result.add( tokens[ i ] );
		}

		String[] res = new String[ result.size( ) ];
		result.toArray( res );
		return res;
	}

	public static int[] toIntArray( Integer[] indices ) {
		int[] result = new int[ indices.length ];
		for( int i = 0; i < indices.length; i++ ) {
			result[ i ] = indices[ i ].intValue( );
		}
		return result;
	}

	public static void unbindTextures( ) {
		glBindTexture( GL_TEXTURE_2D, 0 );
	}
}