package org.EngDrom.EngDrom.conf;

import org.EngDrom.EngDrom.EngDromConfig;

/**
 * 
 * Static final configuration determined at runtime
 * Based on command arguments. If no arguments or --help
 * Is provided, the InputConf constructor will result with
 * A print on how to use the command line tool and exit the
 * Program
 * @author MrThimote, Itai12
 *
 */
public class InputConf {
	
	public final String project_path;
	public final boolean debug_tools;
	
	public InputConf ( String[] args ) {
		if (args.length <= 0) {
			EngDromConfig.printUsage();
			System.exit(1);
		}
		
		if (args[0] == "-h" || args[0] == "--help") {
			EngDromConfig.printUsage();
			System.exit(0);
		}
		
		this.project_path = args[0];
		
		boolean d_tools = false;
		for (int idx = 1; idx < args.length; idx ++) {
			if (args[idx] == "-d") {
				d_tools = true;
			}
		}
		
		this.debug_tools = d_tools;
	}

}
