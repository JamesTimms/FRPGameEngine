package org.FRPengine.rendering;


import org.FRPengine.maths.Vector2f;
import org.FRPengine.maths.Vector3f;

/**
 * Created by TekMaTek on 27/10/2014.
 */
public class Vertex {

	public static final int SIZE = 8;

	protected Vector3f position;
	protected Vector2f texCoord;
	protected Vector3f normal;

	public Vertex( Vector3f position ) {
		this( position, Vector2f.ZERO, Vector3f.ZERO );
	}

	public Vertex( Vector3f position, Vector2f texCoord ) {
		this.position = position;
		this.texCoord = texCoord;
		this.normal = Vector3f.ZERO;
	}

	public Vertex( Vector3f position, Vector2f texCoord, Vector3f normal ) {
		this.position = position;
		this.texCoord = texCoord;
		this.normal = normal;
	}

	public Vector3f getPosition( ) {
		return position;
	}

	public void setPosition( Vector3f position ) {
		this.position = position;
	}

	public Vector2f getTexCoord( ) {
		return texCoord;
	}

	public void setTexCoord( Vector2f texCoord ) {
		this.texCoord = texCoord;
	}

	public Vector3f getNormal( ) {
		return normal;
	}

	public void setNormal( Vector3f normal ) {
		this.normal = normal;
	}
}
