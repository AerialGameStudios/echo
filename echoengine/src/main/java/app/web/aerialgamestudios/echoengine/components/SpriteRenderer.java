package app.web.aerialgamestudios.echoengine.components;

import java.util.ArrayList;
import java.util.List;

import app.web.aerialgamestudios.echoengine.ecs.Component;
import app.web.aerialgamestudios.echoengine.events.Event;
import app.web.aerialgamestudios.echoengine.opengl.IndexBuffer;
import app.web.aerialgamestudios.echoengine.opengl.Renderer;
import app.web.aerialgamestudios.echoengine.opengl.Shader;
import app.web.aerialgamestudios.echoengine.opengl.Texture;
import app.web.aerialgamestudios.echoengine.opengl.VertexArray;
import app.web.aerialgamestudios.echoengine.opengl.VertexBuffer;

public class SpriteRenderer extends Component
{
	private VertexArray vao;
	private VertexBuffer vbo, tbo;
	@SuppressWarnings("unused")
	private IndexBuffer ibo;
	private Texture texture;
	private Shader shader;
	private String vertexShader = "#version 330 core"
				            +"\n"+"layout (location = 0) in vec3 aPos;"
				            +"\n"+"layout (location = 1) in vec2 aTexCoord;"
				            +"\n"+"out vec2 oTexCoord;"
				            +"\n"+"uniform mat4 uProjection;"
				            +"\n"+"uniform mat4 uView;"
				            +"\n"+"uniform mat4 uModel;"
				            +"\n"+"void main()"
				            +"\n"+"{"
				            +"\n"+"  oTexCoord = aTexCoord;"
				            +"\n"+"  gl_Position = uProjection * uView * uModel * vec4(aPos.x, aPos.y, aPos.z, 1.0);"
				            +"\n"+"}";
	private String fragmentShader = "#version 330 core"
						      +"\n"+"out vec4 FragColor;"
						      +"\n"+"in vec2 oTexCoord;"
						      +"\n"+"uniform sampler2D uTexture;"
						      +"\n"+"void main()"
						      +"\n"+"{"
						      +"\n"+"  FragColor = texture(uTexture, oTexCoord);"
						      +"\n"+"}";
	private float[] vertices =
	{
		0.5f,  0.5f, 0.0f,
		0.5f, -0.5f, 0.0f,
		-0.5f, -0.5f, 0.0f,
		-0.5f,  0.5f, 0.0f
	};
	
	private float[] texCoords = 
	{
		1.0f, 1.0f,
		1.0f, 0.0f,
		0.0f, 0.0f,
		0.0f, 1.0f
	};
	
	private int[] indices =
	{
		0, 1, 3,
		1, 2, 3
	};
	
	private String path;
	private boolean initialized = false;
	private List<String> properties = new ArrayList<String>();
	
	public SpriteRenderer(String path)
	{
		this.path = path;
		this.properties.add("Sprite");
	}
	
	private void Render(Event e)
	{
		if(initialized)
		{
			this.texture.EnableTextureSlot(1);
			this.texture.Bind();
			this.shader.Bind();
			this.shader.setUniformInt("uTexture", this.texture.getID());
			this.shader.setUniformMat4("uProjection", this.getParent().getScene().getCamera().getProjection());
			this.shader.setUniformMat4("uView", this.getParent().getScene().getCamera().getView());
			this.shader.setUniformMat4("uModel", this.getParent().getTransform().getModel());
			this.vao.Bind();
			Renderer.Draw(indices);
			this.vao.Unbind();
			this.shader.Unbind();
			this.texture.Unbind();
		}
	}
	
	@Override
	public void Initialize()
	{
		addEventHandler("ENGINE_EV_RENDER", this::Render);
		this.initialized = false;
		this.vao = new VertexArray();
		this.vao.Bind();
		this.vbo = new VertexBuffer(vertices);
		this.ibo = new IndexBuffer(indices);
		this.texture = new Texture(this.path);
		
		this.vao.AddAttrib(3, 0, VertexArray.FLOAT, 0);
		
		this.tbo = new VertexBuffer(texCoords);
		this.vao.AddAttrib(2, 0, VertexArray.FLOAT, 0);
		this.shader = new Shader(vertexShader, fragmentShader);
		
		this.vbo.Unbind();
		this.tbo.Unbind();
		this.vao.Unbind();
		this.texture.Unbind();
		this.shader.Unbind();
		this.initialized = true;
	}

	@Override
	public void Deinitialize()
	{
	}

	@Override
	public List<String> getProperties()
	{
		return this.properties;
	}

	@Override
	public String getName()
	{
		return "SpriteRenderer";
	}

	@Override
	public Object getProperty(String name)
	{
		if(name.equals("Sprite"))
		{
			return this.path;
		}
		else
		{
			return null;
		}
	}

	@Override
	public void setProperty(String name, Object value)
	{
		if(name.equals("Sprite"))
		{
			this.initialized = false;
			this.path = (String)value;
			this.texture = new Texture(this.path);
			this.initialized = true;
		}
	}

}
