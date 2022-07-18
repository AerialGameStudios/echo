package app.web.aerialgamestudios.echoplayer;

import app.web.aerialgamestudios.echoengine.components.SpriteRenderer;
import app.web.aerialgamestudios.echoengine.core.Application;
import app.web.aerialgamestudios.echoengine.ecs.Entity;
import app.web.aerialgamestudios.echoengine.ecs.Scene;
import app.web.aerialgamestudios.echoengine.events.Event;
import app.web.aerialgamestudios.echoengine.events.EventManager;
import app.web.aerialgamestudios.echoengine.opengl.Renderer;

public class EchoPlayer extends Application
{
	private Scene mainScene;
	private Entity entity;
	
	private void Init(Event ev)
	{
		this.mainScene = new Scene("InGame");
		this.entity = new Entity("Sprite", this.mainScene);
		this.entity.AddComponent(new SpriteRenderer("examples/image.png"));
		this.entity.enable();
	}
	
	private void Clear(Event ev)
	{
		Renderer.ClearColor(0, 0, 1, 1);
	}
	
	private void OnRender(Event ev)
	{
	}

	@Override
	public void Run()
	{
		EventManager.getManager().addEventHandler("ENGINE_EV_RENDER", this::OnRender);
		EventManager.getManager().addEventHandler("ENGINE_EV_INIT", this::Init);
		EventManager.getManager().addEventHandler("ENGINE_EV_PRE_CLEAR", this::Clear);
	}

	@Override
	public String getName()
	{
		return "EchoPlayer";
	}

}
