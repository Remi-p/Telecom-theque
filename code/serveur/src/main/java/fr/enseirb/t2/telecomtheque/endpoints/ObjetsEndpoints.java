package fr.enseirb.t2.telecomtheque.endpoints;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;

import org.bson.Document;

import com.google.gson.Gson;
import com.google.gson.annotations.Expose;
import com.mongodb.client.MongoCursor;

import fr.enseirb.t2.telecomtheque.config.Config;
import fr.enseirb.t2.telecomtheque.models.ObjetReturn;
import fr.enseirb.t2.telecomtheque.models.Objets;
import fr.enseirb.t2.telecomtheque.models.ObjetsBis;
import fr.enseirb.t2.telecomtheque.requests.MongoDB;

/**
 * Gestion des endpoints relatifs aux Objets
 */
@Path("objets")
public class ObjetsEndpoints {
	
    // get the global logger
    Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
    
	/**
	 * GET des objets
	 *
	 * @param  
	 * @throws 
	 * @return
	 */
	@GET
	@Produces("application/json")
	public Response GetObjets(){
		
		// Initialisation
		Gson gson = new Gson();
		List<Objets> listObjets = new ArrayList<Objets>();
		MongoCursor<Document> cursor;
		
		// Connexion à la base de donnée
		MongoDB mongo = new MongoDB("test", "objets"); // db et collection
		
		// Obtention de tous les objets
		 cursor = mongo.ObtentionListe();

		// Boucle sur tous les objets
		try {
		    while (cursor.hasNext()) {
		    	Document objet = cursor.next(); // Deplacement du curseur
		    	// Deserialisation
				Objets objet_java = gson.fromJson(objet.toJson(),Objets.class);
				// Ajout de l'objet dans la liste des objets
				listObjets.add(objet_java);
		    }
		} finally {
		    cursor.close();
		}

		// Serialisation
		String resp = gson.toJson(listObjets);
		
		mongo.Deconnexion();
		return Response.status(200).entity(resp).build();		
	}
	
	/**
	 * GET d'un seul objet à partir de son id
	 *
	 * @param  
	 * @throws 
	 * @return
	 */
	@GET  
	@Path("/{idobjet}")
	@Produces("application/json")
	public Response GetObjectbyId(@PathParam("idobjet") final String idobjet){
		
		// Initialisation
        //gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();

		Gson gson = new Gson();
		String objets_response;
		Objets objet = new Objets();
		
		// Connexion à la base de donnée
		MongoDB mongo = new MongoDB("test", "objets"); // db et collection
		
		// Selection de l'objet en fonction de l'id en paramètre
		Document myDoc = mongo.SelectionParId(idobjet);
		
		// Deserialization
		objet = gson.fromJson(myDoc.toJson(),Objets.class);

		// Serialization
		if (objet.getDisp_annee() == null) {
			// Si la date est à afficher telle quelle
			objets_response = gson.toJson(objet);
		}
		else {
			// Si la date est à afficher est un siècle
			ObjetsBis objetbis = new ObjetsBis(objet);
			objets_response = gson.toJson(objetbis);
		}
		
		// Deconnexion
		mongo.Deconnexion();
		
		return Response.status(200).entity(objets_response).build();	
	}
	
	/**
	 * GET de recherche d'un objet
	 *
	 * @param nom : nom de l'objet
	 * 		  amin : annee minimum
	 * 		  amax : annee maximum
	 * @return liste d'objet repondant aux critère
	 */
	@GET
	@Path("/recherche")
	@Produces("application/json")
	public Response GetRechercheObjet(@QueryParam("nom") String nom,
									  @QueryParam("amin") int amin,
									  @QueryParam("amax") int amax){
		
		LOGGER.info(nom);
		LOGGER.info(Integer.toString(amin));
		LOGGER.info(Integer.toString(amax));
		
		// Initialisation
		Gson gson = new Gson();
		List<ObjetReturn> listObjets = new ArrayList<ObjetReturn>();
		MongoCursor<Document> cursor;
		
		// Connexion à la base de donnée
		MongoDB mongo = new MongoDB(Config.DB, Config.OBJETS); // db et collection
		
		// Obtention de tous les objets
		 cursor = mongo.Recherche(nom, amin, amax);

		// Boucle sur tous les objets
		try {
		    while (cursor.hasNext()) {
		    	Document objet = cursor.next(); // Deplacement du curseur
		    	// Deserialisation
				Objets objet_java = gson.fromJson(objet.toJson(),Objets.class);
				// Ajout de l'objet dans la liste des objets
				
				ObjetReturn obj_return = new ObjetReturn();
				
				obj_return.setId(objet_java.get_id().get$oid());
				obj_return.setNom(objet_java.getNom());
				obj_return.setAnnee(objet_java.getAnnee());
				obj_return.setCover(objet_java.getImgs().get(0).getSrc());
				
				listObjets.add(obj_return);
		    }
		} finally {
		    cursor.close();
		}

		// Serialisation
		String resp = gson.toJson(listObjets);
		
		mongo.Deconnexion();
		return Response.status(200).entity(resp).build();		
	}

	
	
}
