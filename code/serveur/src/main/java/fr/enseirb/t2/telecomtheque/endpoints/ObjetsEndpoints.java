package fr.enseirb.t2.telecomtheque.endpoints;

import static com.mongodb.client.model.Filters.eq;

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
import org.bson.types.ObjectId;

import com.google.gson.Gson;
import com.mongodb.client.MongoCursor;

import fr.enseirb.t2.telecomtheque.config.Config;
import fr.enseirb.t2.telecomtheque.models.MinMaxAnnees;
import fr.enseirb.t2.telecomtheque.models.ObjetReturn;
import fr.enseirb.t2.telecomtheque.models.ObjetReturnBis;
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
	 * @api {get} /objets Retourne tous les objets
	 * @apiVersion 1.0.0
	 * @apiName GetObjets
	 * @apiGroup Objets
	 *
	 * @apiDescription Retourne tous les objets de la collection "Objets" de la base de données
	 *
	 * @apiExample Exemple :
	 * curl -i http://tgourdel.rtrinity.enseirb-matmeca.fr/api/objets
	 *
	 * @apiSuccess {String}   response      Json contenant les objets.
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
	 * @api {get} /recherche Retourne objet selon son ID
	 * @apiVersion 1.0.0
	 * @apiName GetObjectbyId
	 * @apiGroup Objets
	 *
	 * @apiDescription Retourne un seul objet selon son ID.
	 *
	 *
	 * @apiParam {String} id       ID de l'objet.
	 * 
	 * @apiExample Exemple :
	 * curl -i http://tgourdel.rtrinity.enseirb-matmeca.fr/api/objets/recherche
	 *
	 * @apiSuccess {String}   response      Json contenant la liste d'objets filtrés.
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
		boolean exist;

		// Connexion à la base de donnée
		MongoDB mongo = new MongoDB(Config.DB, Config.OBJETS); // db et collection
		
		//Vérifie l'existence de l'objet en fonction de l'id en paramètre
		exist = mongo.TestExistenceDocument(idobjet);
		
		if(!exist)
			return Response.status(404).entity("Objet introuvable").build();

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
	 * @api {get} /recherche Recherche d'objets
	 * @apiVersion 1.0.0
	 * @apiName GetRechercheObjet
	 * @apiGroup Objets
	 *
	 * @apiDescription Retourne une liste d'objets selon les filtres choisis.
	 *
	 *
	 * @apiParam {String} nom        Nom de l'objet.
	 * @apiParam {String} amin       Année minimum.
	 * @apiParam {String} amax       Année maximum
	 * 
	 * @apiExample Exemple :
	 * curl -i http://tgourdel.rtrinity.enseirb-matmeca.fr/api/objets/recherche
	 *
	 * @apiSuccess {String}   response      Json contenant la liste d'objets filtrés.
	 */
	@GET
	@Path("/recherche")
	@Produces("application/json")
	public Response GetRechercheObjet(@QueryParam("nom") String nom,
			@QueryParam("amin") int amin,
			@QueryParam("amax") int amax){

		// Initialisation
		Gson gson = new Gson();
		List<Object> listObjets = new ArrayList<Object>();
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

				// Serialization
				if (objet_java.getDisp_annee() == null) {
					// Si la date est à afficher telle quelle
					ObjetReturn obj_return = new ObjetReturn();
					obj_return.setId(objet_java.get_id().get$oid());
					obj_return.setNom(objet_java.getNom());
					obj_return.setAnnee(objet_java.getAnnee());
					obj_return.setCover(objet_java.getImgs().get(0).getSrc());

					listObjets.add(obj_return);
				}
				else {
					// Si la date est à afficher est un siècle
					ObjetReturnBis obj_return_bis = new ObjetReturnBis(objet_java);

					listObjets.add(obj_return_bis);
				}
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
	 * @api {get} /dates Retourne les dates limites
	 * @apiVersion 1.0.0
	 * @apiName GetRangeAnnes
	 * @apiGroup Objets
	 *
	 * @apiDescription Retourne un document json contenant l'année minimal et l'année maximal des objets.
	 *
	 * 
	 * @apiExample Exemple :
	 * curl -i http://tgourdel.rtrinity.enseirb-matmeca.fr/api/objets/dates
	 *
	 * @apiSuccess {String}   response      Json contenant deux champs : amin et amax
	 */
	@GET
	@Path("/dates")
	@Produces("application/json")
	public Response GetRangeAnnes(){

		// Initialisation
		Gson gson = new Gson();
		Gson gson2 = new Gson();

		MinMaxAnnees dates = new MinMaxAnnees();

		// Connexion à la base de donnée
		MongoDB mongo = new MongoDB(Config.DB, Config.OBJETS); // db et collection

		// Cherche les années minimum et maximums
		int amin = gson.fromJson(mongo.MinAnnee().toJson(),Objets.class).getAnnee();
		int amax = gson2.fromJson(mongo.MaxAnnee().toJson(),Objets.class).getAnnee();

		// Rempli l'objet à retourner
		dates.setAmin(amin);
		dates.setAmax(amax);

		// Serialisation
		String resp = gson.toJson(dates);

		mongo.Deconnexion();
		return Response.status(200).entity(resp).build();		
	}

	/**
	 * GET existence or not of an object from an id
	 *
	 **/
	@GET
	@Path("/test/{idobjet}")
	@Produces("text/plain")
	public Response TestObjet(@PathParam("idobjet") final String idobjet){

		// Initialisation
		boolean exist;

		// Connexion à la base de donnée
		MongoDB mongo = new MongoDB(Config.DB, Config.OBJETS); // db et collection

		//Vérifie l'existence de l'objet en fonction de l'id en paramètre
		exist = mongo.TestExistenceDocument(idobjet);
		
		if(exist)
			return Response.status(200).entity("true").build();
		else
			return Response.status(404).entity("false").build();
				
	}

}
