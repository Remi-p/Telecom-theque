package fr.enseirb.t2.telecomtheque.endpoints;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

/**
 * Gestion des endpoints relatifs aux vitrines
 */
@Path("vitrines")
public class VitrinesEndpoints {
	
	/**
	 * GET des vitrines
	 *
	 * @param  
	 * @throws 
	 * @return
	 */
	@GET  
	@Path("")
	@Produces("application/json")
	public Response GetVitrines(){

		String json = "";
		
		// return http code  200
		return Response.status(200).entity(json).build();			
	}
	
	/**
	 * GET d'une seule vitrine
	 *
	 * @param  
	 * @throws 
	 * @return
	 */
	@GET  
	@Path("/{id}")
	@Produces("application/json")
	public Response GetVitrine(){

		String json = "";
		
		// return http code  200
		return Response.status(200).entity(json).build();			
	}
	
	
}
