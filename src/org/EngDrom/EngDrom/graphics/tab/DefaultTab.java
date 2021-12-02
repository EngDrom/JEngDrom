package org.EngDrom.EngDrom.graphics.tab;

import org.EngDrom.EngDrom.graphics.GraphicManager;
import org.EngDrom.LibOpenGL.engine.graphics.Renderer;
import org.EngDrom.LibOpenGL.engine.graphics.font.Font;
import org.EngDrom.LibOpenGL.engine.graphics.font.FontManager;
import org.EngDrom.LibOpenGL.engine.graphics.meshes.GUIMesh;
import org.EngDrom.LibOpenGL.engine.maths.Vector3f;
import org.lwjglx.util.vector.Vector4f;

public class DefaultTab extends TabComponent {

	private final GUIMesh background_ui;
	private final GUIMesh test_text;
	public DefaultTab() {
		background_ui = new GUIMesh(new Vector4f(0.2f, 0.2f, 0.2f, 1));
		Font calibri = FontManager.getFont("./ressources/font/calibri.png", "./ressources/font/calibri.fnt");
		test_text = calibri.to_text("text\ntp");
	}
	
	@Override
	public String getTabName() {
		return "Test Tab";
	}

	@Override
	public void build(int sx, int sy, int w, int h) {
		int mx = sx + w / 2;
		int my = sy + h / 2;
		
		int tx = GraphicManager.window.getWidth();
		int ty = GraphicManager.window.getHeight();
	
		float px = ((float) mx) / ((float) tx);
		float py = ((float) my) / ((float) ty);
		
		float opengl_px = px * 2 - 1;
		float opengl_py = py * 2 - 1;
		
		float opengl_sx = ((float) w) / ((float) tx);
		float opengl_sy = ((float) h) / ((float) ty);
		
		background_ui.setScalar(new Vector3f(opengl_sx, opengl_sy, 1));
		background_ui.setTranslation(new Vector3f(opengl_px, opengl_py, 0));
	}

	@Override
	public void render(Renderer renderer) {
		renderer.renderMesh(test_text);
		renderer.renderMesh(background_ui);
	}

	@Override
	public void create() {
		background_ui.create();
		test_text.create();
	}

}
