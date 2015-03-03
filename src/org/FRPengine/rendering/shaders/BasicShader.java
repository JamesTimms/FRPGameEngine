package org.FRPengine.rendering.shaders;

import org.FRPengine.core.Transform;
import org.FRPengine.rendering.Vertex;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL15.GL_ARRAY_BUFFER;
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

        glEnableVertexAttribArray(0);

        glBindBuffer(GL_ARRAY_BUFFER, transform.mesh.vertexBO);
        glVertexAttribPointer(0, transform.mesh.vertexLength, GL_FLOAT, false, Vertex.SIZE * 4, 0);
        glDrawArrays(GL_TRIANGLES, 0, transform.mesh.indicesLength);

        glDisableVertexAttribArray(0);
    }
}
