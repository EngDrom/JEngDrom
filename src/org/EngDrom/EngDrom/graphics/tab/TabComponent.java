package org.EngDrom.EngDrom.graphics.tab;

import org.EngDrom.LibOpenGL.engine.graphics.Renderer;

/**
 * 
 * Window tab contains for example the list of items in a world, a text editor
 * For JDrom... Abstract class representing methods that a Tab must contain
 * @author MrThimote, Itai12
 * 
 */
public abstract class TabComponent {

	public abstract String getTabName();
	
	public boolean         created;
	public abstract void   create();
	
	// Rebuild inner components in a restricted window area
	public abstract void   build(int sx, int sy, int w, int h);
	public abstract void   render(Renderer renderer);
	
}
