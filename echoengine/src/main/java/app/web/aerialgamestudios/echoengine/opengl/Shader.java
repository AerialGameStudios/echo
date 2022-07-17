package app.web.aerialgamestudios.echoengine.opengl;

import java.nio.FloatBuffer;

import org.joml.Matrix4f;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL45;

public class Shader
{
	private int id;

	public Shader(String vertex, String fragment)
	{
		int vertexId = GL45.glCreateShader(GL45.GL_VERTEX_SHADER);
		int fragmentId = GL45.glCreateShader(GL45.GL_FRAGMENT_SHADER);
		int[] status = {0};
		String infoLog = "";
		
		GL45.glShaderSource(vertexId, vertex);
		GL45.glShaderSource(fragmentId, fragment);
		
		GL45.glCompileShader(vertexId);
		GL45.glGetShaderiv(vertexId, GL45.GL_COMPILE_STATUS, status);
		
		if(status[0] == 0)
		{
			infoLog = GL45.glGetShaderInfoLog(vertexId);
			System.err.println("GL Shader Compile Error (Vertex): ");
			System.err.println(infoLog);
		}
		
		GL45.glCompileShader(fragmentId);
		GL45.glGetShaderiv(fragmentId, GL45.GL_COMPILE_STATUS, status);
		
		if(status[0] == 0)
		{
			infoLog = GL45.glGetShaderInfoLog(fragmentId);
			System.err.println("GL Shader Compile Error (Fragment): ");
			System.err.println(infoLog);
		}
		
		this.id = GL45.glCreateProgram();
		GL45.glAttachShader(this.id, vertexId);
		GL45.glAttachShader(this.id, fragmentId);
		GL45.glLinkProgram(this.id);
		
		GL45.glGetShaderiv(this.id, GL45.GL_LINK_STATUS, status);
		
		if(status[0] == 0)
		{
			infoLog = GL45.glGetShaderInfoLog(this.id);
			System.err.println("GL Shader Program Link Error: ");
			System.err.println(infoLog);
		}
		
		GL45.glDeleteShader(vertexId);
		GL45.glDeleteShader(fragmentId);
	}
	
	public void Bind()
	{
		GL45.glUseProgram(this.id);
	}
	
	public void Unbind()
	{
		GL45.glUseProgram(0);
	}
	
	// Uniform Functions
	public void setUniformInt(String name, int value)
	{
		GL45.glUniform1i(GL45.glGetUniformLocation(this.id, name), value);
	}
	
	public void setUniformMat4(String name, Matrix4f mat)
	{
		FloatBuffer buf = BufferUtils.createFloatBuffer(16);
		mat.get(buf);
		GL45.glUniform4fv(GL45.glGetUniformLocation(this.id, name), buf);
	}
}
