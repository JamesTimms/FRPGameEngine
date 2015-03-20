package org.engineFRP.maths;

/**
 * Based on BennyBox's GameEngine https://github.com/BennyQBD/3DGameEngine.
 * Created by TekMaTek on 05/08/2014.
 */
public class Quaternion {

	public float x;
    public float y;
    public float z;
    public float w;

	public Quaternion( float x, float y, float z, float w ) {
		this.x = x;
		this.y = y;
		this.z = z;
		this.w = w;
	}

	public Quaternion( Vector3f axis, float angle ) {
		float sinHalfAngle = ( float ) Math.sin(angle / 2);
		float cosHalfAngle = ( float ) Math.cos(angle / 2);

		this.x = axis.x * sinHalfAngle;
		this.y = axis.y * sinHalfAngle;
		this.z = axis.z * sinHalfAngle;
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
		float w_ = w * quaternion.w - x * quaternion.x - y * quaternion.y - z * quaternion.z;
		float x_ = x * quaternion.w + w * quaternion.x + y * quaternion.z - z * quaternion.y;
		float y_ = y * quaternion.w + w * quaternion.y + z * quaternion.x - x * quaternion.z;
		float z_ = z * quaternion.w + w * quaternion.z + x * quaternion.y - y * quaternion.x;

		return new Quaternion( x_, y_, z_, w_ );
	}

	public Quaternion mul( Vector3f vector ) {
		float w_ = -x * vector.x - y * vector.y - z * vector.z;
		float x_ = w * vector.x + y * vector.z - z * vector.y;
		float y_ = w * vector.y + z * vector.x - x * vector.z;
		float z_ = w * vector.z + x * vector.y - y * vector.x;

		return new Quaternion( x_, y_, z_, w_ );
	}

	public Quaternion sub( Quaternion quaternion ) {
		return new Quaternion( x - quaternion.x, y - quaternion.y, z - quaternion.z,
							   w - quaternion.w );
	}

	public Quaternion add( Quaternion quaternion ) {
		return new Quaternion( x + quaternion.x, y + quaternion.y, z + quaternion.z,
							   w + quaternion.w );
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
		return x * quaternion.x + y * quaternion.y + z * quaternion.z + w * quaternion.w;
	}

	public Quaternion nlerp( Quaternion quaternion, float lerpFactor, boolean shortest ) {
		Quaternion correctQuaternion = quaternion;

		if( shortest && this.dot( quaternion ) < 0 ) {
			correctQuaternion = new Quaternion( -quaternion.x, -quaternion.y, -quaternion.z,
												-quaternion.w );
		}

		return correctQuaternion.sub( this ).mul( lerpFactor ).add( this ).normalized( );
	}

	public Quaternion slerp( Quaternion quaternion, float lerpFactor, boolean shortest ) {
		final float EPSILON = 1e3f;

		float cos = this.dot( quaternion );
		Quaternion correctQuaternion = quaternion;

		if( shortest && cos < 0 ) {
			cos = -cos;
			correctQuaternion = new Quaternion( -quaternion.x, -quaternion.y, -quaternion.z,
												-quaternion.w );
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
		set( quaternion.x, quaternion.y, quaternion.z, quaternion.w );
		return this;
	}

	public boolean equals( Quaternion quaternion ) {
		return x == quaternion.x && y == quaternion.y && z == quaternion.z && w == quaternion.w;
	}
}
