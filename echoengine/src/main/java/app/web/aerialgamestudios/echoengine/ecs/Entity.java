package app.web.aerialgamestudios.echoengine.ecs;

import java.util.ArrayList;
import java.util.List;

import app.web.aerialgamestudios.echoengine.components.Transform;

public class Entity
{
	private Entity parent;
	private Scene scene;
	private List<Entity> children;
	private List<Component> components;
	private Transform transform;
	private String name;
	private boolean enabled;
	
	public Entity(String name, Scene scene)
	{
		this.name = name;
		this.scene = scene;
		this.children = new ArrayList<Entity>();
		this.components = new ArrayList<Component>();
		this.transform = new Transform();
		this.enabled = false;
	}
	
	public void AddComponent(Component c)
	{
		this.components.add(c);
		c.setParent(this);
	}
	
	public void RemoveComponent(Component c)
	{
		this.components.remove(c);
		c.setParent(null);
	}
	
	public List<Component> GetComponents(String name)
	{
		List<Component> list = new ArrayList<Component>();
		for(Component i : this.components)
		{
			if(i.getName().equalsIgnoreCase(name))
			{
				list.add(i);
			}
		}
		
		return list;
	}
	
	public void enable()
	{
		this.enabled = true;
		for(Component i : this.components)
		{
			i.enable();
		}
		
		for(Entity j : this.children)
		{
			j.enable();
		}
	}
	
	public void disable()
	{
		this.enabled = false;
		for(Component i : this.components)
		{
			i.disable();
		}
		
		for(Entity j : this.children)
		{
			j.disable();
		}
	}
	
	public boolean enabled()
	{
		return this.enabled;
	}

	public Entity getParent()
	{
		return parent;
	}

	public Transform getTransform()
	{
		return transform;
	}

	public String getName()
	{
		return name;
	}
	
	public Scene getScene()
	{
		return this.scene;
	}
}
