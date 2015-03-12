package org.engineFRP.rendering.shaders;

import org.engineFRP.core.Transform;
import org.engineFRP.rendering.Vertex;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL15.GL_ARRAY_BUFFER;
import static org.lwjgl.opengl.GL15.GL_ELEMENT_ARRAY_BUFFER;
import static org.lwjgl.opengl.GL15.glBindBuffer;
import static org.lwjgl.opengl.GL20.*;

/**
 * Created by TekMaTek on 28/01/2015.
 */
public class BasicShader extends Shader {

    public BasicShader() {
        super();

        addVertextShader(LoadShader("basic/basicVertex.vertex"));
        addFragmentShader(LoadShader("basic/basicFragment.fragment"));
        CompileShader();

        addUniform("color");
        addUniform("transform");
    }

    public void updateUniforms(Transform transform, Material material) {
        dealWithTexture(material);
        setUniform3f("color", material.color);
        setUniform4m("transform", transform.getTransformMatrix());
    }

    public void draw(Transform transform) {
        Bind();
        updateUniforms(transform, Material.WhiteNoTexture());

        final int POSITION = 0;
        final int SIZE_OF_BYTE = 4;
        glEnableVertexAttribArray(POSITION);

        glBindBuffer(GL_ARRAY_BUFFER, transform.mesh.vertexBO);
        glVertexAttribPointer(POSITION, 3, GL_FLOAT, false, Vertex.SIZE * SIZE_OF_BYTE, 0);
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, transform.mesh.indexBO);
        glDrawElements(GL_TRIANGLES, transform.mesh.indicesLength, GL_UNSIGNED_INT, 0);

        glDisableVertexAttribArray(0);
    }
}
