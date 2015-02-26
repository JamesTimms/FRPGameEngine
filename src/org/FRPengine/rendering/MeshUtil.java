package org.FRPengine.rendering;

import org.FRPengine.maths.Vector2f;
import org.FRPengine.maths.Vector3f;


/**
 * Created by TekMaTek on 01/02/2015.
 */
public class MeshUtil {

	public static Mesh BuildSimplePlane( ) {
		final float FIELD_DEPTH = 10.0f;
		final float FIELD_WIDTH = 10.0f;
		Vertex[] vertices = new Vertex[] {
				new Vertex(
						new Vector3f( -FIELD_WIDTH, 0.0f, -FIELD_DEPTH ),
						new Vector2f( 0.0f, 0.0f ),
						Vector3f.ZERO ),
				new Vertex(
						new Vector3f( -FIELD_WIDTH, 0.0f, FIELD_DEPTH * 3.0f ),
						new Vector2f( 0.0f, 1.0f ),
						Vector3f.ZERO ),
				new Vertex(
						new Vector3f( FIELD_WIDTH * 3.0f, 0.0f, -FIELD_DEPTH ),
						new Vector2f( 1.0f, 0.0f ),
						Vector3f.ZERO ),
				new Vertex(
						new Vector3f( FIELD_WIDTH * 3.0f, 0.0f, FIELD_DEPTH * 3.0f ),
						new Vector2f( 1.0f, 1.0f ),
						Vector3f.ZERO )
		};

		int indices[] = {
				0, 1, 2,
				2, 1, 3
		};

		return new Mesh( vertices, indices, true );
	}

	public static Mesh BuildSimpleCube( ) {
        Vertex[] vertices = new Vertex[] {
				new Vertex(
						new Vector3f( -1.0f, -1.0f, -1.0f ),
						new Vector2f( 0.0f, 0.0f ),
						Vector3f.ZERO ),
				new Vertex(
						new Vector3f( 1.0f, -1.0f, -1.0f ),
						new Vector2f( 0.0f, 0.0f ),
						Vector3f.ZERO ),
				new Vertex(
						new Vector3f( 1.0f, 1.0f, -1.0f ),
						new Vector2f( 0.0f, 0.0f ),
						Vector3f.ZERO ),
				new Vertex(
						new Vector3f( -1.0f, 1.0f, -1.0f ),
						new Vector2f( 0.0f, 0.0f ),
						Vector3f.ZERO ),

				new Vertex(
						new Vector3f( -1.0f, -1.0f, 1.0f ),
						new Vector2f( 0.0f, 0.0f ),
						Vector3f.ZERO ),
				new Vertex(
						new Vector3f( 1.0f, -1.0f, 1.0f ),
						new Vector2f( 0.0f, 0.0f ),
						Vector3f.ZERO ),
				new Vertex(
						new Vector3f( 1.0f, 1.0f, 1.0f ),
						new Vector2f( 0.0f, 0.0f ),
						Vector3f.ZERO ),
				new Vertex(
						new Vector3f( -1.0f, 1.0f, 1.0f ),
						new Vector2f( 0.0f, 0.0f ),
						Vector3f.ZERO ),

				new Vertex(
						new Vector3f( -1.0f, -1.0f, -1.0f ),
						new Vector2f( 0.0f, 0.0f ),
						Vector3f.ZERO ),
				new Vertex(
						new Vector3f( -1.0f, 1.0f, -1.0f ),
						new Vector2f( 0.0f, 0.0f ),
						Vector3f.ZERO ),
				new Vertex(
						new Vector3f( -1.0f, 1.0f, 1.0f ),
						new Vector2f( 0.0f, 0.0f ),
						Vector3f.ZERO ),
				new Vertex(
						new Vector3f( -1.0f, -1.0f, 1.0f ),
						new Vector2f( 0.0f, 0.0f ),
						Vector3f.ZERO ),

				new Vertex(
						new Vector3f( 1.0f, -1.0f, -1.0f ),
						new Vector2f( 0.0f, 0.0f ),
						Vector3f.ZERO ),
				new Vertex(
						new Vector3f( 1.0f, 1.0f, -1.0f ),
						new Vector2f( 0.0f, 0.0f ),
						Vector3f.ZERO ),
				new Vertex(
						new Vector3f( 1.0f, 1.0f, 1.0f ),
						new Vector2f( 0.0f, 0.0f ),
						Vector3f.ZERO ),
				new Vertex(
						new Vector3f( 1.0f, -1.0f, 1.0f ),
						new Vector2f( 0.0f, 0.0f ),
						Vector3f.ZERO ),

				new Vertex(
						new Vector3f( -1.0f, -1.0f, -1.0f ),
						new Vector2f( 0.0f, 0.0f ),
						Vector3f.ZERO ),
				new Vertex(
						new Vector3f( -1.0f, -1.0f, 1.0f ),
						new Vector2f( 0.0f, 0.0f ),
						Vector3f.ZERO ),
				new Vertex(
						new Vector3f( 1.0f, -1.0f, 1.0f ),
						new Vector2f( 0.0f, 0.0f ),
						Vector3f.ZERO ),
				new Vertex(
						new Vector3f( 1.0f, -1.0f, -1.0f ),
						new Vector2f( 0.0f, 0.0f ),
						Vector3f.ZERO ),

				new Vertex(
						new Vector3f( -1.0f, 1.0f, -1.0f ),
						new Vector2f( 0.0f, 0.0f ),
						Vector3f.ZERO ),
				new Vertex(
						new Vector3f( -1.0f, 1.0f, 1.0f ),
						new Vector2f( 0.0f, 0.0f ),
						Vector3f.ZERO ),
				new Vertex(
						new Vector3f( 1.0f, 1.0f, 1.0f ),
						new Vector2f( 0.0f, 0.0f ),
						Vector3f.ZERO ),
				new Vertex(
						new Vector3f( 1.0f, 1.0f, -1.0f ),
						new Vector2f( 0.0f, 0.0f ),
						Vector3f.ZERO )
		};

		int indices[] = {
				0, 1, 2,
				0, 2, 3,

				4, 5, 6,
				4, 6, 7,

				8, 9, 10,
				8, 10, 11,

				12, 13, 14,
				12, 14, 15,

				16, 17, 18,
				16, 18, 19,

				20, 21, 22,
				20, 22, 23
		};

		return new Mesh( vertices, indices, true );
	}
}
