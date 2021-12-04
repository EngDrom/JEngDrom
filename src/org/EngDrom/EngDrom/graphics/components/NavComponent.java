package org.EngDrom.EngDrom.graphics.components;

import java.util.ArrayList;

import org.EngDrom.EngDrom.graphics.GraphicManager;
import org.EngDrom.EngDrom.graphics.tab.TabComponent;
import org.EngDrom.LibOpenGL.engine.graphics.Renderer;
import org.EngDrom.LibOpenGL.engine.graphics.meshes.GUIMesh;
import org.EngDrom.LibOpenGL.engine.graphics.meshes.TextMesh;
import org.EngDrom.LibOpenGL.engine.maths.Vector3f;
import org.lwjglx.util.vector.Vector4f;

public class NavComponent extends Component {

	public ArrayList<TabComponent> tabs  = new ArrayList<TabComponent>();
	public ArrayList<TextMesh>     texts = new ArrayList<TextMesh>();
	public int                   cur_tab = 0;
	public GUIMesh                    bg = null;
	private int             text_advance = 10;
	
	private int sx;
	private int sy;
	private int w;
	private int h;
	public NavComponent() {
		bg = new GUIMesh(new Vector4f(0.15f, 0.15f, 0.15f, 1));
	}
	
	@Override
	public void create() {
		bg.create();
	}

	@Override
	public void build(int sx, int sy, int w, int h) {
		this.sx = sx;
		this.sy = sy;
		this.w  = w;
		this.h  = h;
		
		float mw = GraphicManager.window.getWidth();
		float mh = GraphicManager.window.getHeight();
		bg.setScalar(new Vector3f(w / mw, h / mh, 1));
		bg.setTranslation(new Vector3f(
				(sx + w / 2) / mw * 2 - 1, 
				(sy + h / 2) / mh * 2 - 1, 0));
	}

	@Override
	public void render(Renderer renderer) {
		for (TextMesh txt:texts) {
			renderer.renderMesh(txt);
		}
		renderer.renderMesh(bg);
	}
	
	public void openTab(TabComponent comp) {
		if (!comp.created)
			comp.create();
		
		comp.created = true;
		tabs.add(comp);
		
		TextMesh txt = GraphicManager.default_font.to_text(comp.getTabName());
		float width  = txt.ratio_woh * (h - 10) - 10;
		
		txt.setScalar(new Vector3f(width / GraphicManager.window.getWidth(),
				              ((float)h - 10) / GraphicManager.window.getHeight(),
				                     1));
		txt.setTranslation(new Vector3f((text_advance + width / 2) / GraphicManager.window.getWidth() * 2 - 1, 
				(sy + ((float)h + 10) / 2) / GraphicManager.window.getHeight() * 2 - 1, 0));
		txt.create();
		
		text_advance += width + 20;
		
		texts.add(txt);
	}
	
	public TabComponent getCurrentTab() {
		if (tabs.size() > cur_tab && cur_tab >= 0)
			return tabs.get(cur_tab);
		return null;
	}
	
	public void setTab(int tab) {
		cur_tab = tab;
	}
	
	public void setTab(TabComponent tab) {
		cur_tab = tabs.indexOf(tab);
	}
	
	public void update(Renderer renderer) {
		this.render(renderer);
		if (getCurrentTab() != null) getCurrentTab().render(renderer);
	}

}
