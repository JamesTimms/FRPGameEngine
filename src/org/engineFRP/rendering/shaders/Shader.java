package org.engineFRP.rendering.shaders;

import org.engineFRP.core.GameObject;
import org.engineFRP.maths.Vector3f;
import org.engineFRP.rendering.RenderingUtil;
import org.engineFRP.rendering.Texture;
import org.engineFRP.maths.Matrix4f;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.HashMap;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL32.*;

/**
 * Created by TekMaTek on 19/12/2014.
 */

public abstract class Shader {

    private int program;
    private HashMap<String, Integer> uniforms;
    protected int indicesType = GL_TRIANGLES;

    public Shader() {
        this(GL_TRIANGLES);
    }

    public Shader(int indicesType) {
        this.indicesType = indicesType;
        program = glCreateProgram();
        uniforms = new HashMap<>();
        if(program == 0) {
            System.err.println("Shader creation failed: Could not find valid memory location in constructor.");
            System.exit(1);
        }
    }

    public static String LoadShader(String filename) {
        StringBuilder shaderSource = new StringBuilder();
        BufferedReader shaderReader;

        try {
            shaderReader = new BufferedReader(new FileReader("./res/shaders/" + filename));
            String line;
            while((line = shaderReader.readLine()) != null) {
                shaderSource.append(line).append("\n");
            }//concatenate
            shaderReader.close();
        } catch(Exception exception) {
            exception.printStackTrace();
            System.exit(1);
        }

        return shaderSource.toString();
    }

    public abstract void draw(GameObject gameObject);

    public abstract void updateUniforms(GameObject gameObject);

    public void addVertexShader(String text) {
        addProgram(text, GL_VERTEX_SHADER);
    }

    protected void addProgram(String text, int type) {
        int shader = glCreateShader(type);

        if(shader == 0) {
            System.err.println("Shader creation failed: Could not find valid memory location when adding shader.");
            System.exit(1);
        }

        glShaderSource(shader, text);
        glCompileShader(shader);

        if(glGetShaderi(shader, GL_COMPILE_STATUS) == 0) {
            System.err.println(glGetShaderInfoLog(shader, 1024));
            System.exit(1);
        }

        glAttachShader(program, shader);
    }

    public void addGeometryShader(String text) {
        addProgram(text, GL_GEOMETRY_SHADER);

    }

    public void addFragmentShader(String text) {
        addProgram(text, GL_FRAGMENT_SHADER);

    }

    public void CompileShader() {
        glLinkProgram(program);
        if(glGetProgrami(program, GL_LINK_STATUS) == 0) {
            System.err.println(glGetShaderInfoLog(program, 1024));
            System.exit(1);
        }

        glValidateProgram(program);
        if(glGetProgrami(program, GL_VALIDATE_STATUS) == 0) {
            System.err.println(glGetShaderInfoLog(program, 1024));
            System.exit(1);
        }
    }

    public void Bind() {
        glUseProgram(program);
    }

    protected void dealWithTexture(Texture texture) {
        if(texture != null) {
            texture.bind();
        } else {
            RenderingUtil.unbindTextures();
        }
    }

    public void addUniform(String uniform) {
        int uniformLocation = glGetUniformLocation(program, uniform);

//		if( uniformLocation == 0xFFFFFFFF ) {
//			System.err.println( "Error: Could not find uniform: " + uniform );
//			new Exception( ).printStackTrace( );
//			System.exit( 1 );
//		}
        uniforms.put(uniform, uniformLocation);
    }

    public void setUniformi(String uniformName, int value) {
        glUniform1i(uniforms.get(uniformName), value);
    }

    public void setUniformf(String uniformName, float value) {
        glUniform1f(uniforms.get(uniformName), value);
    }

    public void setUniform3f(String uniformName, Vector3f vector) {
        glUniform3f(uniforms.get(uniformName), vector.x, vector.y, vector.z);
    }

    public void setUniform4m(String uniformName, Matrix4f matrix) {
        final boolean ROW_MAJOR_ORDER = true;
        glUniformMatrix4fv(uniforms.get(uniformName), ROW_MAJOR_ORDER, RenderingUtil.createFlippedBuffer(matrix));
    }
}
