package org.engineFRP.rendering.shaders;

import org.engineFRP.maths.Vector3f;

/**
 * Created by TekMaTek on 28/01/2015.
 */
public class Material {

    public static final Material red = BuildMaterial(new Vector3f(1.0f, 0.0f, 0.0f), 0.5f, 0.2f, 1.0f);
    public static final Material green = BuildMaterial(new Vector3f(0.0f, 1.0f, 0.0f), 0.5f, 0.2f, 1.0f);
    public static final Material blue = BuildMaterial(new Vector3f(0.0f, 0.0f, 1.0f), 0.5f, 0.2f, 1.0f);
    public static final Material white = BuildMaterial(new Vector3f(1.0f, 1.0f, 1.0f), 0.5f, 0.2f, 1.0f);

    public Vector3f color;
    public float specularIntensity;
    public float specularExponent;
    public float balance;//The color balance. Should be 1.0f if not needed.

    private Material() {
    }

    public static Material White() {
        return BuildMaterial(Vector3f.ONE, 0.5f, 0.2f, 1.0f);
    }

    public static Material BuildMaterial(Vector3f color, float specularIntensity, float specularExponent, float balance) {
        Material newMaterial = new Material();
        newMaterial.color = color;
        newMaterial.specularIntensity = specularIntensity;
        newMaterial.specularExponent = specularExponent;
        newMaterial.balance = balance;
        return newMaterial;
    }

    public static Material ColorWithBalance(Vector3f color, float balance) {
        return BuildMaterial(Vector3f.ONE, 0.5f, 0.2f, balance);
    }
}