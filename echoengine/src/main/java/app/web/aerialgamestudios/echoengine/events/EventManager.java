package app.web.aerialgamestudios.echoengine.events;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

public class EventManager
{
	private static EventManager manager;
	private boolean inUse = false;
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
	
	public void removeEventHandler(Consumer<Event> eventHandler)
	{
		inUse = true;
		for(String i : this.eventHandlers.keySet())
		{
			if(this.eventHandlers.get(i).contains(eventHandler))
			{
				this.eventHandlers.get(i).remove(eventHandler);
				break;
			}
		}
		inUse = false;
	}
	
	public void dispatchEvent(Event event)
	{
		if(eventHandlers.get(event.type) != null)
		{
			inUse = true;
			ArrayList<Consumer<Event>> handlers = (ArrayList<Consumer<Event>>) ((ArrayList<Consumer<Event>>) eventHandlers.get(event.type)).clone();
			for(Consumer<Event> handler : handlers)
			{
				handler.accept(event);
			}
			inUse = false;
		}
	}
}
