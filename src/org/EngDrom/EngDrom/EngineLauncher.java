package org.EngDrom.EngDrom;

import org.EngDrom.EngDrom.conf.InputConf;
import org.EngDrom.EngDrom.project.Project;
import org.EngDrom.GDrom.EngineManager;
import org.EngDrom.GDrom.opengl.Window;
import org.lwjgl.glfw.GLFW;

/**
 * 
 * Entry Point of the Engine
 * @author MrThimote, Itai12
 *
 */
public class EngineLauncher {

	public static void main( String[] args ) {
		// Get current configuration
		InputConf user_config = new InputConf(args);
		Project   project     = new Project(user_config);
		
		Window window = EngineManager.createWindow(1200, 800, project.user_pref.project_name);
		window.setContext();
		
		project.user_pref.save(user_config.project_path + "user.pref");
		
		while (!GLFW.glfwWindowShouldClose(window.getWindow())) {
			GLFW.glfwSwapBuffers(window.getWindow());

			GLFW.glfwPollEvents();
		}
	}
	
}
