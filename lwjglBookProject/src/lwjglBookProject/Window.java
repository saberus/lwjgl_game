package lwjglBookProject;
import static org.lwjgl.glfw.GLFW.*;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.glfw.GLFWFramebufferSizeCallback;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.opengl.GL;
import static org.lwjgl.opengl.GL11.GL_FALSE;
import static org.lwjgl.opengl.GL11.GL_TRUE;
import static org.lwjgl.opengl.GL11.glClearColor;
import static org.lwjgl.system.MemoryUtil.NULL;

public class Window {
	
	private String title;
	
	private int width;
	
	private int height;
	
	private long windowHandle;
	
	private boolean resized;
	
	private boolean vSync;

	public Window(String title, int width, int height, boolean vSync) {
		this.title = title;
		this.width = width;
		this.height = height;
		this.vSync = vSync;
		this.resized = false;
	}
	
	public void init() {
		//Setting up an error callback. The default implementation will print the error message in System.err.
		GLFWErrorCallback.createPrint(System.err).set();
		
		//Initialize GLFW. Most GLFW functions will not work before doing this.
		if(!glfwInit()) {
			throw new IllegalStateException("Can't initialize GLFW");
		}
		
		glfwDefaultWindowHints(); //Optional, the current window hints are already current
		glfwWindowHint(GLFW_VISIBLE, GL_FALSE); //The window will stay hidden after creation 
		glfwWindowHint(GLFW_RESIZABLE, GL_TRUE); //The window will be resizable
		glfwWindowHint(GLFW_CONTEXT_VERSION_MAJOR, 3);
		glfwWindowHint(GLFW_CONTEXT_VERSION_MINOR, 2);
		glfwWindowHint(GLFW_OPENGL_PROFILE, GLFW_OPENGL_CORE_PROFILE);
		glfwWindowHint(GLFW_OPENGL_FORWARD_COMPAT, GL_TRUE);
		
		//Creating the window
		windowHandle = glfwCreateWindow(width, height, title, 0, 0);
		if(windowHandle == NULL) {
			throw new RuntimeException("Cannot create window");
		}
		
		// Setup resize callback
		glfwSetFramebufferSizeCallback(windowHandle, (window, width, height) -> {
			this.width = width;
			this.height = height;
			this.setResized(true);
		});
		
		//Setup key callback. It will be called every time a key is pressed, repeated or released.
		glfwSetKeyCallback(windowHandle, (window, key, scancode, action, mods) -> {
			if(key == GLFW_KEY_ESCAPE && action == GLFW_RELEASE) {
				glfwSetWindowShouldClose(window, true); // It will be detected on the render loop.
			}
		});
		
		// Get the resolution of the primary monitor.
		GLFWVidMode vidMode = glfwGetVideoMode(glfwGetPrimaryMonitor());
		
		// Center out the window
		glfwSetWindowPos(windowHandle, (vidMode.width() - width) / 2, (vidMode.height() - height) / 2);
		
		//Make the OpenGL context current
		glfwMakeContextCurrent(windowHandle);
		
		//Enable vSync if needed
		if(vSync == true) {
			glfwSwapInterval(1);
		}
		
		//Make the window visible
		glfwShowWindow(windowHandle);
		
		GL.createCapabilities();
		
		//Set the clear color 
		glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
		//glEnable(GL_DEPTH_TEST);
	}
	
    public void setClearColor(float r, float g, float b, float alpha) {
        glClearColor(r, g, b, alpha);
    }
	
	public boolean isKeyPressed(int keyCode) {
		return glfwGetKey(windowHandle, keyCode) == GLFW_PRESS;
	}

	public boolean isResized() {
		return resized;
	}

	public void setResized(boolean resized) {
		this.resized = resized;
	}

	public boolean isvSync() {
		return vSync;
	}

	public void setvSync(boolean vSync) {
		this.vSync = vSync;
	}

	public String getTitle() {
		return title;
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}
	
	public void update() {
		glfwSwapBuffers(windowHandle);
		glfwPollEvents();
	}
	
	
	
	
	

}
