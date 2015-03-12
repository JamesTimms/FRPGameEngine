package org.engineFRP.maths;

/**
 * Based on BennyBox's GameEngine https://github.com/BennyQBD/3DGameEngine.
 * Created by TekMaTek on 05/08/2014.
 */
public class Quaternion {

	private float x;
	private float y;
	private float z;
	private float w;

	public Quaternion( float x, float y, float z, float w ) {
		this.x = x;
		this.y = y;
		this.z = z;
		this.w = w;
	}

	public Quaternion( Vector3f axis, float angle ) {
		float sinHalfAngle = ( float ) Math.sin(angle / 2);
		float cosHalfAngle = ( float ) Math.cos(angle / 2);

		this.x = axis.getX( ) * sinHalfAngle;
		this.y = axis.getY( ) * sinHalfAngle;
		this.z = axis.getZ( ) * sinHalfAngle;
		this.w = cosHalfAngle;
	}

	public Quaternion( Matrix4f rot ) {
		float trace = rot.get( 0, 0 ) + rot.get( 1, 1 ) + rot.get( 2, 2 );

		if( trace > 0 ) {
			float s = 0.5f / ( float ) Math.sqrt(trace + 1.0f);
			w = 0.25f / s;
			x = ( rot.get( 1, 2 ) - rot.get( 2, 1 ) ) * s;
			y = ( rot.get( 2, 0 ) - rot.get( 0, 2 ) ) * s;
			z = ( rot.get( 0, 1 ) - rot.get( 1, 0 ) ) * s;
		} else {
			if( rot.get( 0, 0 ) > rot.get( 1, 1 ) && rot.get( 0, 0 ) > rot.get( 2, 2 ) ) {
				float s = 2.0f * ( float ) Math.sqrt(1.0f + rot.get(0, 0) - rot.get(1, 1) - rot.get(2, 2));
				w = ( rot.get( 1, 2 ) - rot.get( 2, 1 ) ) / s;
				x = 0.25f * s;
				y = ( rot.get( 1, 0 ) + rot.get( 0, 1 ) ) / s;
				z = ( rot.get( 2, 0 ) + rot.get( 0, 2 ) ) / s;
			} else if( rot.get( 1, 1 ) > rot.get( 2, 2 ) ) {
				float s = 2.0f * ( float ) Math.sqrt(1.0f + rot.get(1, 1) - rot.get(0, 0) - rot.get(2, 2));
				w = ( rot.get( 2, 0 ) - rot.get( 0, 2 ) ) / s;
				x = ( rot.get( 1, 0 ) + rot.get( 0, 1 ) ) / s;
				y = 0.25f * s;
				z = ( rot.get( 2, 1 ) + rot.get( 1, 2 ) ) / s;
			} else {
				float s = 2.0f * ( float ) Math.sqrt(1.0f + rot.get(2, 2) - rot.get(0, 0) - rot.get(1, 1));
				w = ( rot.get( 0, 1 ) - rot.get( 1, 0 ) ) / s;
				x = ( rot.get( 2, 0 ) + rot.get( 0, 2 ) ) / s;
				y = ( rot.get( 1, 2 ) + rot.get( 2, 1 ) ) / s;
				z = 0.25f * s;
			}
		}

		float length = ( float ) Math.sqrt(x * x + y * y + z * z + w * w);
		x /= length;
		y /= length;
		z /= length;
		w /= length;
	}

	public float length( ) {
		return ( float ) Math.sqrt(x * x + y * y + z * z + w * w);
	}

	public Quaternion normalized( ) {
		float length = length( );

		return new Quaternion( x / length, y / length, z / length, w / length );
	}

	public Quaternion conjugate( ) {
		return new Quaternion( -x, -y, -z, w );
	}

	public Quaternion mul( float factor ) {
		return new Quaternion( x * factor, y * factor, z * factor, w * factor );
	}

	public Quaternion mul( Quaternion quaternion ) {
		float w_ = w * quaternion.getW( ) - x * quaternion.getX( ) - y * quaternion.getY( ) - z * quaternion.getZ( );
		float x_ = x * quaternion.getW( ) + w * quaternion.getX( ) + y * quaternion.getZ( ) - z * quaternion.getY( );
		float y_ = y * quaternion.getW( ) + w * quaternion.getY( ) + z * quaternion.getX( ) - x * quaternion.getZ( );
		float z_ = z * quaternion.getW( ) + w * quaternion.getZ( ) + x * quaternion.getY( ) - y * quaternion.getX( );

		return new Quaternion( x_, y_, z_, w_ );
	}

	public Quaternion mul( Vector3f vector ) {
		float w_ = -x * vector.getX( ) - y * vector.getY( ) - z * vector.getZ( );
		float x_ = w * vector.getX( ) + y * vector.getZ( ) - z * vector.getY( );
		float y_ = w * vector.getY( ) + z * vector.getX( ) - x * vector.getZ( );
		float z_ = w * vector.getZ( ) + x * vector.getY( ) - y * vector.getX( );

		return new Quaternion( x_, y_, z_, w_ );
	}

	public Quaternion sub( Quaternion quaternion ) {
		return new Quaternion( x - quaternion.getX( ), y - quaternion.getY( ), z - quaternion.getZ( ),
							   w - quaternion.getW( ) );
	}

	public Quaternion add( Quaternion quaternion ) {
		return new Quaternion( x + quaternion.getX( ), y + quaternion.getY( ), z + quaternion.getZ( ),
							   w + quaternion.getW( ) );
	}

	public Matrix4f toRotationMatrix( ) {
		Vector3f forward =
				new Vector3f( 2.0f * ( x * z - w * y ), 2.0f * ( y * z + w * x ), 1.0f - 2.0f * ( x * x + y * y ) );
		Vector3f up =
				new Vector3f( 2.0f * ( x * y + w * z ), 1.0f - 2.0f * ( x * x + z * z ), 2.0f * ( y * z - w * x ) );
		Vector3f right =
				new Vector3f( 1.0f - 2.0f * ( y * y + z * z ), 2.0f * ( x * y - w * z ), 2.0f * ( x * z + w * y ) );

		return new Matrix4f( ).initRotation( forward, up, right );
	}

	public float dot( Quaternion quaternion ) {
		return x * quaternion.getX( ) + y * quaternion.getY( ) + z * quaternion.getZ( ) + w * quaternion.getW( );
	}

	public Quaternion nlerp( Quaternion quaternion, float lerpFactor, boolean shortest ) {
		Quaternion correctQuaternion = quaternion;

		if( shortest && this.dot( quaternion ) < 0 ) {
			correctQuaternion = new Quaternion( -quaternion.getX( ), -quaternion.getY( ), -quaternion.getZ( ),
												-quaternion.getW( ) );
		}

		return correctQuaternion.sub( this ).mul( lerpFactor ).add( this ).normalized( );
	}

	public Quaternion slerp( Quaternion quaternion, float lerpFactor, boolean shortest ) {
		final float EPSILON = 1e3f;

		float cos = this.dot( quaternion );
		Quaternion correctQuaternion = quaternion;

		if( shortest && cos < 0 ) {
			cos = -cos;
			correctQuaternion = new Quaternion( -quaternion.getX( ), -quaternion.getY( ), -quaternion.getZ( ),
												-quaternion.getW( ) );
		}

		if( Math.abs(cos) >= 1 - EPSILON ) {
			return nlerp( correctQuaternion, lerpFactor, false );
		}

		float sin = ( float ) Math.sqrt(1.0f - cos * cos);
		float angle = ( float ) Math.atan2(sin, cos);
		float invSin = 1.0f / sin;

		float srcFactor = ( float ) Math.sin((1.0f - lerpFactor) * angle) * invSin;
		float destFactor = ( float ) Math.sin((lerpFactor) * angle) * invSin;

		return this.mul( srcFactor ).add( correctQuaternion.mul( destFactor ) );
	}

	public Vector3f getForward( ) {
		return new Vector3f( 0, 0, 1 ).rotate( this );
	}

	public Vector3f getBack( ) {
		return new Vector3f( 0, 0, -1 ).rotate( this );
	}

	public Vector3f getUp( ) {
		return new Vector3f( 0, 1, 0 ).rotate( this );
	}

	public Vector3f getDown( ) {
		return new Vector3f( 0, -1, 0 ).rotate( this );
	}

	public Vector3f getRight( ) {
		return new Vector3f( 1, 0, 0 ).rotate( this );
	}

	public Vector3f getLeft( ) {
		return new Vector3f( -1, 0, 0 ).rotate( this );
	}

	public Quaternion set( float x, float y, float z, float w ) {
		this.x = x;
		this.y = y;
		this.z = z;
		this.w = w;
		return this;
	}

	public Quaternion set( Quaternion quaternion ) {
		set( quaternion.getX( ), quaternion.getY( ), quaternion.getZ( ), quaternion.getW( ) );
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

	public float getZ( ) {
		return z;
	}

	public void setZ( float z ) {
		this.z = z;
	}

	public float getW( ) {
		return w;
	}

	public void setW( float w ) {
		this.w = w;
	}

	public boolean equals( Quaternion quaternion ) {
		return x == quaternion.getX( ) && y == quaternion.getY( ) && z == quaternion.getZ( ) && w == quaternion.getW( );
	}
}
