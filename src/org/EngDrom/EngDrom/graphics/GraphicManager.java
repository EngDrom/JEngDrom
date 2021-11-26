package org.EngDrom.EngDrom.graphics;

import java.util.ArrayList;

import org.EngDrom.EngDrom.graphics.tab.TabComponent;

/**
 * 
 * Static class to manage the graphic library
 * @author MrThimote, Itai12
 *
 */
public class GraphicManager {
	
	public static ArrayList<TabComponent> tabs;
	public static int                     cur_tab;
	
	public static void init() {
		tabs = new ArrayList<TabComponent>();
	}
	
	public static void openTab(TabComponent comp) {
		tabs.add(comp);
	}
	
	public static TabComponent getCurrentTab() {
		if (tabs.size() > cur_tab && cur_tab >= 0)
			return tabs.get(cur_tab);
		return null;
	}
	
	public static void setTab(int tab) {
		cur_tab = tab;
	}
	
	public static void setTab(TabComponent tab) {
		cur_tab = tabs.indexOf(tab);
	}
	
}
