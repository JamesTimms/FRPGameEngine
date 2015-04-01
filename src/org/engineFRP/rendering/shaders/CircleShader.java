package org.engineFRP.rendering.shaders;

import org.engineFRP.core.Transform;
import org.engineFRP.rendering.Vertex;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL20.*;

/**
 * Created by TekMaTek on 28/01/2015.
 */
public class CircleShader extends Shader {

    public CircleShader() {
        super();

        addVertextShader(LoadShader("basic/basicVertex.vertex"));
        addFragmentShader(LoadShader("basic/basicFragment.fragment"));
        CompileShader();

        addUniform("color");
        addUniform("balance");
        addUniform("transform");
    }

    public void draw(Transform transform) {
        Bind();
        updateUniforms(transform, transform.material);

        final int POSITION = 0;
        final int TEXTURE_COORDS = 1;
        final int SIZE_OF_BYTE = 4;
        glEnableVertexAttribArray(POSITION);
        glEnableVertexAttribArray(TEXTURE_COORDS);

        glBindBuffer(GL_ARRAY_BUFFER, transform.mesh.vertexBO);

        glVertexAttribPointer(POSITION, 3, GL_FLOAT, false, Vertex.SIZE * SIZE_OF_BYTE, 0);
        glVertexAttribPointer(TEXTURE_COORDS, 2, GL_FLOAT, false, Vertex.SIZE * 4, SIZE_OF_BYTE * 3);

        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, transform.mesh.indexBO);
        glDrawElements(GL_TRIANGLE_FAN, transform.mesh.indicesLength, GL_UNSIGNED_INT, 0);

        glDisableVertexAttribArray(POSITION);
        glDisableVertexAttribArray(TEXTURE_COORDS);
    }

    public void updateUniforms(Transform transform, Material material) {
        dealWithTexture(transform.mesh.texture);
        setUniform3f("color", material.color);
        setUniformf("balance", material.balance);
        setUniform4m("transform", transform.getTransformMatrix());
    }
}