package app.web.aerialgamestudios.echoengine.events;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

public class EventManager
{
	private static EventManager manager;
	private Map<String, List<Consumer<Event>>> eventHandlers;
	
	public static EventManager getManager()
	{
		if(manager == null)
		{
			manager = new EventManager();
		}
		
		return manager;
	}
	
	public EventManager()
	{
		this.eventHandlers = new HashMap<String, List<Consumer<Event>>>();
	}
	
	public void addEventHandler(String type, Consumer<Event> eventHandler)
	{
		if(this.eventHandlers.get(type) != null)
		{
			this.eventHandlers.get(type).add(eventHandler);
		}
		else
		{
			this.eventHandlers.put(type, new ArrayList<Consumer<Event>>());
			this.eventHandlers.get(type).add(eventHandler);
		}
	}
	
	public void removeEventHandler(String type, Consumer<Event> eventHandler)
	{
		if(this.eventHandlers.get(type) != null)
		{
			this.eventHandlers.get(type).remove(eventHandler);
		}
	}
	
	public void dispatchEvent(Event event)
	{
		if(eventHandlers.get(event.type) != null)
		{
			for(Consumer<Event> handler : eventHandlers.get(event.type))
			{
				handler.accept(event);
			}
		}
	}
}
