package app.web.aerialgamestudios.echoengine.components;

import org.joml.Matrix4f;
import org.joml.Vector2f;
import org.joml.Vector3f;

public class Transform
{
	private Vector2f scale, position;
	private float rotation;
	private Matrix4f model;
	
	private float Radians(float angle)
	{
		return angle * (((float)Math.PI)/180f);
	}
	
	public Transform()
	{
		this.scale = new Vector2f(1f, 1f);
		this.position = new Vector2f(0f, 0f);
		this.rotation = 0f;
		Recalculate();
	}
	
	public void Recalculate()
	{
		this.model = new Matrix4f();
		this.model.identity();
		this.model.translate(new Vector3f(this.position.x, this.position.y, 0f));
		this.model.rotate(Radians(this.rotation), new Vector3f(0f, 0f, 1f));
		this.model.scale(new Vector3f(this.scale.x, this.scale.y, 0f));
	}

	public Vector2f getScale()
	{
		return scale;
	}

	public void setScale(Vector2f scale)
	{
		this.scale = scale;
		Recalculate();
	}

	public Vector2f getPosition()
	{
		return position;
	}

	public void setPosition(Vector2f position)
	{
		this.position = position;
		Recalculate();
	}

	public float getRotation()
	{
		return rotation;
	}

	public void setRotation(float rotation)
	{
		this.rotation = rotation;
		Recalculate();
	}

	public Matrix4f getModel()
	{
		return model;
	}
}
