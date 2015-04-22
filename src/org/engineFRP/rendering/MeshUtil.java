package org.engineFRP.rendering;

import org.engineFRP.rendering.Manafolds.Circle;
import org.engineFRP.rendering.Manafolds.Rect;
import org.engineFRP.rendering.shaders.CircleShader;
import org.engineFRP.rendering.shaders.SquareShader;
import org.engineFRP.rendering.Manafolds.Triangle;


/**
 * Created by TekMaTek on 01/02/2015.
 */
public class MeshUtil {

    public static Mesh BuildTriangle() {
        return new Mesh(new Triangle());
    }

    public static Mesh BuildSquare() {
        return new Mesh(new Rect());
    }

    public static Mesh BuildSquareWithTexture(String filename) {
        return new Mesh(new Rect(), Texture.loadTexture(filename), new SquareShader());
    }

    public static Mesh BuildSquareWithTexture(String filename, float size) {
        return new Mesh(new Rect(size), Texture.loadTexture(filename), new SquareShader());
    }

    public static Mesh BuildRectWithTexture(String filename, float width, float height) {
        return new Mesh(new Rect(width, height), Texture.loadTexture(filename), new SquareShader());
    }

    public static Mesh BuildRect(float width, float height) {
        return new Mesh(new Rect(width, height), Texture.BlankTexture(), new SquareShader());
    }

    public static Mesh BuildCircleWithTexture(String filename, float radius) {
        return new Mesh(new Circle(radius), Texture.loadTexture(filename), new CircleShader());
    }

    public static Mesh BuildCircle(float radius) {
        return new Mesh(new Circle(radius), Texture.BlankTexture(), new CircleShader());
    }

    public static Mesh scaledSquare(String filename, float size) {
        return new Mesh(new Rect(size), Texture.loadTexture(filename), new SquareShader());
    }
}
