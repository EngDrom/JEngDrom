package org.EngDrom.EngDrom.graphics;

import org.EngDrom.EngDrom.graphics.components.NavComponent;
import org.EngDrom.EngDrom.graphics.tab.DefaultTab;
import org.EngDrom.EngDrom.graphics.tab.TabComponent;
import org.EngDrom.EngDrom.project.Project;
import org.EngDrom.LibOpenGL.engine.graphics.Renderer;
import org.EngDrom.LibOpenGL.engine.graphics.font.Font;
import org.EngDrom.LibOpenGL.engine.graphics.font.FontManager;
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
	
	public static NavComponent navbar = new NavComponent();
	public static Window window;
	public static Renderer renderer;
	public static Font default_font;
	
	public static void init(Project project) {
		GLFW.glfwInit();
		
		GraphicManager.window = new Window(
				project.user_pref.getProjectWidth(), 
				project.user_pref.getProjectHeight(), 
				"EngDrom"
			);
		GraphicManager.window.create();
		
		renderer = new Renderer(null, window, project.user_pref.graphics_shader_version);
		renderer.create();
	
		default_font = FontManager.getFont("./ressources/font/calibri.png", "./ressources/font/calibri.fnt");
		default_font.font_material.create();
		
		INITIALIZED = true;
		navbar.create();
		navbar.build(0, window.getHeight() - 40, window.getWidth(), 40);
		openTab(new DefaultTab("Test Tab"));
		openTab(new DefaultTab("Test Tab (2)"));
		openTab(new DefaultTab("Test Tab (3)"));
		openTab(new DefaultTab("Test Tab (4)"));
		openTab(new DefaultTab("Test Tab (5)"));
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
		navbar.openTab(comp);
	}
	
	public static TabComponent getCurrentTab() {
		return navbar.getCurrentTab();
	}
	
	public static void setTab(int tab) {
		navbar.setTab(tab);
		if (getCurrentTab() != null) 
			getCurrentTab().build(0, 0, window.getWidth(), window.getHeight());
	}
	
	public static void setTab(TabComponent tab) {
		navbar.setTab(tab);
		if (getCurrentTab() != null) 
			getCurrentTab().build(0, 0, window.getWidth(), window.getHeight());
	}
	
	public static void update(Renderer renderer) {
		navbar.render(renderer);
		if (getCurrentTab() != null) getCurrentTab().render(renderer);
	}
	
}
