package app.web.aerialgamestudios.echoengine.opengl;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL45;

public class IndexBuffer
{
	private int[] indices;
	private int id;
	
	public IndexBuffer(int[] indices)
	{
		this.indices = indices;
		this.id = GL45.glGenBuffers();
		GL45.glBindBuffer(GL45.GL_ELEMENT_ARRAY_BUFFER, this.id);
		GL45.glBufferData(GL45.GL_ELEMENT_ARRAY_BUFFER, BufferUtils.createIntBuffer(this.indices.length).put(this.indices).flip(), GL45.GL_STATIC_DRAW);
	}
	
	public void Bind()
	{
		GL45.glBindBuffer(GL45.GL_ELEMENT_ARRAY_BUFFER, this.id);
	}
	
	 public void Unbind()
	 {
		 GL45.glBindBuffer(GL45.GL_ELEMENT_ARRAY_BUFFER, 0); 
	 }
}
