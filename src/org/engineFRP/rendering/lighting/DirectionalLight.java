package org.engineFRP.rendering.lighting;

import org.engineFRP.maths.Vector3f;


/**
 * Created by TekMaTek on 29/01/2015.
 */
public class DirectionalLight {

	public static DirectionalLight directionalLight = DirectionalLight.BuildDirectionalLight(
			BaseLight.GreenLight( ), new Vector3f( 0.7f, 0.7f, 0.7f ) );

	public BaseLight baseLight;
	private Vector3f direction;

	public static DirectionalLight BuildDirectionalLight( BaseLight baseLight, Vector3f direction ) {
		DirectionalLight newDirLight = new DirectionalLight( );
		newDirLight.baseLight = baseLight;
		newDirLight.direction = direction.normalized( );
		return newDirLight;
	}

	public static DirectionalLight Default( ) {
		return BuildDirectionalLight( BaseLight.WhiteLight( ), new Vector3f( 0.35f, 0.35f, 0.35f ) );
	}

	public Vector3f getDirection( ) {
		return direction;
	}

	public void setDirection( Vector3f direction ) {
		this.direction = direction.normalized( );
	}
}
