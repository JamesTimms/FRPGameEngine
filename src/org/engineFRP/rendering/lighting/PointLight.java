package org.engineFRP.rendering.lighting;

import org.engineFRP.maths.Vector3f;

/**
 * Created by TekMaTek on 29/01/2015.
 */
public class PointLight {

	public static PointLight pointLight = PointLight.BluePointLight( );

	public BaseLight baseLight;
	public Attenuation atten;
	public Vector3f position;
	public float range;

	private PointLight( ) {
	}

	public static PointLight BuildPointLight( BaseLight baseLight, Attenuation atten, Vector3f position, float range ) {
		PointLight newPointLight = new PointLight( );
		newPointLight.baseLight = baseLight;
		newPointLight.atten = atten;
		newPointLight.position = position;
		newPointLight.range = range;
		return newPointLight;
	}

	public static PointLight BluePointLight( ) {
		return BuildPointLight(
				BaseLight.BlueLight( ), Attenuation.DefaultAttenuation( ), new Vector3f( 0.5f, 0.5f, 0.5f ), 15.0f );
	}

	public static PointLight GreenPointLight( ) {
		return BuildPointLight(
				BaseLight.GreenLight( ), Attenuation.DefaultAttenuation( ), new Vector3f( 0.5f, 0.5f, 0.5f ), 15.0f );
	}

	public static PointLight RedPointLight( ) {
		return BuildPointLight(
				BaseLight.RedLight( ), Attenuation.DefaultAttenuation( ), new Vector3f( 0.5f, 0.5f, 0.5f ), 15.0f );
	}
}
