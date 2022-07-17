package app.web.aerialgamestudios.echoengine.components;

import org.joml.Matrix4f;
import org.joml.Vector2f;

import app.web.aerialgamestudios.echoengine.Window;
import app.web.aerialgamestudios.echoengine.events.Event;
import app.web.aerialgamestudios.echoengine.events.EventManager;
import app.web.aerialgamestudios.echoengine.events.WindowResizePayload;

public class Camera
{
	private Matrix4f projection, view;
	private float zoom = 1f;
	private Vector2f position = new Vector2f(0f, 0f);
	private float rotation = 0f;
	
	private void OnResize(Event ev)
	{
		WindowResizePayload payload = (WindowResizePayload)ev.payload;
		Recalculate(payload.width, payload.height);
	}
	
	public Camera(int width, int height)
	{
		float aspectRatio = ((float)width)/((float)height);
		float left, right, bottom, top;
		left = -aspectRatio;
		right = aspectRatio;
		bottom = -1;
		top = 1;
		
		this.projection = new Matrix4f();
		this.view = new Matrix4f();
		
		this.projection.identity();
		this.view.identity();
		this.projection.ortho(left, right, bottom, top, 0, 100).scale(this.zoom);
		
		EventManager.getManager().addEventHandler("ENGINE_EV_RESIZE", this::OnResize);
	}
	
	public void Recalculate(int width, int height)
	{
		float aspectRatio = ((float)width)/((float)height);
		float left, right, bottom, top;
		left = -aspectRatio;
		right = aspectRatio;
		bottom = -1;
		top = 1;
		
		this.projection = new Matrix4f();
		
		this.projection.identity();
		this.projection.ortho(left, right, bottom, top, 0, 100).scale(this.zoom);
	}

	public Matrix4f getProjection()
	{
		return projection;
	}

	public Matrix4f getView()
	{
		return view;
	}
}
