package org.EngDrom.EngDrom.graphics.components;

import org.EngDrom.LibOpenGL.engine.graphics.Renderer;

public abstract class Component {

	public boolean         created;
	public abstract void   create();
	
	// Rebuild inner components in a restricted window area
	public abstract void   build(int sx, int sy, int w, int h);
	public abstract void   render(Renderer renderer);
	
}
