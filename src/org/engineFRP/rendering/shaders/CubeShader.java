package org.engineFRP.rendering.shaders;

import org.engineFRP.core.Camera;
import org.engineFRP.core.Transform;
import org.engineFRP.rendering.Vertex;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL20.*;

/**
 * Created by TekMaTek on 01/03/2015.
 */
public class CubeShader extends Shader {

    public CubeShader() {
        super();

        addVertextShader(LoadShader("basic3D/complicated.vertex"));
        addFragmentShader(LoadShader("basic3D/complicated.fragment"));
        CompileShader();

        addUniform("transform");
        addUniform("color");
    }

    public void draw(Transform transform) {
        Bind();
        updateUniforms(transform, Material.White());

        final int POSITION = 0;
        final int TEXTURE_COORDS = 1;
        final int NORMALS = 2;
        final int SIZE_OF_BYTE = 4;
        glEnableVertexAttribArray(POSITION);
        glEnableVertexAttribArray(TEXTURE_COORDS);
        glEnableVertexAttribArray(NORMALS);

        glBindBuffer(GL_ARRAY_BUFFER, transform.mesh.vertexBO);
        glVertexAttribPointer(POSITION, 3, GL_FLOAT, false, Vertex.SIZE * 4, 0);
        glVertexAttribPointer(TEXTURE_COORDS, 2, GL_FLOAT, false, Vertex.SIZE * 4, SIZE_OF_BYTE * 3);
        glVertexAttribPointer(NORMALS, 3, GL_FLOAT, false, Vertex.SIZE * 4, SIZE_OF_BYTE * 5);

        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, transform.mesh.indexBO);
        glDrawElements(GL_TRIANGLES, transform.mesh.indicesLength, GL_UNSIGNED_INT, 0);

        glDisableVertexAttribArray(POSITION);
        glDisableVertexAttribArray(TEXTURE_COORDS);
        glDisableVertexAttribArray(NORMALS);
    }

    public void updateUniforms(Transform transform, Material material) {
//        dealWithTexture(material.texture);
        setUniform4m("transform", Camera.mainCamera.GetViewProjection());
        setUniform3f("color", material.color);
    }
}
