package app.web.aerialgamestudios.echoengine.opengl;

import org.lwjgl.opengl.GL45;
import org.lwjgl.system.MemoryUtil;

import app.web.aerialgamestudios.echoengine.Window;

public class Framebuffer
{
	private int id;
	private int texture_id;
	private int render_buffer;
	
	public Framebuffer()
	{
		this.id = GL45.glGenFramebuffers();
		Bind();
		if(GL45.glCheckFramebufferStatus(GL45.GL_FRAMEBUFFER) != GL45.GL_FRAMEBUFFER_COMPLETE)
		{
			System.err.println("Framebuffer is not complete!");
		}
		
		this.texture_id = GL45.glGenTextures();
		BindTexture();
		
		GL45.glTexImage2D(GL45.GL_TEXTURE_2D, 0, GL45.GL_RGB, Window.instance.getWidth(), Window.instance.getHeight(), 0, GL45.GL_RGB, GL45.GL_UNSIGNED_BYTE, MemoryUtil.NULL);

		GL45.glTexParameteri(GL45.GL_TEXTURE_2D, GL45.GL_TEXTURE_MIN_FILTER, GL45.GL_LINEAR);
		GL45.glTexParameteri(GL45.GL_TEXTURE_2D, GL45.GL_TEXTURE_MAG_FILTER, GL45.GL_LINEAR);
		
		GL45.glFramebufferTexture2D(GL45.GL_FRAMEBUFFER, GL45.GL_COLOR_ATTACHMENT0, GL45.GL_TEXTURE_2D, this.texture_id, 0);
		UnbindTexture();
		
		this.render_buffer = GL45.glGenRenderbuffers();
		BindRenderbuffers();
		GL45.glRenderbufferStorage(GL45.GL_RENDERBUFFER, GL45.GL_DEPTH24_STENCIL8, Window.instance.getWidth(), Window.instance.getHeight());
		GL45.glFramebufferRenderbuffer(GL45.GL_FRAMEBUFFER, GL45.GL_DEPTH_STENCIL_ATTACHMENT, GL45.GL_RENDERBUFFER, this.render_buffer);
		UnbindRenderbuffers();
		Unbind();
	}
	
	public void Bind()
	{
		GL45.glBindFramebuffer(GL45.GL_FRAMEBUFFER, this.id);	
	}
	
	public void Unbind()
	{
		GL45.glBindFramebuffer(GL45.GL_FRAMEBUFFER, 0);
	}
	
	public void BindTexture()
	{
		GL45.glBindTexture(GL45.GL_TEXTURE_2D, this.texture_id);
	}
	
	public void UnbindTexture()
	{
		GL45.glBindTexture(GL45.GL_TEXTURE_2D, 0);
	}
	
	public void BindRenderbuffers()
	{
		GL45.glBindRenderbuffer(GL45.GL_RENDERBUFFER, this.render_buffer);
	}
	
	public void UnbindRenderbuffers()
	{
		GL45.glBindRenderbuffer(GL45.GL_RENDERBUFFER, 0);
	}

	public int getId()
	{
		return id;
	}

	public int getTextureId()
	{
		return texture_id;
	}

	public int getRenderBuffer()
	{
		return render_buffer;
	}
}
