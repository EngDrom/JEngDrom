package org.EngDrom.EngDrom.graphics;

import java.util.ArrayList;

import org.EngDrom.EngDrom.graphics.tab.DefaultTab;
import org.EngDrom.EngDrom.graphics.tab.TabComponent;
import org.EngDrom.EngDrom.project.Project;
import org.EngDrom.LibOpenGL.engine.graphics.Renderer;
import org.EngDrom.LibOpenGL.engine.io.Window;
import org.lwjgl.glfw.GLFW;

/**
 * 
 * Static class to manage the graphic library
 * @author MrThimote, Itai12
 *
 */
public class GraphicManager {
	
	public static boolean INITIALIZED = false;
	
	public static ArrayList<TabComponent> tabs;
	public static int                     cur_tab = 0;
	public static Window                  window;
	public static Renderer renderer;
	
	public static void init(Project project) {
		GLFW.glfwInit();
		
		tabs = new ArrayList<TabComponent>();
		GraphicManager.window = new Window(
				project.user_pref.getProjectWidth(), 
				project.user_pref.getProjectHeight(), 
				"EngDrom"
			);
		GraphicManager.window.create();
		
		renderer = new Renderer(null, window, project.user_pref.graphics_shader_version);
		renderer.create();
		
		INITIALIZED = true;
		openTab(new DefaultTab());
		setTab(0);
	}
	
	public static void loop() {
		while(!GraphicManager.window.shouldClose()) {
			GraphicManager.window.update();
			GraphicManager.update(renderer);
			GraphicManager.window.swapBuffers();
		}
		GraphicManager.window.destroy();
	}
	
	public static void openTab(TabComponent comp) {
		if (!comp.created)
			comp.create();
		
		comp.created = true;
		tabs.add(comp);
	}
	
	public static TabComponent getCurrentTab() {
		if (tabs.size() > cur_tab && cur_tab >= 0)
			return tabs.get(cur_tab);
		return null;
	}
	
	public static void setTab(int tab) {
		cur_tab = tab;
		if (getCurrentTab() != null) 
			getCurrentTab().build(0, 0, window.getWidth(), window.getHeight());
	}
	
	public static void setTab(TabComponent tab) {
		cur_tab = tabs.indexOf(tab);
		if (getCurrentTab() != null) 
			getCurrentTab().build(0, 0, window.getWidth(), window.getHeight());
	}
	
	public static void update(Renderer renderer) {
		if (getCurrentTab() != null) getCurrentTab().render(renderer);
	}
	
}
