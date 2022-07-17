package app.web.aerialgamestudios.echoengine.opengl;

import org.lwjgl.opengl.GL45;

public class Renderer
{
	public static void ClearColor(float r, float g, float b, float a)
	{
		GL45.glClearColor(r, g, b, a);
	}
	
	public static void Draw(int[] indices)
	{
		GL45.glDrawElements(GL45.GL_TRIANGLES, indices.length, GL45.GL_UNSIGNED_INT, 0);
	}
}
