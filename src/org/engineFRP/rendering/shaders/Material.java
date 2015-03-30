package org.engineFRP.rendering.shaders;


import org.engineFRP.maths.Vector3f;

/**
 * Created by TekMaTek on 28/01/2015.
 */
public class Material {

    public Vector3f color;
    public float specularIntensity;
    public float specularExponent;

    private Material() {
    }

    public static Material White() {
        return BuildMaterial(Vector3f.ONE, 0.5f, 0.2f);
    }

    public static Material BuildMaterial(Vector3f color, float specularIntensity, float specularExponent) {
        Material newMaterial = new Material();
        newMaterial.color = color;
        newMaterial.specularIntensity = specularIntensity;
        newMaterial.specularExponent = specularExponent;
        return newMaterial;
    }
}