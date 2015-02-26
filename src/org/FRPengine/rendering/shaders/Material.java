package org.FRPengine.rendering.shaders;


import org.FRPengine.maths.Vector3f;
import org.FRPengine.rendering.Texture;

/**
 * Created by TekMaTek on 28/01/2015.
 */
public class Material {

    public Texture texture;
    public Vector3f color;
    public float specularIntensity;
    public float specularExponent;

    private Material() {
    }
    
    public static Material BuildMaterial
            ( Texture texture, Vector3f color, float specularIntensity, float specularExponent ) {
        Material newMaterial = new Material( );
        newMaterial.texture = texture;
        newMaterial.color = color;
        newMaterial.specularIntensity = specularIntensity;
        newMaterial.specularExponent = specularExponent;
        return newMaterial;
    }

    public static Material WhiteNoTexture( ) {
        return BuildMaterial( Texture.NoTexture( ), Vector3f.ONE, 0.5f, 0.2f );
    }
}