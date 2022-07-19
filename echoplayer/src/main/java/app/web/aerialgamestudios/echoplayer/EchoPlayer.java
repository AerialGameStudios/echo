package app.web.aerialgamestudios.echoplayer;

import app.web.aerialgamestudios.echoengine.components.SpriteRenderer;
import app.web.aerialgamestudios.echoengine.core.Application;
import app.web.aerialgamestudios.echoengine.ecs.Entity;
import app.web.aerialgamestudios.echoengine.ecs.Scene;
import app.web.aerialgamestudios.echoengine.events.Event;
import app.web.aerialgamestudios.echoengine.events.EventManager;
import imgui.ImGui;

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
	
	private void OnRender(Event ev)
	{
		ImGui.showDemoWindow();
	}

	@Override
	public void Run()
	{
		EventManager.getManager().addEventHandler("ENGINE_EV_RENDER", this::OnRender);
		EventManager.getManager().addEventHandler("ENGINE_EV_INIT", this::Init);
	}

	@Override
	public String getName()
	{
		return "EchoPlayer";
	}

}
