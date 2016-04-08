package fr.enseirb.t2.telecomtheque.endpoints;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

/**
 * Gestion des endpoints relatifs aux Objets
 */
@Path("objets")
public class ObjetsEndpoints {
	
	/**
	 * GET de tous les objets
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
	
}
