package org.FRPengine.rendering.lighting;

import org.FRPengine.maths.Vector3f;

/**
 * Created by TekMaTek on 01/02/2015.
 */
public class SpotLight {

	public static SpotLight spotLight =
			SpotLight.BuildSpotLight(
					PointLight.BuildPointLight(
							BaseLight.RedLight( ), Attenuation.DefaultAttenuation( ), new Vector3f( 8.8f, 0.5f, 8.8f
							), 15.0f )
					, new Vector3f( 0.0f, -1.0f, 0.0f ), 0.20f );

	public PointLight pointLight;
	public Vector3f direction;
	public float cutoff;

	private SpotLight( ) {
	}

	public static SpotLight BuildSpotLight( PointLight pointLight, Vector3f direction, float cutoff ) {
		SpotLight newSpotLight = new SpotLight( );
		newSpotLight.pointLight = pointLight;
		newSpotLight.direction = direction.normalized( );
		newSpotLight.cutoff = cutoff;
		return newSpotLight;
	}

	public static SpotLight BlueSpotLight( ) {
		return BuildSpotLight( PointLight.BluePointLight( ), Vector3f.ONE, 2.0f );
	}

	public static SpotLight GreenSpotLight( ) {
		return BuildSpotLight( PointLight.GreenPointLight( ), Vector3f.ONE, 2.0f );
	}

	public static SpotLight RedSpotLight( ) {
		return BuildSpotLight( PointLight.RedPointLight( ), new Vector3f( 0.0f, -1.0f, 0.0f ), 0.20f );
	}
}
