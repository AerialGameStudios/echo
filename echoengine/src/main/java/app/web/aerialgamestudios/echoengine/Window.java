package app.web.aerialgamestudios.echoengine;

import java.nio.IntBuffer;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL33;
import org.lwjgl.system.MemoryStack;
import org.lwjgl.system.MemoryUtil;

import app.web.aerialgamestudios.echoengine.events.Event;
import app.web.aerialgamestudios.echoengine.events.EventManager;
import app.web.aerialgamestudios.echoengine.events.KeyboardEventPayload;

public class Window
{
	private long handle;
	private static boolean glfwInitialized = false;
	private String title;
	private int width, height;
	
	public Window(String title, int width, int height)
	{
		if(!glfwInitialized)
		{
			if(!GLFW.glfwInit())
			{
				throw new IllegalStateException("Unable to initialize GLFW, Crash called!");
			}
			else
			{
				glfwInitialized = true;
			}
		}
		
		GLFW.glfwDefaultWindowHints();
		GLFW.glfwWindowHint(GLFW.GLFW_VISIBLE, GLFW.GLFW_FALSE);
		GLFW.glfwWindowHint(GLFW.GLFW_RESIZABLE, GLFW.GLFW_TRUE);
		
		this.handle = GLFW.glfwCreateWindow(width, height, title, MemoryUtil.NULL, MemoryUtil.NULL);
		
		if(this.handle == MemoryUtil.NULL)
		{
			throw new RuntimeException("GLFW window failed to initialize!");
		}
		
		GLFW.glfwSetKeyCallback(this.handle, (handle, key, scancode, action, mods) -> 
		{
			EventManager.getManager().dispatchEvent(new Event(new KeyboardEventPayload(0, action, key, -1), "ENGINE_EV_KEY_INPUT"));
		});
		
		GLFW.glfwSetCharCallback(this.handle, (handle, codePoint) -> 
		{
			EventManager.getManager().dispatchEvent(new Event(new KeyboardEventPayload(0, -1, -1, codePoint), "ENGINE_EV_KEY_TYPE"));
		});
		
		
		
		MemoryStack stack = MemoryStack.stackPush();
		IntBuffer widthBuffer = stack.mallocInt(1);
		IntBuffer heightBuffer = stack.mallocInt(1);
		
		GLFW.glfwGetWindowSize(this.handle, widthBuffer, heightBuffer);
		
		GLFWVidMode vidmode = GLFW.glfwGetVideoMode(GLFW.glfwGetPrimaryMonitor());
		
		GLFW.glfwSetWindowPos(
				this.handle,
				(vidmode.width() - widthBuffer.get(0)) / 2,
				(vidmode.height() - heightBuffer.get(0)) / 2
		);
		
		GLFW.glfwMakeContextCurrent(this.handle);
		GLFW.glfwShowWindow(this.handle);
		
		GL.createCapabilities();
		
		while (!GLFW.glfwWindowShouldClose(this.handle))
		{
			GL33.glClear(GL33.GL_COLOR_BUFFER_BIT | GL33.GL_DEPTH_BUFFER_BIT);
			
			GLFW.glfwSwapBuffers(this.handle);
			GLFW.glfwPollEvents();
		}
	}

	public String getTitle()
	{
		return title;
	}

	public int getWidth()
	{
		return width;
	}

	public int getHeight() {
		return height;
	}
}
