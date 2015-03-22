package org.engineFRP.core;

import org.engineFRP.rendering.shaders.Shader;
import org.engineFRP.rendering.shaders.SquareShader;

/**
 * Created by TekMaTek on 22/03/2015.
 */
public class SceneGraph {

    private static Shader shader2;
    public final Transform[] sceneTransforms;

    public SceneGraph(Transform[] scene) {
        shader2 = new SquareShader();
        this.sceneTransforms = scene;
    }

    public void drawScene() {
        for(Transform transform : sceneTransforms) {
            shader2.draw(transform);
        }
    }
}
