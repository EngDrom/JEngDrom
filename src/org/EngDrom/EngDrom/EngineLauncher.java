package org.EngDrom.EngDrom;

import org.EngDrom.EngDrom.conf.InputConf;
import org.EngDrom.EngDrom.project.Project;

public class EngineLauncher {

	public static void main( String[] args ) {
		InputConf user_config = new InputConf(args);
		Project   project     = new Project(user_config);
	}
	
}
