package org.FRPengine.rendering.shaders;

/**
 * Created by TekMaTek on 28/01/2015.
 */
public class BasicShader extends Shader {

	public BasicShader( ) {
		super( );

		addVertextShader( LoadShader( "basic/basicVertex.vertex" ) );
		addFragmentShader( LoadShader( "basic/basicFragment.fragment" ) );
		CompileShader( );
        
//		addUniform( "color" );
	}

	public void updateUniforms( Material material ) {
		dealWithTexture( material );

		setUniform3f( "color", material.color );
	}
}
