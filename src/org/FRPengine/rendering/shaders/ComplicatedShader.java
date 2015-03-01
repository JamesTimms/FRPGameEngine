package org.FRPengine.rendering.shaders;

import org.FRPengine.core.Camera;
import org.FRPengine.rendering.Mesh;
import org.FRPengine.rendering.Vertex;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL20.*;

/**
 * Created by TekMaTek on 01/03/2015.
 */
public class ComplicatedShader extends Shader {

    public ComplicatedShader() {
        super();

        addVertextShader(LoadShader("basic3D/complicated.vertex"));
        addFragmentShader(LoadShader("basic3D/complicated.fragment"));
        CompileShader();

        addUniform("transform");
        addUniform("color");
    }

    public void updateUniforms(Material material) {
        dealWithTexture(material);
        setUniform4m("transform", Camera.mainCamera.GetViewProjection());
        setUniform3f("color", material.color);
    }

    public void draw(Mesh mesh) {
        Bind();
        updateUniforms(Material.WhiteNoTexture());

        final int POSITION = 0;
        final int TEXTURE_COORDS = 1;
        final int NORMALS = 2;
        final int SIZE_OF_BYTE = 4;
        glEnableVertexAttribArray(POSITION);
        glEnableVertexAttribArray(TEXTURE_COORDS);
        glEnableVertexAttribArray(NORMALS);

        glBindBuffer(GL_ARRAY_BUFFER, mesh.vertexBO);
        glVertexAttribPointer(POSITION, 3, GL_FLOAT, false, Vertex.SIZE * 4, 0);
        glVertexAttribPointer(TEXTURE_COORDS, 2, GL_FLOAT, false, Vertex.SIZE * 4, SIZE_OF_BYTE * 3);
        glVertexAttribPointer(NORMALS, 3, GL_FLOAT, false, Vertex.SIZE * 4, SIZE_OF_BYTE * 5);

        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, mesh.indexBO);
        glDrawElements(GL_TRIANGLES, mesh.indicesLength, GL_UNSIGNED_INT, 0);

        glDisableVertexAttribArray(POSITION);
        glDisableVertexAttribArray(TEXTURE_COORDS);
        glDisableVertexAttribArray(NORMALS);
    }
}
