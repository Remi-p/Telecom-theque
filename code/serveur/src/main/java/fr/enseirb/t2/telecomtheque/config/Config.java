package fr.enseirb.t2.telecomtheque.config;

/**
 * Configuration => singleton
 * Global variables
 * 
 **/
public class Config {

	/* General configuration 
	================================================= */
	public static boolean VERBOSE = true;

	// Private constructor : disallow creating instance
	private Config() {}
	
	public static void setVerbose(boolean verb) {
		Config.VERBOSE = verb;
	}
	
}