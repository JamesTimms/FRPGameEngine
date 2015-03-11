package org.FRPengine.rendering;

import org.FRPengine.Physics.Manafolds.Square;
import org.FRPengine.maths.Vector3f;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;

import static org.lwjgl.opengl.GL15.*;


/**
 * Created by TekMaTek on 27/10/2014.
 */
public class Mesh {

	public int vertexBO;
    public int indexBO;
    public int indicesLength;
    public int vertexLength;

    public Square dummySquare = new Square();

	public Mesh( String filename ) {
		initMeshData( );
		loadMesh(filename);
	}

	public Mesh( Vertex[] vertices, int[] indices ) {
		this(vertices, indices, false);
	}

	public Mesh( Vertex[] vertices, int[] indices, boolean shouldCalcNormals ) {
		initMeshData( );
		addVertices( vertices, indices, shouldCalcNormals );
	}

    public void addVertices(Vertex[] vertices) {
        indicesLength = vertices.length * Vertex.SIZE;
        vertexLength = vertices.length;
        glBindBuffer(GL_ARRAY_BUFFER, vertexBO);
        glBufferData(GL_ARRAY_BUFFER, RenderingUtil.createFlippedBuffer(vertices), GL_STATIC_DRAW);
    }
    
    private void addVertices( Vertex[] vertices, int[] indices ) {
        addVertices(vertices, indices, false);
    }

    private void addVertices( Vertex[] vertices, int[] indices, boolean shouldCalcNormals ) {
        if( shouldCalcNormals ) {
            calcNormals( vertices, indices );
        }
        indicesLength = indices.length;
        vertexLength = vertices.length;
        glBindBuffer(GL_ARRAY_BUFFER, vertexBO);
        glBufferData( GL_ARRAY_BUFFER, RenderingUtil.createFlippedBuffer( vertices ), GL_STATIC_DRAW );

        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, indexBO);
        glBufferData(GL_ELEMENT_ARRAY_BUFFER, RenderingUtil.createFlippedBuffer(indices), GL_STATIC_DRAW);
    }

	private void initMeshData( ) {
		vertexBO = glGenBuffers( );
		indexBO = glGenBuffers( );
		indicesLength = 0;
        vertexLength = 0;
	}

	private void loadMesh( String filename ) {
		String[] splitArray = filename.split( "\\." );
		String ext = splitArray[ splitArray.length - 1 ];

		if( !ext.equals( "obj" ) ) {
			System.err.println( "Error: File formate not supported for mesh data: " + ext );
			new Exception( ).printStackTrace( );
			System.exit( 1 );
		}

		ArrayList< Vertex > vertices = new ArrayList< Vertex >( );
		ArrayList< Integer > indices = new ArrayList< Integer >( );

		BufferedReader meshReader = null;
		try {
			meshReader = new BufferedReader( new FileReader( "./resource/models/" + filename ) );
			String line;
			while( ( line = meshReader.readLine( ) ) != null ) {
				String[] tokens = line.split( " " );
				tokens = RenderingUtil.removeEmptyString( tokens );
				if( tokens.length == 0 || tokens[ 0 ].equals( '#' ) ) {
					continue;
				} else if( tokens[ 0 ].equals( "v" ) ) {
					vertices.add( new Vertex( new Vector3f(
							Float.valueOf( tokens[ 1 ] ),
							Float.valueOf( tokens[ 2 ] ),
							Float.valueOf( tokens[ 3 ] ) ) ) );
				} else if( tokens[ 0 ].equals( "f" ) ) {
					indices.add( Integer.parseInt( tokens[ 1 ].split( "/" )[ 0 ] ) - 1 );
					indices.add( Integer.parseInt( tokens[ 2 ].split( "/" )[ 0 ] ) - 1 );
					indices.add( Integer.parseInt( tokens[ 3 ].split( "/" )[ 0 ] ) - 1 );
					if( tokens.length > 4 ) {
						indices.add( Integer.parseInt( tokens[ 1 ].split( "/" )[ 0 ] ) - 1 );
						indices.add( Integer.parseInt( tokens[ 3 ].split( "/" )[ 0 ] ) - 1 );
						indices.add( Integer.parseInt( tokens[ 4 ].split( "/" )[ 0 ] ) - 1 );
					}
				}
			}
			meshReader.close( );
		} catch( Exception exception ) {
			exception.printStackTrace( );
			System.exit( 1 );
		}

		Vertex[] vertexData = new Vertex[ vertices.size( ) ];
		vertices.toArray( vertexData );

		Integer[] indexData = new Integer[ indices.size( ) ];
		indices.toArray( indexData );

		this.addVertices( vertexData, RenderingUtil.toIntArray( indexData ) );
	}

	private void calcNormals( Vertex[] vertices, int[] indices ) {
		for( int i = 0; i < indices.length; i += 3 ) {
			int i0 = indices[ i ];
			int i1 = indices[ i + 1 ];
			int i2 = indices[ i + 2 ];

			Vector3f v1 = vertices[ i1 ].getPos().sub( vertices[ i0 ].getPos() );
			Vector3f v2 = vertices[ i2 ].getPos().sub( vertices[ i0 ].getPos() );

			Vector3f normal = v1.cross( v2 ).normalized( );
			System.out.println( normal );
			vertices[ i0 ].setNormal( vertices[ i0 ].getNormal( ).add( normal ) );
			vertices[ i1 ].setNormal( vertices[ i1 ].getNormal( ).add( normal ) );
			vertices[ i2 ].setNormal( vertices[ i2 ].getNormal( ).add( normal ) );
		}

		for( int i = 0; i < vertices.length; i++ ) {
			vertices[ i ].setNormal( vertices[ i ].getNormal( ).normalized( ) );
		}
	}
}
