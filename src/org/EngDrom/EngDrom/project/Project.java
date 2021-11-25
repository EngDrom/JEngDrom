package org.EngDrom.EngDrom.project;

import org.EngDrom.EngDrom.conf.InputConf;
import org.EngDrom.EngDrom.conf.UserPreferences;

/**
 * 
 * Project class represents the status of the opened project
 * @author MrThimote, Itai12
 *
 */
public class Project {
	
	// Project directory
	public final String          project_path;
	// Command line arguments
	public final InputConf       user_config;
	// User preferences (located in $PROJECT$/user.pref)
	public final UserPreferences user_pref;
	
	public Project(InputConf user_config) {
		// Initialize project path and configuration
		this.project_path = user_config.project_path;
		this.user_config  = user_config;
		this.user_pref    = new UserPreferences(this.project_path + "user.pref");
		
		// If the user.pref file could not be found (resulting in an error), save the default config to the file
		if (this.user_pref.had_error) {
			this.user_pref.save(this.project_path + "user.pref");
			System.out.println("Could not find the user.pref file, just created it for you");
		}
	}
	
}
