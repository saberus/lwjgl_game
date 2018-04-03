package lwjglProject;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import org.lwjgl.opengl.GL;

import org.lwjgl.glfw.GLFWVidMode;

public class Main {

	public static void main(String[] args) {
		if(!glfwInit()) {
			throw new IllegalStateException("Failed to initialize GLFW");
		}
		
		glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE);
		long window = glfwCreateWindow(640, 480, "My Window", 0, 0);
		
		if(window == 0) {
			throw new IllegalStateException("Failed to create window");
		}
		
		GLFWVidMode videoMode = glfwGetVideoMode(glfwGetPrimaryMonitor());
		glfwSetWindowPos(window, (videoMode.width() - 640) / 2, (videoMode.height() - 480) / 2);
		
		glfwShowWindow(window);
		
		glfwMakeContextCurrent(window);
		
		while (!glfwWindowShouldClose(window)) {
			glfwPollEvents();
			
			glClear(GL_COLOR_BUFFER_BIT);
			
			glfwSwapBuffers(window);
		}
		
		glfwTerminate();

	}

}
