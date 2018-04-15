package lwjglProject.game;

import org.lwjgl.glfw.*;
import org.lwjgl.opengl.GL;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;

public class Window {

	private int width;
	private int height;
	private String title;
	private boolean resized;
	private boolean vSync;
	
	private long windowHandle;
	
	public Window(int width, int height, String title, boolean vSync) {
		this.width = width;
		this.height = height;
		this.title = title;
		this.vSync = vSync;
		this.resized = false;
		
	}
	
	public void init() {
		
		//Setup Error Callback
		GLFWErrorCallback.createPrint(System.err).set();
		
		//Initializing glfw
		if(!glfwInit()) {
			throw new IllegalStateException("cannot initialize glfw");
		}
		
		//Setting up the window hints
		glfwDefaultWindowHints();
		glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE);
		glfwWindowHint(GLFW_RESIZABLE, GL_TRUE);
		glfwWindowHint(GLFW_CONTEXT_VERSION_MAJOR, 3);
		glfwWindowHint(GLFW_CONTEXT_VERSION_MINOR, 2);
		glfwWindowHint(GLFW_OPENGL_PROFILE, GLFW_OPENGL_CORE_PROFILE);
		glfwWindowHint(GLFW_OPENGL_FORWARD_COMPAT, GL_TRUE);
		
		//Creating the window 
		windowHandle = glfwCreateWindow(width, height, title, 0, 0);
		
		if(windowHandle == 0) {
			throw new RuntimeException("cannot create window");
		}
		
		

		
		//Setup resize callback
		glfwSetFramebufferSizeCallback(windowHandle, (window, width, height) -> {
			
			this.width = width;
			this.height = height;
			this.setResized(true);
		});
		
		//Setup escape key press callback
		glfwSetKeyCallback(windowHandle, (window, key, scancode, action, mods) -> {
			if(key == GLFW_KEY_ESCAPE && action == GLFW_RELEASE) {
				glfwSetWindowShouldClose(windowHandle, true);
			}
		});
		
		
		
		//Getting a primary monitor resolution
		GLFWVidMode videoMode = glfwGetVideoMode(glfwGetPrimaryMonitor());
		
		//Center the window
		glfwSetWindowPos(windowHandle, (videoMode.width() - width) / 2, (videoMode.height() - height) / 2);
		
		//Make context current
		glfwMakeContextCurrent(windowHandle);
				
		//Enable v-Sync
		if(vSync) {
			glfwSwapInterval(1);
		}
		
		//Make window visible
		glfwShowWindow(windowHandle);
		
		GL.createCapabilities();
		
		//Depth test
		glEnable(GL_DEPTH_TEST);
		
		//Setup color for window clearing operation
		//glClearColor(0.2f, 0.3f, 0.3f, 0.1f);
		
		setClearColor(0.2f, 0.3f, 0.3f, 0.1f);
	}

	public void setClearColor(float r, float g, float b, float alpha) {
		glClearColor(r, g, b, alpha);
	}
	
	public boolean isKeyPressed(int keyCode) {
		return glfwGetKey(windowHandle, keyCode) == GLFW_PRESS;
	}
	
	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
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
	
	public void update() {
		glfwSwapBuffers(windowHandle);
		glfwPollEvents();
	}
	
}
