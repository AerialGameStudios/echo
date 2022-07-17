package app.web.aerialgamestudios.echoengine.opengl;

import org.lwjgl.opengl.GL45;

public class OpenGL
{
	public static void CheckError()
	{
		int error = GL45.glGetError();
		if(error != GL45.GL_NO_ERROR);
		{
			switch(error)
			{
				case GL45.GL_INVALID_OPERATION:
					System.err.println(error+": Invalid Operation");
					break;
	            case GL45.GL_INVALID_ENUM:
	            	System.err.println(error+": Invalid Enum");
	            	break;
	            case GL45.GL_INVALID_VALUE:
	            	System.err.println(error+": Invalid Value");
	            	break;
	            case GL45.GL_OUT_OF_MEMORY:
	            	System.err.println(error+": Out of Memory");
	            	break;
	            case GL45.GL_INVALID_FRAMEBUFFER_OPERATION:
	            	System.err.println(error+": Invalid Framebuffer Operation");
	            	break;
			}
		}
	}
}
