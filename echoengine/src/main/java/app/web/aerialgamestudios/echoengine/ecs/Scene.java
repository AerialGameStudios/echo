package app.web.aerialgamestudios.echoengine.ecs;

import java.util.ArrayList;
import java.util.List;

import app.web.aerialgamestudios.echoengine.components.Camera;

public class Scene
{
	private List<Entity> entities;
	private String name;
	private Camera camera;
	
	public Scene(String name)
	{
		this.name = name;
		this.entities = new ArrayList<Entity>();
		this.camera = new Camera(1920, 1080);
	}

	public List<Entity> getEntities()
	{
		return entities;
	}

	public String getName()
	{
		return name;
	}

	public Camera getCamera()
	{
		return camera;
	}
}
