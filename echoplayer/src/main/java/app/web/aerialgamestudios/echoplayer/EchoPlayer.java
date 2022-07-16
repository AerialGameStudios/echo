package app.web.aerialgamestudios.echoplayer;

import app.web.aerialgamestudios.echoengine.core.Application;
import app.web.aerialgamestudios.echoengine.events.Event;
import app.web.aerialgamestudios.echoengine.events.EventManager;
import app.web.aerialgamestudios.echoengine.events.KeyboardEventPayload;

public class EchoPlayer extends Application
{
	private void KeyCallback(Event ev)
	{
		KeyboardEventPayload payload = (KeyboardEventPayload)ev.payload;
		
		if(payload.inputAction == KeyboardEventPayload.PRESS)
		{
			System.out.println("Test");
		}
	}

	@Override
	public void Run()
	{
		EventManager.getManager().addEventHandler("ENGINE_EV_KEY_INPUT", this::KeyCallback);
	}

	@Override
	public String getName()
	{
		return "EchoPlayer";
	}

}
