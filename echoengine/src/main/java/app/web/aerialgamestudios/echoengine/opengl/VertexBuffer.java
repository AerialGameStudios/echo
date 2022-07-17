package app.web.aerialgamestudios.echoengine.opengl;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL45;

public class VertexBuffer
{
	private float[] vertices;
	private int id;
	
	public VertexBuffer(float[] vertices)
	{
		this.vertices = vertices;
		this.id = GL45.glGenBuffers();
		GL45.glBindBuffer(GL45.GL_ARRAY_BUFFER, this.id);
		GL45.glBufferData(GL45.GL_ARRAY_BUFFER, BufferUtils.createFloatBuffer(this.vertices.length).put(this.vertices).flip(), GL45.GL_STATIC_DRAW);
	}
	
	public void Bind()
	{
		GL45.glBindBuffer(GL45.GL_ARRAY_BUFFER, this.id);
	}
	
	 public void Unbind()
	 {
		 GL45.glBindBuffer(GL45.GL_ARRAY_BUFFER, 0); 
	 }
}
