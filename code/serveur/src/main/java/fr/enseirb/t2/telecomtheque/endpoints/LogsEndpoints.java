package fr.enseirb.t2.telecomtheque.endpoints;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 * Endpoints to access logs
 */
@Path("logs")
public class LogsEndpoints {
	
	/**
	 * @api {get} /logs Logs au format HTML.
	 * @apiVersion 1.0.0
	 * @apiName htmlLogs
	 * @apiGroup Logs
	 *
	 * @apiDescription Retourne les logs au format HTML.
	 *
	 * @apiExample Exemple :
	 * curl -i http://tgourdel.rtrinity.enseirb-matmeca.fr/api/logs
	 *
	 * @apiSuccess {String}   InputStream  Retourne une page HTML.
	 */
	@GET
	@Produces(MediaType.TEXT_HTML)
	public InputStream htmlLogs() throws FileNotFoundException
	{
	   // return the logs html file
	   File f = new File("src/main/html/logs/logs.html");
	   return new FileInputStream(f);
	}
	
	/**
	 * @api {get} /logs/txt Logs au format TEXT.
	 * @apiVersion 1.0.0
	 * @apiName htmlLogs
	 * @apiGroup Logs
	 *
	 * @apiDescription Retourne les logs au format TXT.
	 *
	 * @apiExample Exemple :
	 * curl -i http://tgourdel.rtrinity.enseirb-matmeca.fr/api/txt
	 *
	 * @apiSuccess {String}   InputStream  Retourne une page TXT.
	 */
	@GET
	@Path("/txt")
	@Produces(MediaType.TEXT_PLAIN)
	public InputStream textLogs() throws FileNotFoundException
	{
	   // return the logs html file
	   File f = new File("src/main/html/logs/logs.txt");
	   return new FileInputStream(f);
	}
	
}
