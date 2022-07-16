package app.web.aerialgamestudios.echoengine.events;

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
