package app.web.aerialgamestudios.echoengine.opengl;

import java.nio.ByteBuffer;
import java.nio.IntBuffer;

import org.lwjgl.opengl.GL45;
import org.lwjgl.stb.STBImage;
import org.lwjgl.system.MemoryUtil;

public class Texture
{
	private int id;
	private ByteBuffer image;
	
	public Texture(String path)
	{
		this.id = GL45.glGenTextures(); // SAFE
		GL45.glBindTexture(GL45.GL_TEXTURE_2D, this.id); // SAFE
		
		GL45.glTexParameteri(GL45.GL_TEXTURE_2D, GL45.GL_TEXTURE_WRAP_S, GL45.GL_REPEAT);	// set texture wrapping to GL_REPEAT (default wrapping method)
		GL45.glTexParameteri(GL45.GL_TEXTURE_2D, GL45.GL_TEXTURE_WRAP_T, GL45.GL_REPEAT);
	    // set texture filtering parameters
	    GL45.glTexParameteri(GL45.GL_TEXTURE_2D, GL45.GL_TEXTURE_MIN_FILTER, GL45.GL_LINEAR);
	    GL45.glTexParameteri(GL45.GL_TEXTURE_2D, GL45.GL_TEXTURE_MAG_FILTER, GL45.GL_LINEAR);
	    
	    STBImage.stbi_set_flip_vertically_on_load(true);
	    
	    IntBuffer widthBuf = MemoryUtil.memAllocInt(1);
	    IntBuffer heightBuf = MemoryUtil.memAllocInt(1);
	    IntBuffer channelsBuf = MemoryUtil.memAllocInt(1);
	    
	    this.image = STBImage.stbi_load(path, widthBuf, heightBuf, channelsBuf, 0);
	    
	    if(this.image != null)
	    {
	    	if(channelsBuf.get() == 3)
	    	{
	    		GL45.glTexImage2D(GL45.GL_TEXTURE_2D, 0, GL45.GL_RGB, widthBuf.get(), heightBuf.get(), 0, GL45.GL_RGB, GL45.GL_UNSIGNED_BYTE, this.image); // SAFE
	    	}
	    	else
	    	{
	    		GL45.glTexImage2D(GL45.GL_TEXTURE_2D, 0, GL45.GL_RGBA, widthBuf.get(), heightBuf.get(), 0, GL45.GL_RGBA, GL45.GL_UNSIGNED_BYTE, this.image); // SAFE
	    	}
	    	GL45.glGenerateMipmap(GL45.GL_TEXTURE_2D);
	    	STBImage.stbi_image_free(this.image);
	    	MemoryUtil.memFree(widthBuf);
	    	MemoryUtil.memFree(heightBuf);
	    	MemoryUtil.memFree(channelsBuf);
	    }
	    else
	    {
	    	System.err.println("Image could not load.");
	    }
	    
	    Bind();
	}
	
	public void Bind()
	{
		GL45.glBindTexture(GL45.GL_TEXTURE_2D, this.id);
	}
	
	public void Unbind()
	{
		GL45.glBindTexture(GL45.GL_TEXTURE_2D, 0);
	}
	
	public void EnableTextureSlot(int slot)
	{
		GL45.glEnable(GL45.GL_TEXTURE0+slot);
		GL45.glActiveTexture(GL45.GL_TEXTURE0+slot);
	}
	
	public int getID()
	{
		return this.id;
	}
}
