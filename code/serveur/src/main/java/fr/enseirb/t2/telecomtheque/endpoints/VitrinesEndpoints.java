package fr.enseirb.t2.telecomtheque.endpoints;

import java.util.logging.Logger;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

/**
 * Gestion des endpoints relatifs aux vitrines
 */
@Path("vitrines")
public class VitrinesEndpoints {
	
    // get the global logger
    Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
	/**
	 * GET des vitrines
	 *
	 * @param  
	 * @throws 
	 * @return
	 */
	@GET  
	@Produces("application/json")
	public Response GetVitrines(){

		String json = "";
		
		// return http code  200
		return Response.status(200).entity(json).build();			
	}
	
	/**
	 * GET d'une seule vitrine à partir de son id
	 *
	 * @param  
	 * @throws 
	 * @return
	 */
	@GET  
	@Path("/{idvitrine}")
	@Produces("text/plain")
	public String GetVitrine(){

		return "coucou";
	}
}
