package fr.enseirb.t2.telecomtheque.util;

import java.io.IOException;
//import java.util.logging.ConsoleHandler;
import java.util.logging.FileHandler;
import java.util.logging.Formatter;
//import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

import fr.enseirb.t2.telecomtheque.config.Config;

//import java.util.logging.SimpleFormatter;

public class Logs {
  static private FileHandler fileTxt;
  static private SimpleFormatter formatterTxt;

  static public void setup() throws IOException {

    // get the global logger to configure it
    Logger logger = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

    logger.setLevel(Level.INFO);
    fileTxt = new FileHandler(Config.LOGS_LOCATION + "logs.txt");
    // create a TXT formatter
    formatterTxt = new SimpleFormatter();
    fileTxt.setFormatter(formatterTxt);
    logger.addHandler(fileTxt);
  }
}
 

