package app.web.aerialgamestudios.echoengine.opengl;

import org.lwjgl.opengl.GL45;

public class VertexArray
{
    public static final int // From lwjgl3 (BSD-3-Clause)
	    BYTE           = 0x1400,
	    UNSIGNED_BYTE  = 0x1401,
	    SHORT          = 0x1402,
	    UNSIGNED_SHORT = 0x1403,
	    INT            = 0x1404,
	    UNSIGNED_INT   = 0x1405,
	    FLOAT          = 0x1406,
	    D2_BYTES        = 0x1407,
	    D3_BYTES        = 0x1408,
	    D4_BYTES        = 0x1409,
	    DOUBLE         = 0x140A;
    
	private int id;
	private int currentAttrib = 0;
	
	public VertexArray()
	{
		this.id = GL45.glGenVertexArrays();
	}
	
	public void AddAttrib(int num, int stride, int dataType, int offset)
	{
		GL45.glVertexAttribPointer(this.currentAttrib, num, dataType, false, stride, offset);
		GL45.glEnableVertexAttribArray(this.currentAttrib); 
		this.currentAttrib++;
	}
	
	public void Bind()
	{
		GL45.glBindVertexArray(this.id);
	}
	
	public void Unbind()
	{
		GL45.glBindVertexArray(0);
	}
}
