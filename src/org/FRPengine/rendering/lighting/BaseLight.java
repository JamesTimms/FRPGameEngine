package org.FRPengine.rendering.lighting;

import org.FRPengine.maths.Vector3f;

/**
 * Created by TekMaTek on 29/01/2015.
 */
public class BaseLight {

	public Vector3f color;
	public float intensity;

	public static BaseLight BuildBaseLight( Vector3f color, float intensity ) {
		BaseLight newBaseLight = new BaseLight( );
		newBaseLight.color = color;
		newBaseLight.intensity = intensity;
		return newBaseLight;
	}

	public static BaseLight WhiteLight( ) {
		return BuildBaseLight( Vector3f.ONE, 1.0f );
	}

	public static BaseLight GreenLight( ) {
		return BuildBaseLight( new Vector3f( 0.0f, 1.0f, 0.0f ), 1.0f );
	}

	public static BaseLight BlueLight( ) {
		return BuildBaseLight( new Vector3f( 0.0f, 0.0f, 1.0f ), 1.0f );
	}

	public static BaseLight RedLight( ) {
		return BuildBaseLight( new Vector3f( 1.0f, 0.0f, 0.0f ), 1.0f );
	}
}
