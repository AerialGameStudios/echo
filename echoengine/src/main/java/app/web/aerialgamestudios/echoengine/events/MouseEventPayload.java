package app.web.aerialgamestudios.echoengine.events;

public class MouseEventPayload
{
    /** The key or button was released. */
    public static final int RELEASE = 0; // From GLFW impl in LWJGL (BSD-3-Clause)

    /** The key or button was pressed. */
    public static final int PRESS = 1; // From GLFW impl in LWJGL (BSD-3-Clause)

    /** The key was held down until it repeated. */
    public static final int REPEAT = 2; // From GLFW impl in LWJGL (BSD-3-Clause)
    
    public static final int // From GLFW impl in LWJGL (BSD-3-Clause)
	    MOUSE_BUTTON_1      = 0,
	    MOUSE_BUTTON_2      = 1,
	    MOUSE_BUTTON_3      = 2,
	    MOUSE_BUTTON_4      = 3,
	    MOUSE_BUTTON_5      = 4,
	    MOUSE_BUTTON_6      = 5,
	    MOUSE_BUTTON_7      = 6,
	    MOUSE_BUTTON_8      = 7,
	    MOUSE_BUTTON_LAST   = MOUSE_BUTTON_8,
	    MOUSE_BUTTON_LEFT   = MOUSE_BUTTON_1,
	    MOUSE_BUTTON_RIGHT  = MOUSE_BUTTON_2,
	    MOUSE_BUTTON_MIDDLE = MOUSE_BUTTON_3;
    
	public int type; // 0 is position, 1 is button input
	
	// Position ONLY
	public double mouseX, mouseY;
	
	// Button Input ONLY
	public int button, action;

	public MouseEventPayload(int type, double mouseX, double mouseY, int button, int action)
	{
		this.type = type;
		this.mouseX = mouseX;
		this.mouseY = mouseY;
		this.button = button;
		this.action = action;
	}	
}
