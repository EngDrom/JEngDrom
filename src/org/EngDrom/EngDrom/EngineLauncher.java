package org.EngDrom.EngDrom;

import org.EngDrom.EngDrom.conf.InputConf;
import org.EngDrom.EngDrom.project.Project;

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
	}
	
}
