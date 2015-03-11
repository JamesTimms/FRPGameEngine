package org.FRPengine.maths;

/**
 * Based on BennyBox's GameEngine https://github.com/BennyQBD/3DGameEngine.
 * Created by TekMaTek on 05/08/2014.
 */
public class Vector2f {

	public static final Vector2f ONE = new Vector2f( 1.0f, 1.0f );
	public static final Vector2f ZERO = new Vector2f( 0.0f, 0.0f );
	protected float x;
	protected float y;

	public Vector2f( float x, float y ) {
		this.x = x;
		this.y = y;
	}

	public float length( ) {
		return ( float ) Math.sqrt(x * x + y * y);
	}

	public float max( ) {
		return Math.max(x, y);
	}

	public float dot( Vector2f vector ) {
		return x * vector.getX( ) + y * vector.getY( );
	}

	public Vector2f normalized( ) {
		float length = length( );

		return new Vector2f( x / length, y / length );
	}

	public Vector2f lerp( Vector2f vector, float lerpFactor ) {
		return vector.sub( this ).mul( lerpFactor ).add( this );
	}

	public Vector2f rotate( float angle ) {
		double rad = Math.toRadians(angle);
		double cos = Math.cos(rad);
		double sin = Math.sin(rad);

		return new Vector2f( ( float ) ( x * cos - y * sin ), ( float ) ( x * sin + y * cos ) );
	}

	public Vector2f add( Vector2f vector ) {
		return new Vector2f( x + vector.getX( ), y + vector.getY( ) );
	}

	public Vector2f add( float vector ) {
		return new Vector2f( x + vector, y + vector );
	}

	public Vector2f sub( Vector2f vector ) {
		return new Vector2f( x - vector.getX( ), y - vector.getY( ) );
	}

	public Vector2f sub( float vector ) {
		return new Vector2f( x - vector, y - vector );
	}

	public Vector2f mul( Vector2f vector ) {
		return new Vector2f( x * vector.getX( ), y * vector.getY( ) );
	}

	public Vector2f mul( float vector ) {
		return new Vector2f( x * vector, y * vector );
	}

	public Vector2f div( Vector2f vector ) {
		return new Vector2f( x / vector.getX( ), y / vector.getY( ) );
	}

	public Vector2f div( float vector ) {
		return new Vector2f( x / vector, y / vector );
	}

	public Vector2f abs( ) {
		return new Vector2f( Math.abs(x), Math.abs(y) );
	}

	public String toString( ) {
		return "(" + x + " " + y + ")";
	}

	public Vector2f set( float x, float y ) {
		this.x = x;
		this.y = y;
		return this;
	}

	public Vector2f set( Vector2f vector ) {
		set( vector.getX( ), vector.getY( ) );
		return this;
	}

	public float getX( ) {
		return x;
	}

	public void setX( float x ) {
		this.x = x;
	}

	public float getY( ) {
		return y;
	}

	public void setY( float y ) {
		this.y = y;
	}

	public boolean equals( Vector2f vector ) {
		return x == vector.getX( ) && y == vector.getY( );
	}
}
