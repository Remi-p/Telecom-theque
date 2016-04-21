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
	public static String LOGS_LOCATION;
	
	/* Base de données MongoDB
	================================================= */
	public static String DB = "test";
	public static String OBJETS = "objets";
	public static String VITRINES = "vitrines";

	// Private constructor : disallow creating instance
	private Config() {}
	
	public static void setVerbose(boolean verb) {
		Config.VERBOSE = verb;
	}
	
}