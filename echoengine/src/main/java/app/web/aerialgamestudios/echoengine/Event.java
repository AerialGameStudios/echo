package app.web.aerialgamestudios.echoengine;

public class Event
{
	public Object payload;
	public String type;
	
	public Event(Object payload, String type)
	{
		this.payload = payload;
		this.type = type;
	}
}
