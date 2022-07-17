package app.web.aerialgamestudios.echoplayer;

import org.joml.Vector2f;

import app.web.aerialgamestudios.echoengine.components.Camera;
import app.web.aerialgamestudios.echoengine.core.Application;
import app.web.aerialgamestudios.echoengine.events.Event;
import app.web.aerialgamestudios.echoengine.events.EventManager;
import app.web.aerialgamestudios.echoengine.opengl.IndexBuffer;
import app.web.aerialgamestudios.echoengine.opengl.Renderer;
import app.web.aerialgamestudios.echoengine.opengl.Shader;
import app.web.aerialgamestudios.echoengine.opengl.Texture;
import app.web.aerialgamestudios.echoengine.opengl.VertexArray;
import app.web.aerialgamestudios.echoengine.opengl.VertexBuffer;

public class EchoPlayer extends Application
{
	private VertexArray vao;
	private VertexBuffer vbo, tbo;
	private IndexBuffer ibo;
	private Texture texture;
	private Shader shader;
	private Camera camera;
	
	private String vertexShader = "#version 330 core"
			                +"\n"+"layout (location = 0) in vec3 aPos;"
			                +"\n"+"layout (location = 1) in vec2 aTexCoord;"
			                +"\n"+"out vec2 oTexCoord;"
			                +"\n"+"uniform mat4 uProjection;"
			                +"\n"+"uniform mat4 uView;"
			                +"\n"+"void main()"
			                +"\n"+"{"
			                +"\n"+"  oTexCoord = aTexCoord;"
			                +"\n"+"  gl_Position = uProjection * uView * vec4(aPos.x, aPos.y, aPos.z, 1.0);"
			                +"\n"+"}";
	private String fragmentShader = "#version 330 core"
						      +"\n"+"out vec4 FragColor;"
						      +"\n"+"in vec2 oTexCoord;"
						      +"\n"+"uniform sampler2D uTexture;"
						      +"\n"+"void main()"
						      +"\n"+"{"
						      +"\n"+"  FragColor = texture(uTexture, oTexCoord);"
						      +"\n"+"}";
	float[] vertices =
	{
		     0.5f,  0.5f, 0.0f,
		     0.5f, -0.5f, 0.0f,
		    -0.5f, -0.5f, 0.0f,
		    -0.5f,  0.5f, 0.0f
	};
	
	float[] texCoords = 
	{
		1.0f, 1.0f,
		1.0f, 0.0f,
		0.0f, 0.0f,
		0.0f, 1.0f
	};
	
	int[] indices =
	{  // note that we start from 0!
		    0, 1, 3,   // first triangle
		    1, 2, 3    // second triangle
	};  
	
	private void Init(Event ev)
	{
		this.vao = new VertexArray();
		this.vao.Bind();
		this.vbo = new VertexBuffer(vertices);
		this.ibo = new IndexBuffer(indices);
		this.texture = new Texture("examples/image.png");
		
		this.vao.AddAttrib(3, 0, VertexArray.FLOAT, 0);
		
		this.tbo = new VertexBuffer(texCoords);
		this.vao.AddAttrib(2, 0, VertexArray.FLOAT, 0);
		this.shader = new Shader(vertexShader, fragmentShader);
		
		this.vbo.Unbind();
		this.tbo.Unbind();
		this.vao.Unbind();
		this.texture.Unbind();
		this.shader.Unbind();
		
		this.camera = new Camera(1920, 1080);
	}
	
	private void Clear(Event ev)
	{
		Renderer.ClearColor(0, 0, 1, 1);
	}
	
	private void OnRender(Event ev)
	{
		this.texture.EnableTextureSlot(1);
		this.texture.Bind();
		this.shader.Bind();
		this.shader.setUniformInt("uTexture", this.texture.getID());
		this.shader.setUniformMat4("uProjection", this.camera.getProjection());
		this.shader.setUniformMat4("uView", this.camera.getView());
		this.vao.Bind();
		Renderer.Draw(indices);
		this.vao.Unbind();
		this.shader.Unbind();
		this.texture.Unbind();
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
