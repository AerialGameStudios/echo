package app.web.aerialgamestudios.echoengine.events;

public class WindowResizePayload
{
	public int width, height;
	
	public WindowResizePayload(int width, int height)
	{
		this.width = width;
		this.height = height;
	}
}
