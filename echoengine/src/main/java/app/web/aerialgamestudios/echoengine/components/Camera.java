package app.web.aerialgamestudios.echoengine.components;

import org.joml.Matrix4f;
import org.joml.Vector2f;
import org.joml.Vector3f;
import org.joml.Vector4f;
import org.lwjgl.opengl.GL45;

import app.web.aerialgamestudios.echoengine.events.Event;
import app.web.aerialgamestudios.echoengine.events.EventManager;
import app.web.aerialgamestudios.echoengine.events.WindowResizePayload;

public class Camera
{
	private Matrix4f projection, view;
	private float zoom = 10f;
	private Vector2f position = new Vector2f(0f, 0f);
	private float rotation = 0f;
	private Vector4f color;
	
	private float Radians(float angle)
	{
		return angle * (((float)Math.PI)/180f);
	}
	
	private void OnResize(Event ev)
	{
		WindowResizePayload payload = (WindowResizePayload)ev.payload;
		Recalculate(payload.width, payload.height);
	}
	
	private void Clear(Event ev)
	{
		GL45.glClearColor(this.color.x, this.color.y, this.color.z, this.color.w);
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
		RecalculateView();
		this.projection.ortho(left, right, bottom, top, 0, 100);
		this.color = new Vector4f(0.5f, 0.9f, 1f, 1f);
		
		EventManager.getManager().addEventHandler("ENGINE_EV_RESIZE", this::OnResize);
		EventManager.getManager().addEventHandler("ENGINE_EV_PRE_CLEAR", this::Clear);
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
		this.projection.ortho(left, right, bottom, top, 0, 100);
	}
	
	public void RecalculateView()
	{
		this.view = new Matrix4f();
		this.view.identity();
		this.view.translate(new Vector3f(this.position.x, this.position.y, 0));
		this.view.rotate(Radians(this.rotation), new Vector3f(0f, 0f, 1f));
		this.view.scale(this.zoom);
		this.view.invert();
	}

	public Matrix4f getProjection()
	{
		return projection;
	}

	public Matrix4f getView()
	{
		return view;
	}

	public float getZoom()
	{
		return zoom;
	}

	public void setZoom(float zoom)
	{
		this.zoom = zoom;
		RecalculateView();
	}

	public Vector2f getPosition()
	{
		return position;
	}

	public void setPosition(Vector2f position)
	{
		this.position = position;
		RecalculateView();
	}

	public float getRotation()
	{
		return rotation;
	}

	public void setRotation(float rotation)
	{
		this.rotation = rotation;
		RecalculateView();
	}

	public Vector4f getColor()
	{
		return color;
	}

	public void setColor(Vector4f color)
	{
		this.color = color;
	}
}
