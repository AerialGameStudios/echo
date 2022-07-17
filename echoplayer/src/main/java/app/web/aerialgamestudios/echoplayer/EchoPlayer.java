package app.web.aerialgamestudios.echoplayer;

import app.web.aerialgamestudios.echoengine.core.Application;
import app.web.aerialgamestudios.echoengine.events.Event;
import app.web.aerialgamestudios.echoengine.events.EventManager;
import app.web.aerialgamestudios.echoengine.opengl.IndexBuffer;
import app.web.aerialgamestudios.echoengine.opengl.Renderer;
import app.web.aerialgamestudios.echoengine.opengl.Shader;
import app.web.aerialgamestudios.echoengine.opengl.VertexArray;
import app.web.aerialgamestudios.echoengine.opengl.VertexBuffer;

public class EchoPlayer extends Application
{
	private VertexArray vao;
	private VertexBuffer vbo;
	private IndexBuffer ibo;
	private Shader shader;
	
	private String vertexShader = "#version 330 core"
			                +"\n"+"layout (location = 0) in vec3 aPos;"
			                +"\n"+"void main()"
			                +"\n"+"{"
			                +"\n"+"  gl_Position = vec4(aPos.x, aPos.y, aPos.z, 1.0);"
			                +"\n"+"}";
	private String fragmentShader = "#version 330 core"
						      +"\n"+"out vec4 FragColor;"
						      +"\n"+"void main()"
						      +"\n"+"{"
						      +"\n"+"  FragColor = vec4(1.0f, 0.5f, 0.2f, 1.0f);"
						      +"\n"+"}";
	float[] vertices = {
		     0.5f,  0.5f, 0.0f,  // top right
		     0.5f, -0.5f, 0.0f,  // bottom right
		    -0.5f, -0.5f, 0.0f,  // bottom left
		    -0.5f,  0.5f, 0.0f   // top left 
	};
	int[] indices = {  // note that we start from 0!
		    0, 1, 3,   // first triangle
		    1, 2, 3    // second triangle
	};  
	
	private void Init(Event ev)
	{
		this.vao = new VertexArray();
		this.vao.Bind();
		this.vbo = new VertexBuffer(vertices);
		this.ibo = new IndexBuffer(indices);
		
		this.vao.AddAttrib(3, 0, VertexArray.FLOAT, 0);
		this.shader = new Shader(vertexShader, fragmentShader);
		
		this.vbo.Unbind();
		this.vao.Unbind();
	}
	
	private void Clear(Event ev)
	{
		Renderer.ClearColor(0, 0, 1, 1);
	}
	
	private void OnRender(Event ev)
	{
		this.shader.Bind();
		this.vao.Bind();
		Renderer.Draw(indices);
		this.vao.Unbind();
		this.shader.Unbind();
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
