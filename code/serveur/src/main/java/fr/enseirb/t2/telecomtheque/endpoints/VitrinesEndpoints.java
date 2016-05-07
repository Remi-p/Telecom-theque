package fr.enseirb.t2.telecomtheque.endpoints;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import com.google.gson.Gson;
import com.mongodb.client.MongoCursor;
import fr.enseirb.t2.telecomtheque.models.ObjetReturn;
import fr.enseirb.t2.telecomtheque.models.ObjetReturnBis;
import fr.enseirb.t2.telecomtheque.models.Objets;
import fr.enseirb.t2.telecomtheque.models.VitrineObjetsReturn;
import fr.enseirb.t2.telecomtheque.models.VitrineReturn;
import fr.enseirb.t2.telecomtheque.models.Vitrines;
import fr.enseirb.t2.telecomtheque.requests.MongoDB;

import org.bson.Document;

/**
 * Gestion des endpoints relatifs aux vitrines
 */
@Path("vitrines")
public class VitrinesEndpoints {
	
    // get the global logger
    Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
    
	/**
	 * @api {get} /recherche Retourne les vitrines
	 * @apiVersion 1.0.0
	 * @apiName GetVitrines
	 * @apiGroup Vitrines
	 *
	 * @apiDescription Retourne toutes les vitrines en Json.
	 *

	 * @apiExample Exemple :
	 * curl -i http://tgourdel.rtrinity.enseirb-matmeca.fr/api/vitrines
	 *
	 * @apiSuccess {String}   response      Json contenant la liste des vitrines.
	 */
	@GET
	@Produces("application/json")
	public Response GetVitrines(){
		
		// Initialisation
		Gson gson = new Gson();
		List<VitrineReturn> listVitrinesReturn = new ArrayList<VitrineReturn>();
		MongoCursor<Document> cursor_vitrines;
		VitrineReturn vit_return; // Modèle de vitrine retourné
		
		// Connexion à la base de donnée
		MongoDB mongo_vitrines = new MongoDB("test", "vitrines"); // db et collection
		MongoDB mongo_objets = new MongoDB("test", "objets"); // db et collection

		// Recuperation de toutes les vitrines
		cursor_vitrines = mongo_vitrines.ObtentionListe();
		
		// Boucle sur les vitrines
		try {
		    while (cursor_vitrines.hasNext()) {
		    			    	
		    	Document vitrine = cursor_vitrines.next();
		    	
		    	// Deserialization of the vitrine
				Vitrines objet_java = gson.fromJson(vitrine.toJson(),Vitrines.class);
				
				// Création du nmodèle de sortie de vitrine
				vit_return = new VitrineReturn(objet_java);
					
					// Obtention du champs cover à partir du premier objet
					Document objet_for_cover = mongo_objets.SelectionParId(objet_java.getObjets().get(0));
					// Serialisation de cet objet
					Objets objet = gson.fromJson(objet_for_cover.toJson(),Objets.class);
				
				// Remplissage du cover
				vit_return.setCover(objet.getImgs().get(0).getSrc());
				
				// Ajout de la vitrine à la liste des vitrines
				listVitrinesReturn.add(vit_return);
		    }
		} finally {
		    cursor_vitrines.close();
		}
		
		// Serialisation de la liste des vitrines
		String resp = gson.toJson(listVitrinesReturn);
		
		// Deconnexion des mongo
		mongo_objets.Deconnexion();
		mongo_vitrines.Deconnexion();
		
		return Response.status(200).entity(resp).build();		
	}
	
	/**
	 * @api {get} /\{test} Retourne une vitrine selon son ID
	 * @apiVersion 1.0.0
	 * @apiName GetVitrines
	 * @apiGroup Vitrines
	 *
	 * @apiDescription Retourne une seule vitrine selon son ID.
	 *

	 * @apiExample Exemple :
	 * curl -i http://tgourdel.rtrinity.enseirb-matmeca.fr/api/vitrines/{idvitrine}
	 *
	 * @apiSuccess {String}   response      Json contenant la liste des vitrines.
	 */
	@GET  
	@Path("/{idvitrine}")
	@Produces("application/json")
	public Response GetVitrine(@PathParam("idvitrine") final String idvitrine){
		
		// Initialisation
		Gson gson = new Gson();
		Document doc_vitrine;
		Vitrines vitrine;
		VitrineObjetsReturn vit_return = new VitrineObjetsReturn();
		
		// Connexion à la base de donnée
		MongoDB mongo_vitrines = new MongoDB("test", "vitrines"); // db et collection
		MongoDB mongo_objets = new MongoDB("test", "objets"); // db et collection

		// Obtention de la vitrine en fonction de l'id en paramètre
		doc_vitrine = mongo_vitrines.SelectionParId(idvitrine);
		
		// Deserialisation de la vitrine
		vitrine = gson.fromJson(doc_vitrine.toJson(),Vitrines.class);

		// Remplissage de l'objet qui sera retourné
		// contenant les informations sur les vitrines
		// et les objets de cette vitrine
		vit_return.setVitrine(vitrine.get_id().get$oid());
		vit_return.setNom(vitrine.getNom());
		
		// Boucle sur les id des objets
		 for(String objs_id:vitrine.getObjets()) {
			
			ObjetReturn obj_return = new ObjetReturn();
			obj_return.setId(objs_id);
						
				// Obtention du champs cover à partir du premier objet
				Document objet_for_cover = mongo_objets.SelectionParId(objs_id);
				// Serialisation de cet objet
				Objets objet = gson.fromJson(objet_for_cover.toJson(),Objets.class);
				
				// Serialization
				if (objet.getDisp_annee() == null) {
					// Si la date est à afficher telle quelle
					obj_return.setNom(objet.getNom());
					obj_return.setAnnee(objet.getAnnee());
					obj_return.setCover(objet.getImgs().get(0).getSrc());
					
					vit_return.getObjets().add(obj_return);
				}
				else {
					// Si la date est à afficher est un siècle
					ObjetReturnBis obj_return_bis = new ObjetReturnBis(objet);
					
					vit_return.getObjets().add(obj_return_bis);
				}
				

				
			// Ajout de cet objet à la liste des objets

		 }
		
		// Serialisation de l'objet retourné
		String vitrines_response = gson.toJson(vit_return);

		// Deconnexion des mongos
		mongo_objets.Deconnexion();
		mongo_vitrines.Deconnexion();
		
		return Response.status(200).entity(vitrines_response).build();	
	}
}
