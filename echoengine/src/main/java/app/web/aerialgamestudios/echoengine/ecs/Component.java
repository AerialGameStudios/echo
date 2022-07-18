package app.web.aerialgamestudios.echoengine.ecs;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

import app.web.aerialgamestudios.echoengine.events.Event;
import app.web.aerialgamestudios.echoengine.events.EventManager;

public abstract class Component
{
	private List<Consumer<Event>> eventHandlers = new ArrayList<Consumer<Event>>();
	private boolean enabled = false;
	private Entity parent;
	
	public abstract void Initialize();
	public abstract void Deinitialize();
	public abstract List<String> getProperties();
	public abstract String getName();
	public abstract Object getProperty(String name);
	public void addEventHandler(String type, Consumer<Event> handler)
	{
		this.eventHandlers.add(handler);
		EventManager.getManager().addEventHandler(type, handler);
	}
	
	public void enable()
	{
		this.enabled = true;
		Initialize();
	}
	
	public void disable()
	{
		this.enabled = false;
		for(Consumer<Event> i : eventHandlers)
		{
			EventManager.getManager().removeEventHandler(i);
		}
		Deinitialize();
	}
	
	public boolean enabled()
	{
		return this.enabled;
	}
	
	public void setParent(Entity entity)
	{
		this.parent = entity;
	}
	
	public Entity getParent()
	{
		return this.parent;
	}
}
