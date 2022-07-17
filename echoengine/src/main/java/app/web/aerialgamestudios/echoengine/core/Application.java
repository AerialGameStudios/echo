package app.web.aerialgamestudios.echoengine.core;

import app.web.aerialgamestudios.echoengine.Window;

public abstract class Application
{
	private Window window;
	
	public abstract void Run();
	public abstract String getName();
	
	public Window getWindow()
	{
		return this.window;
	}
	
	public void Main()
	{
		Run();
		this.window = new Window(getName(), 1920, 1080);
	}
}
