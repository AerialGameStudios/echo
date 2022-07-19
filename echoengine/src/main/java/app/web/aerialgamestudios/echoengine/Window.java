package app.web.aerialgamestudios.echoengine;

import java.nio.IntBuffer;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL45;
import org.lwjgl.system.MemoryStack;
import org.lwjgl.system.MemoryUtil;

import app.web.aerialgamestudios.echoengine.events.CodeEventPayload;
import app.web.aerialgamestudios.echoengine.events.Event;
import app.web.aerialgamestudios.echoengine.events.EventManager;
import app.web.aerialgamestudios.echoengine.events.KeyboardEventPayload;
import app.web.aerialgamestudios.echoengine.events.MouseEventPayload;
import app.web.aerialgamestudios.echoengine.events.WindowResizePayload;
import imgui.ImFontAtlas;
import imgui.ImGui;
import imgui.ImGuiIO;
import imgui.ImGuiStyle;
import imgui.callback.ImStrConsumer;
import imgui.callback.ImStrSupplier;
import imgui.flag.ImGuiBackendFlags;
import imgui.flag.ImGuiCol;
import imgui.flag.ImGuiConfigFlags;
import imgui.flag.ImGuiKey;
import imgui.flag.ImGuiMouseCursor;
import imgui.gl3.ImGuiImplGl3;
import imgui.type.ImInt;

public class Window
{
	private long handle;
	private static boolean glfwInitialized = false;
	private String title;
	private int width, height;
	private double currentFrame, deltaTime, lastFrame;
    //private final ImGuiImplGlfw imGuiGlfw = new ImGuiImplGlfw();
    private final ImGuiImplGl3 imGuiGl3 = new ImGuiImplGl3();
    private final long[] mouseCursors = new long[ImGuiMouseCursor.COUNT];
	
    private float[] ImVec4(float r, float g, float b, float a)
    {
    	float[] result = {r, g, b, a};
    	return result;
    }
    
    private void initImGuiGLFW()
    {
    	final ImGuiIO io = ImGui.getIO();

        io.setIniFilename("imgui.ini"); // We don't want to save .ini file
        io.setConfigFlags(ImGuiConfigFlags.NavEnableKeyboard); // Navigation with keyboard
        io.setConfigFlags(ImGuiConfigFlags.DockingEnable);
        io.setBackendFlags(ImGuiBackendFlags.HasMouseCursors); // Mouse cursors to display while resizing windows etc.
        io.setBackendPlatformName("imgui_java_impl_glfw");

        // ------------------------------------------------------------
        // Keyboard mapping. ImGui will use those indices to peek into the io.KeysDown[] array.
        final int[] keyMap = new int[ImGuiKey.COUNT];
        keyMap[ImGuiKey.Tab] = GLFW.GLFW_KEY_TAB;
        keyMap[ImGuiKey.LeftArrow] = GLFW.GLFW_KEY_LEFT;
        keyMap[ImGuiKey.RightArrow] = GLFW.GLFW_KEY_RIGHT;
        keyMap[ImGuiKey.UpArrow] = GLFW.GLFW_KEY_UP;
        keyMap[ImGuiKey.DownArrow] = GLFW.GLFW_KEY_DOWN;
        keyMap[ImGuiKey.PageUp] = GLFW.GLFW_KEY_PAGE_UP;
        keyMap[ImGuiKey.PageDown] = GLFW.GLFW_KEY_PAGE_DOWN;
        keyMap[ImGuiKey.Home] = GLFW.GLFW_KEY_HOME;
        keyMap[ImGuiKey.End] = GLFW.GLFW_KEY_END;
        keyMap[ImGuiKey.Insert] = GLFW.GLFW_KEY_INSERT;
        keyMap[ImGuiKey.Delete] = GLFW.GLFW_KEY_DELETE;
        keyMap[ImGuiKey.Backspace] = GLFW.GLFW_KEY_BACKSPACE;
        keyMap[ImGuiKey.Space] = GLFW.GLFW_KEY_SPACE;
        keyMap[ImGuiKey.Enter] = GLFW.GLFW_KEY_ENTER;
        keyMap[ImGuiKey.Escape] = GLFW.GLFW_KEY_ESCAPE;
        keyMap[ImGuiKey.KeyPadEnter] = GLFW.GLFW_KEY_KP_ENTER;
        keyMap[ImGuiKey.A] = GLFW.GLFW_KEY_A;
        keyMap[ImGuiKey.C] = GLFW.GLFW_KEY_C;
        keyMap[ImGuiKey.V] = GLFW.GLFW_KEY_V;
        keyMap[ImGuiKey.X] = GLFW.GLFW_KEY_X;
        keyMap[ImGuiKey.Y] = GLFW.GLFW_KEY_Y;
        keyMap[ImGuiKey.Z] = GLFW.GLFW_KEY_Z;
        io.setKeyMap(keyMap);

        // ------------------------------------------------------------
        // Mouse cursors mapping
        mouseCursors[ImGuiMouseCursor.Arrow] = GLFW.glfwCreateStandardCursor(GLFW.GLFW_ARROW_CURSOR);
        mouseCursors[ImGuiMouseCursor.TextInput] = GLFW.glfwCreateStandardCursor(GLFW.GLFW_IBEAM_CURSOR);
        mouseCursors[ImGuiMouseCursor.ResizeAll] = GLFW.glfwCreateStandardCursor(GLFW.GLFW_ARROW_CURSOR);
        mouseCursors[ImGuiMouseCursor.ResizeNS] = GLFW.glfwCreateStandardCursor(GLFW.GLFW_VRESIZE_CURSOR);
        mouseCursors[ImGuiMouseCursor.ResizeEW] = GLFW.glfwCreateStandardCursor(GLFW.GLFW_HRESIZE_CURSOR);
        mouseCursors[ImGuiMouseCursor.ResizeNESW] = GLFW.glfwCreateStandardCursor(GLFW.GLFW_ARROW_CURSOR);
        mouseCursors[ImGuiMouseCursor.ResizeNWSE] = GLFW.glfwCreateStandardCursor(GLFW.GLFW_ARROW_CURSOR);
        mouseCursors[ImGuiMouseCursor.Hand] = GLFW.glfwCreateStandardCursor(GLFW.GLFW_HAND_CURSOR);
        mouseCursors[ImGuiMouseCursor.NotAllowed] = GLFW.glfwCreateStandardCursor(GLFW.GLFW_ARROW_CURSOR);

        // ------------------------------------------------------------
        // GLFW callbacks to handle user input

        GLFW.glfwSetKeyCallback(this.handle, (w, key, scancode, action, mods) -> {
            if (action == GLFW.GLFW_PRESS) {
                io.setKeysDown(key, true);
            } else if (action == GLFW.GLFW_RELEASE) {
                io.setKeysDown(key, false);
            }

            io.setKeyCtrl(io.getKeysDown(GLFW.GLFW_KEY_LEFT_CONTROL) || io.getKeysDown(GLFW.GLFW_KEY_RIGHT_CONTROL));
            io.setKeyShift(io.getKeysDown(GLFW.GLFW_KEY_LEFT_SHIFT) || io.getKeysDown(GLFW.GLFW_KEY_RIGHT_SHIFT));
            io.setKeyAlt(io.getKeysDown(GLFW.GLFW_KEY_LEFT_ALT) || io.getKeysDown(GLFW.GLFW_KEY_RIGHT_ALT));
            io.setKeySuper(io.getKeysDown(GLFW.GLFW_KEY_LEFT_SUPER) || io.getKeysDown(GLFW.GLFW_KEY_RIGHT_SUPER));

            if (!io.getWantCaptureKeyboard()) {
            	EventManager.getManager().dispatchEvent(new Event(new KeyboardEventPayload(0, action, key, -1), "ENGINE_EV_KEY_INPUT"));
            }
        });

        GLFW.glfwSetCharCallback(this.handle, (w, c) -> {
            if (c != GLFW.GLFW_KEY_DELETE) {
                io.addInputCharacter(c);
            }
        });

        GLFW.glfwSetMouseButtonCallback(this.handle, (w, button, action, mods) -> {
            final boolean[] mouseDown = new boolean[5];

            mouseDown[0] = button == GLFW.GLFW_MOUSE_BUTTON_1 && action != GLFW.GLFW_RELEASE;
            mouseDown[1] = button == GLFW.GLFW_MOUSE_BUTTON_2 && action != GLFW.GLFW_RELEASE;
            mouseDown[2] = button == GLFW.GLFW_MOUSE_BUTTON_3 && action != GLFW.GLFW_RELEASE;
            mouseDown[3] = button == GLFW.GLFW_MOUSE_BUTTON_4 && action != GLFW.GLFW_RELEASE;
            mouseDown[4] = button == GLFW.GLFW_MOUSE_BUTTON_5 && action != GLFW.GLFW_RELEASE;

            io.setMouseDown(mouseDown);

            if (!io.getWantCaptureMouse() && mouseDown[1]) {
                ImGui.setWindowFocus(null);
            }

            if (!io.getWantCaptureMouse()) {
            	EventManager.getManager().dispatchEvent(new Event(new MouseEventPayload(0, -1, -1, button, action), "ENGINE_EV_CURSOR_BUTTON"));
            }
        });

        GLFW.glfwSetScrollCallback(this.handle, (w, xOffset, yOffset) -> {
            io.setMouseWheelH(io.getMouseWheelH() + (float) xOffset);
            io.setMouseWheel(io.getMouseWheel() + (float) yOffset);
        });

        io.setSetClipboardTextFn(new ImStrConsumer() {
            @Override
            public void accept(final String s) {
                GLFW.glfwSetClipboardString(handle, s);
            }
        });

        io.setGetClipboardTextFn(new ImStrSupplier() {
            @Override
            public String get() {
                final String clipboardString = GLFW.glfwGetClipboardString(handle);
                if (clipboardString != null) {
                    return clipboardString;
                } else {
                    return "";
                }
            }
        }); // After all fonts were added we don't need this config more
        
        ImGuiStyle style = ImGui.getStyle();
        
    	style.setDisplayWindowPadding(15, 15);
    	style.setWindowRounding(5.0f);;
    	style.setFramePadding(5, 5);;
    	style.setFrameRounding(4.0f);;
    	style.setItemSpacing(12, 8);
    	style.setItemInnerSpacing(8, 6);
    	style.setIndentSpacing(25.0f);
    	style.setScrollbarSize(15.0f);
    	style.setScrollbarRounding(9.0f);
    	style.setGrabMinSize(5.0f);
    	style.setGrabRounding(3.0f);
    	
    	float[][] Colors = style.getColors();
    	Colors[ImGuiCol.Text] = ImVec4(0.80f, 0.80f, 0.83f, 1.00f);
    	Colors[ImGuiCol.TextDisabled] = ImVec4(0.24f, 0.23f, 0.29f, 1.00f);
    	Colors[ImGuiCol.WindowBg] = ImVec4(0.06f, 0.05f, 0.1f, 1.00f);
    	//Colors[ImGuiCol.ChildWindowBg] = ImVec4(0.07f, 0.07f, 0.09f, 1.00f);
    	Colors[ImGuiCol.PopupBg] = ImVec4(0.07f, 0.07f, 0.09f, 1.00f);
    	Colors[ImGuiCol.Border] = ImVec4(0.80f, 0.80f, 0.83f, 0.88f);
    	Colors[ImGuiCol.BorderShadow] = ImVec4(0.92f, 0.91f, 0.88f, 0.00f);
    	Colors[ImGuiCol.FrameBg] = ImVec4(0.10f, 0.09f, 0.2f, 1.00f);
    	Colors[ImGuiCol.FrameBgHovered] = ImVec4(0.24f, 0.23f, 0.29f, 1.00f);
    	Colors[ImGuiCol.FrameBgActive] = ImVec4(0.56f, 0.56f, 0.58f, 1.00f);
    	Colors[ImGuiCol.TitleBg] = ImVec4(0.10f, 0.09f, 0.2f, 1.00f);
    	Colors[ImGuiCol.TitleBgCollapsed] = ImVec4(1.00f, 0.98f, 0.95f, 0.75f);
    	Colors[ImGuiCol.TitleBgActive] = ImVec4(0.07f, 0.07f, 0.09f, 1.00f);
    	Colors[ImGuiCol.MenuBarBg] = ImVec4(0.10f, 0.09f, 0.2f, 1.00f);
    	Colors[ImGuiCol.ScrollbarBg] = ImVec4(0.10f, 0.09f, 0.2f, 1.00f);
    	Colors[ImGuiCol.ScrollbarGrab] = ImVec4(0.80f, 0.80f, 0.83f, 0.31f);
    	Colors[ImGuiCol.ScrollbarGrabHovered] = ImVec4(0.56f, 0.56f, 0.58f, 1.00f);
    	Colors[ImGuiCol.ScrollbarGrabActive] = ImVec4(0.06f, 0.05f, 0.1f, 1.00f);
    	//Colors[ImGuiCol.ComboBg] = ImVec4(0.19f, 0.18f, 0.21f, 1.00f);
    	Colors[ImGuiCol.CheckMark] = ImVec4(0.80f, 0.80f, 0.83f, 0.31f);
    	Colors[ImGuiCol.SliderGrab] = ImVec4(0.80f, 0.80f, 0.83f, 0.31f);
    	Colors[ImGuiCol.SliderGrabActive] = ImVec4(0.06f, 0.05f, 0.1f, 1.00f);
    	Colors[ImGuiCol.Button] = ImVec4(0.10f, 0.09f, 0.2f, 1.00f);
    	Colors[ImGuiCol.ButtonHovered] = ImVec4(0.24f, 0.23f, 0.29f, 1.00f);
    	Colors[ImGuiCol.ButtonActive] = ImVec4(0.56f, 0.56f, 0.58f, 1.00f);
    	Colors[ImGuiCol.Header] = ImVec4(0.10f, 0.09f, 0.2f, 1.00f);
    	Colors[ImGuiCol.HeaderHovered] = ImVec4(0.56f, 0.56f, 0.58f, 1.00f);
    	Colors[ImGuiCol.HeaderActive] = ImVec4(0.06f, 0.05f, 0.1f, 1.00f);
    	//Colors[ImGuiCol.Column] = ImVec4(0.56f, 0.56f, 0.58f, 1.00f);
    	//Colors[ImGuiCol.ColumnHovered] = ImVec4(0.24f, 0.23f, 0.29f, 1.00f);
    	//Colors[ImGuiCol.ColumnActive] = ImVec4(0.56f, 0.56f, 0.58f, 1.00f);
    	Colors[ImGuiCol.ResizeGrip] = ImVec4(0.00f, 0.00f, 0.00f, 0.00f);
    	Colors[ImGuiCol.ResizeGripHovered] = ImVec4(0.56f, 0.56f, 0.58f, 1.00f);
    	Colors[ImGuiCol.ResizeGripActive] = ImVec4(0.06f, 0.05f, 0.1f, 1.00f);
    	//Colors[ImGuiCol.CloseButton] = ImVec4(0.40f, 0.39f, 0.38f, 0.16f);
    	//Colors[ImGuiCol.CloseButtonHovered] = ImVec4(0.40f, 0.39f, 0.38f, 0.39f);
    	//Colors[ImGuiCol.CloseButtonActive] = ImVec4(0.40f, 0.39f, 0.38f, 1.00f);
    	Colors[ImGuiCol.PlotLines] = ImVec4(0.40f, 0.39f, 0.38f, 0.63f);
    	Colors[ImGuiCol.PlotLinesHovered] = ImVec4(0.25f, 1.00f, 0.00f, 1.00f);
    	Colors[ImGuiCol.PlotHistogram] = ImVec4(0.40f, 0.39f, 0.38f, 0.63f);
    	Colors[ImGuiCol.PlotHistogramHovered] = ImVec4(0.25f, 1.00f, 0.00f, 1.00f);
    	Colors[ImGuiCol.TextSelectedBg] = ImVec4(0.25f, 1.00f, 0.00f, 0.43f);
    	//Colors[ImGuiCol.ModalWindowDarkening] = ImVec4(1.00f, 0.98f, 0.95f, 0.73f);
    	
    	style.setColors(Colors);
    	
    	ImFontAtlas atlas = io.getFonts();
    	atlas.addFontFromFileTTF("fonts/Roboto-Medium.ttf", 14);
    	atlas.build();
    }
    
    private void ImGuiGLFWNewFrame()
    {
        float[] winWidth = {this.width};
        float[] winHeight = {this.height};
        double[] mousePosX = {0};
        double[] mousePosY = {0};
        GLFW.glfwGetCursorPos(this.handle, mousePosX, mousePosY);
        
        final ImGuiIO io = ImGui.getIO();
        io.setDisplaySize(winWidth[0], winHeight[0]);
        io.setDisplayFramebufferScale(1f, 1f);
        io.setMousePos((float) mousePosX[0], (float) mousePosY[0]);
        io.setDeltaTime((float) deltaTime);
    }
    
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
		
		GLFW.glfwSetWindowSizeCallback(this.handle, (handle, aWidth, aHeight) -> 
		{
			this.width = aWidth;
			this.height = aHeight;
			EventManager.getManager().dispatchEvent(new Event(new WindowResizePayload(aWidth, aHeight), "ENGINE_EV_RESIZE"));
		});
		
		GLFW.glfwSetCharCallback(this.handle, (handle, codePoint) -> 
		{
			EventManager.getManager().dispatchEvent(new Event(new KeyboardEventPayload(1, -1, -1, codePoint), "ENGINE_EV_KEY_TYPE"));
		});
		
		GLFW.glfwSetCursorPosCallback(this.handle, (handle, xpos, ypos) -> 
		{
			EventManager.getManager().dispatchEvent(new Event(new MouseEventPayload(0, xpos, ypos, -1, -1), "ENGINE_EV_CURSOR_POS"));
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
		GL45.glEnable(GL45.GL_BLEND);
		GL45.glBlendFunc(GL45.GL_SRC_ALPHA, GL45.GL_ONE_MINUS_SRC_ALPHA);
		
		ImGui.createContext();
		
		EventManager.getManager().dispatchEvent(new Event(new CodeEventPayload(), "ENGINE_EV_INIT"));
		
        //imGuiGlfw.init(handle, true);
        initImGuiGLFW();
        imGuiGl3.init("#version 330");
        
        currentFrame = GLFW.glfwGetTime();
        lastFrame = currentFrame;
		
		while (!GLFW.glfwWindowShouldClose(this.handle))
		{
			currentFrame = GLFW.glfwGetTime();
	        deltaTime = currentFrame - lastFrame;
	        lastFrame = currentFrame;
			
			EventManager.getManager().dispatchEvent(new Event(new CodeEventPayload(), "ENGINE_EV_PRE_CLEAR"));
			GL45.glClear(GL45.GL_COLOR_BUFFER_BIT | GL45.GL_DEPTH_BUFFER_BIT);
			EventManager.getManager().dispatchEvent(new Event(new CodeEventPayload(), "ENGINE_EV_POST_CLEAR"));
			ImGuiGLFWNewFrame();
	        ImGui.newFrame();
			EventManager.getManager().dispatchEvent(new Event(new CodeEventPayload(), "ENGINE_EV_RENDER"));
			
	        ImGui.render();
	        imGuiGl3.renderDrawData(ImGui.getDrawData());

	        if (ImGui.getIO().hasConfigFlags(ImGuiConfigFlags.ViewportsEnable))
	        {
	            final long backupWindowPtr = GLFW.glfwGetCurrentContext();
	            ImGui.updatePlatformWindows();
	            ImGui.renderPlatformWindowsDefault();
	            GLFW.glfwMakeContextCurrent(backupWindowPtr);
	        }
	        
			GLFW.glfwSwapBuffers(this.handle);
			EventManager.getManager().dispatchEvent(new Event(new CodeEventPayload(), "ENGINE_EV_SWAP"));
			GLFW.glfwPollEvents();
			EventManager.getManager().dispatchEvent(new Event(new CodeEventPayload(), "ENGINE_EV_POLL"));
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
