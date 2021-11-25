package org.EngDrom.EngDrom;

public class EngDromConfig {

	public static final String RELEASE_TYPE = "Dev";
	public static final String MAJOR        = "0";
	public static final String MINOR        = "0";
	public static final String SUBVERSION   = "0";
	
	public static final String VERSION = RELEASE_TYPE + "_" + MAJOR + "." + MINOR + "." + SUBVERSION;
	
	public static void printUsage() {
		System.out.println("Welcome to EngDrom version " + VERSION + " !");
		System.out.println("An error occured while we were parsing the args");
		System.out.println("We couldn't find the EngDrom project directory");
		System.out.println("The project path should be the argument for the EngDrom");
		System.out.println("Native launcher, should be used like this : engdrom <path>");
	}
	
}
