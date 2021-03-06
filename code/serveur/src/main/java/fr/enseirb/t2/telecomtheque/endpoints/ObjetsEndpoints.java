package fr.enseirb.t2.telecomtheque.endpoints;

import static com.mongodb.client.model.Filters.eq;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Formatter;
import java.util.List;
import java.util.logging.Logger;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
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
import fr.enseirb.t2.telecomtheque.models.Existence;
import fr.enseirb.t2.telecomtheque.models.LikesDoc;
import fr.enseirb.t2.telecomtheque.models.LikesNotes;
import fr.enseirb.t2.telecomtheque.models.LikesPost;
import fr.enseirb.t2.telecomtheque.models.MinMaxAnnees;
import fr.enseirb.t2.telecomtheque.models.NotesDoc;
import fr.enseirb.t2.telecomtheque.models.NotesPost;
import fr.enseirb.t2.telecomtheque.models.NotesPostReturn;
import fr.enseirb.t2.telecomtheque.models.ObjetReturn;
import fr.enseirb.t2.telecomtheque.models.ObjetReturnBis;
import fr.enseirb.t2.telecomtheque.models.Objets;
import fr.enseirb.t2.telecomtheque.models.ObjetsBis;
import fr.enseirb.t2.telecomtheque.models.RequestError;
import fr.enseirb.t2.telecomtheque.models.Votes;
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
	 * @apiDescription Retourne tous les objets de la collection "Objets" de la base de données.
	 *
	 * @apiExample Exemple :
	 * curl -i http://tgourdel.rtrinity.enseirb-matmeca.fr/api/objets
	 *
	 * @apiSuccess {String}   response  Json contenant les objets.
	 * @apiSuccessExample Success-Response:
	 *     HTTP/1.1 200 OK
	 *		{
	 *			"_id": {
	 *			"$oid": "57169ee995e5008a634be22c"
	 *		},
	 *		"annee": 1926,
	 *		"nom": "Caméra HERNEMAN",
	 *		"description": "Caméra allemande pour prise de vue de film 35 mm. Manivelle avec poignée en bois. Poignée plate en cuir fixée à deux rivets sur le dessus. Niveau sphérique à bulle vissée sur le dessus. Sur le côté droit, un compteur (de 1 a 60) avec étiquette collée ciné spot. Près de la manivelle, une plaque en métal.",
	 *		"imgs": [
	 *			{
	 *			"src": "http://www.culture.gouv.fr/Wave/image/joconde/0675/m081633_2-apv-3-2_p.jpg"
	 *			}
	 *	]
	 *}
	 *
	 * @apiError UserNotFound L'objet n'a pas été trouvé dans la base de données.
	 *
	 * @apiErrorExample Error-Response:
	 *     HTTP/1.1 404 Not Found
	 *{
	 *	"error": "Objet introuvable"
	 *}
	 */
	@GET
	@Produces("application/json")
	public Response GetObjets(){

		LOGGER.info("GET de tous les objets");
		
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
	 * @api {get} /objets/:idobjet Retourne objet selon son ID
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
	 * curl -i http://tgourdel.rtrinity.enseirb-matmeca.fr/api/objets/57169ee995e5008a634be22c
	 *
	 * @apiSuccess {String}   response    Json contenant la liste d'objets filtrés.
	 * 
	 * @apiSuccessExample Success-Response:
	 *     HTTP/1.1 200 OK
	 *
	 * @apiError Not Found L'objet n'a pas été trouvé dans la base de données.
	 *
	 * @apiErrorExample Error-Response:
	 *     HTTP/1.1 404 Not Found
	 *{
	 *	"error": "Objet introuvable"
	 *}
	 */
	@GET  
	@Path("/{idobjet}")
	@Produces("application/json")
	public Response GetObjectbyId(@PathParam("idobjet") final String idobjet){
		
		LOGGER.info("GET de l'objet : "+idobjet);
		
		// Initialisation
		Gson gson = new Gson();
		String objets_response;
		Objets objet = new Objets();
		boolean exist;

		// Connexion à la base de donnée
		MongoDB mongo = new MongoDB(Config.DB, Config.OBJETS); // db et collection
		
		//Vérifie l'existence de l'objet en fonction de l'id en paramètre
		exist = mongo.TestExistenceDocument(idobjet);
		
		if(!exist) {
			LOGGER.severe("Objet : "+idobjet+" introuvable");
			RequestError error = new RequestError("Objet introuvable");
			return Response.status(404).entity(gson.toJson(error)).build();
		}

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
	 * @api {get} /objets/recherche Recherche d'objets
	 * @apiVersion 1.0.0
	 * @apiName GetRechercheObjet
	 * @apiGroup Objets
	 *
	 * @apiDescription Retourne une liste d'objets selon les filtres choisis tel que le nom de l'objet, et une période.
	 *
	 *
	 * @apiParam {String} nom        Nom de l'objet.
	 * @apiParam {String} amin       Année minimum.
	 * @apiParam {String} amax       Année maximum
	 * 
	 * @apiExample Exemple :
	 * curl -i http://tgourdel.rtrinity.enseirb-matmeca.fr/api/objets/recherche?nom=projecteur&amin=1900&amax=1975
	 *
	 * @apiSuccess {String}   response      Json contenant la liste d'objets filtrés.
	 * 
	 * @apiSuccessExample Success-Response:
	 *     HTTP/1.1 200 OK
	 *[
	 *	{
	 * 		"id": "57169f1395e5008a634be22e",
	 * 		"nom": "Projecteur radiocinéma 35mm 82301 PHILIPS",
	 *		"annee": "20ème siècle",
	 *		"cover": "http://www.culture.gouv.fr/Wave/image/joconde/0884/m103989_017600_p.jpg"
	 *	}
	 *]
	 */
	@GET
	@Path("/recherche")
	@Produces("application/json")
	public Response GetRechercheObjet(@QueryParam("nom") String nom,
			@QueryParam("amin") int amin,
			@QueryParam("amax") int amax){

		LOGGER.info("Recherche d'objet");

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
	 * @api {get} /objets/dates Retourne les dates limites
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
	 * 
	 * @apiSuccessExample Success-Response:
	 *     HTTP/1.1 200 OK
	 * {
   	 *	"amin": 1900,
  	 * 	"amax": 1990
	 * }
	 *
	 * @apiError Error Aucun objet dans la base de données.
	 *
	 * @apiErrorExample Error-Response:
	 *     HTTP/1.1 404 Not Found
	 *{
	 *	"error": "Aucun objet disponible"
	 * }
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

		if(mongo.CountDocuments() == 0) {
			RequestError error = new RequestError("Aucun objet disponible");
			return Response.status(404).entity(gson.toJson(error)).build();
		}
		
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
	 * @api {get} /objets/test/:idobjet Test l'existence d'un objet
	 * @apiVersion 1.0.0
	 * @apiName TestObjet
	 * @apiGroup Objets
	 *
	 * @apiDescription Retourne un boolean déterminant l'existence ou non d'un objet en fonction de son ID.
	 *
	 * @apiExample Exemple :
	 * curl -i http://tgourdel.rtrinity.enseirb-matmeca.fr/api/objets/test/57169ee995e5008a634be22c
	 *
	 * @apiSuccess {String}   response      Json contenant un champ existence marqué à true ou false
	 * @apiSuccessExample Success-Response:
	 *     HTTP/1.1 200 OK
	 *{
   	 *	"existence": "false",
	 *}
	 */
	@GET
	@Path("/test/{idobjet}")
	@Produces("application/json")
	public Response TestObjet(@PathParam("idobjet") final String idobjet){

		// Initialisation
		boolean exist;
		Gson gson = new Gson();

		// Connexion à la base de donnée
		MongoDB mongo = new MongoDB(Config.DB, Config.OBJETS); // db et collection

		//Vérifie l'existence de l'objet en fonction de l'id en paramètre
		exist = mongo.TestExistenceDocument(idobjet);
		
		Existence existence = new Existence();
		
		if(exist) {
			existence.setExistence("true");
			return Response.status(200).entity(gson.toJson(existence)).build();
		}
		else {
			existence.setExistence("false");
			return Response.status(404).entity(gson.toJson(existence)).build();
		}
				
	}
	
	/**
	 * @api {get} /objets/meta/:idobjet/:uuid Information des likes et des votes
	 * @apiVersion 1.0.0
	 * @apiName GetLikes
	 * @apiGroup Objets
	 *
	 * @apiDescription  Retourne un document JSON contenant les informations suivantes :
	 * 					si l'utilisateur a aimé ou voté pour l'objet,
	 * 					le nombre de vote, la moyenne et le nombre de like.
	 *
	 * @apiExample Exemple :
	 * curl -i http://tgourdel.rtrinity.enseirb-matmeca.fr/api/objets/meta/57169ee995e5008a634be22c/uuid
	 *
	 * @apiSuccess {String}   response      Json contenant les champs relatifs aux votes ainsi qu'aux likes.
	 * @apiSuccessExample Success-Response:
	 *     HTTP/1.1 200 OK
	 *{
	 *  "like": "false",
	 *  "vote": "false",
	 *  "nb": 4,
	 *  "moy": 2.9,
	 *  "nbv": 8,
	 *  "note": 0
	 *}
	 *
	 */
	@GET
	@Path("/meta/{idobjet}/{uuid}")
	@Produces("application/json")
	public Response GetLikes(@PathParam("uuid") final String uuid, @PathParam("idobjet") final String idobjet){
		
		// Initialisation
		String resp;
		boolean exist_likes;
		boolean exist_notes;
		Gson gson = new Gson();
		LikesNotes likesnotes = new LikesNotes();
		LikesDoc likesdoc = new LikesDoc();
		NotesDoc notesdoc = new NotesDoc();
		DecimalFormat df = new DecimalFormat();
		df.setMaximumFractionDigits(1); //trois chiffre apres la virgule
		
		// Connexion à la base de donnée
		MongoDB mongo_likes = new MongoDB(Config.DB, Config.LIKES); // db et collection
		MongoDB mongo_notes = new MongoDB(Config.DB, Config.NOTES); // db et collection

		//Vérifie l'existence de l'objet en fonction de l'id en paramètre
		exist_likes = mongo_likes.TestExistenceLike(idobjet);
		exist_notes = mongo_notes.TestExistenceNote(idobjet);
		
		// Si l'objet à déjà été liké
		if(exist_likes) {
			Document myDoc = mongo_likes.Selection("objet",idobjet);
			likesdoc = gson.fromJson(myDoc.toJson(), LikesDoc.class);
			likesnotes.setLike("false");
			for(String b_uuid: likesdoc.getUuid()) {
				if(b_uuid.equals(uuid)) {
					likesnotes.setLike("true");
				}
			}
			likesnotes.setNb(likesdoc.getUuid().size());
		} // Si l'objet n'a jamais été liké
		else {
			likesnotes.setLike("false");
			likesnotes.setNb(0);
		}
		
		// Si l'objet a déjà été noté
		if(exist_notes) {
			Document myDoc = mongo_notes.Selection("objet",idobjet);
			notesdoc = gson.fromJson(myDoc.toJson(), NotesDoc.class);
			likesnotes.setVote("false");
			
			double moy = 0;
			
			for(Votes b_uuid: notesdoc.getNotes()) {
				moy = moy + b_uuid.getNote();
				if(b_uuid.getUuid().equals(uuid)) {
					likesnotes.setVote("true");
					likesnotes.setNote(b_uuid.getNote());
				}
			}
			int nbvotes = notesdoc.getNotes().size();
			
			likesnotes.setNbv(nbvotes);
			if (nbvotes != 0) 
				likesnotes.setMoy(Double.parseDouble(df.format(moy/nbvotes)));
			else
				likesnotes.setMoy(0);

		}
		else { // Si l'objet n'a jamais été noté
			likesnotes.setVote("false");
			likesnotes.setNbv(0);
		}
		
		resp = gson.toJson(likesnotes);
		
		return Response.status(200).entity(resp).build();
	}
	
	/**
	 * @api {post} /objets/likes/action Action sur les likes
	 * @apiVersion 1.0.0
	 * @apiName likeObjet
	 * @apiGroup Objets
	 *
	 * @apiDescription Ajoute ou suprime un like sur un objet par son ID et l'uuid de l'utilisateur.
	 *
	 * @apiExample Exemple :
	 * curl -i http://tgourdel.rtrinity.enseirb-matmeca.fr/api/objets/likes/action
	 *
	 * @apiSuccess {String}   response      String "Ajout" ou "Suppresion"
	 * @apiSuccessExample Success-Response:
	 *     HTTP/1.1 200 OK
	 *     "Ajout"
	 */
	@POST  
	@Path("/likes/action")
	@Consumes("application/json")
	public Response likeObjet(String jsonInput) throws Exception {

		// Initialisation
		String resp;
		boolean exist;
		Gson gson = new Gson();
		LikesDoc likesdoc = new LikesDoc();
		LikesPost likespost = new LikesPost();
		
		// Serialization
		likespost = gson.fromJson(jsonInput, LikesPost.class);
		
		// Connexion à la base de donnée
		MongoDB mongo = new MongoDB(Config.DB, Config.LIKES); // db et collection

		//Vérifie l'existence de l'objet en fonction de l'id en paramètre
		exist = mongo.TestExistenceLike(likespost.getObjet());
		
		if(exist) { // Si l'objet a déjà été liké
			Document myDoc = mongo.Selection("objet",likespost.getObjet());
			likesdoc = gson.fromJson(myDoc.toJson(), LikesDoc.class);
			
			int i;
			
			for(i=0; i<likesdoc.getUuid().size();i++) {
				if(likesdoc.getUuid().get(i).equals(likespost.getUuid())) {
					likesdoc.getUuid().remove(i);
					resp = gson.toJson(likesdoc);
					mongo.Update(likespost.getObjet(), Document.parse(resp));
					
					LOGGER.info("Suppression d'un like de l'objet d'ID "+likesdoc.getObjet());

					return Response.status(200).entity("Suppression").build();
				}
			}
			
			likesdoc.getUuid().add(likespost.getUuid());
			
			resp = gson.toJson(likesdoc);
			
			mongo.Update(likespost.getObjet(), Document.parse(resp));
			LOGGER.info("Ajout d'un like sur l'objet d'ID "+likesdoc.getObjet());

			return Response.status(200).entity("Ajout").build();
		}
		else { // Si l'objet n'a jamais été liké on l'ajoute à la BDD
			LikesDoc likesdoc2 = new LikesDoc();
			
			likesdoc2.setObjet(likespost.getObjet());
			likesdoc2.setUuid(new ArrayList<String>());
			likesdoc2.getUuid().add(likespost.getUuid());
			
			resp = gson.toJson(likesdoc2);
			
			mongo.Insert(Document.parse(resp));
			LOGGER.info("Ajout d'un like sur l'objet d'ID "+likesdoc2.getObjet());
			return Response.status(200).entity("Ajout").build();
		}
	}
		
		/**
		 * @api {post} /objets/notes/action Action de vote sur un objet
		 * @apiVersion 1.0.0
		 * @apiName addNotes
		 * @apiGroup Objets
		 *
		 * @apiDescription Ajoute ou supprime une note à l'objet en étant identifié par l'uuid du périphérique.
		 *
		 * @apiExample Exemple :
		 * curl -i http://tgourdel.rtrinity.enseirb-matmeca.fr/api/objets/notes/action
		 * @apiSuccessExample Success-Response:
		 *     HTTP/1.1 200 OK
		 *{
		 *	"moy": 2.9,
		 *	"msg": "Suppression"
		 *}
		 */
		@POST  
		@Path("/notes/action")
		@Consumes("application/json")
		@Produces("application/json")
		public Response addNotes(String jsonInput) throws Exception {

			// Initialisation
			String resp;
			boolean exist;
			boolean change = false;
			Gson gson = new Gson();
			NotesDoc notesdoc = new NotesDoc();
			NotesPost notespost = new NotesPost();
			DecimalFormat df = new DecimalFormat();
			df.setMaximumFractionDigits(1); // Affichage de la moyenne des votes
											// Un chiffre après la virgule

			// Serialization
			notespost = gson.fromJson(jsonInput, NotesPost.class);
			
			// Connexion à la base de donnée
			MongoDB mongo = new MongoDB(Config.DB, Config.NOTES); // db et collection

			//Vérifie l'existence de l'objet en fonction de l'id en paramètre
			exist = mongo.TestExistenceNote(notespost.getObjet());
			
			if(exist) { // Si l'objet a déjà été noté
				Document myDoc = mongo.Selection("objet",notespost.getObjet());
				notesdoc = gson.fromJson(myDoc.toJson(), NotesDoc.class);
				
				int i;
				double moy = 0;
				
				for(i=0; i<notesdoc.getNotes().size();i++) {
					// si l'utilisateur modifie sa note
					if(notesdoc.getNotes().get(i).getUuid().equals(notespost.getUuid())) {
						notesdoc.getNotes().remove(i);
						resp = gson.toJson(notesdoc);
						mongo.Update(notespost.getObjet(), Document.parse(resp));
						LOGGER.info("Suppresion d'un vote sur l'objet d'iD "+notesdoc.getObjet());
						change = true;
					}
					else {
						moy = moy + notesdoc.getNotes().get(i).getNote();
					}
				}
				
				int oldsize = notesdoc.getNotes().size();
				
				if(change) {
						if(oldsize == 0)
							moy = 0;
						else
							moy = moy/oldsize;
						moy = Double.parseDouble(df.format(moy));

					return Response.status(200).entity(gson.toJson(new NotesPostReturn(moy,"Suppression"))).build();
				}
				
				notesdoc.getNotes().add(new Votes(notespost.getUuid(), notespost.getNote()));
				
				resp = gson.toJson(notesdoc);
				
				mongo.Update(notespost.getObjet(), Document.parse(resp));
				LOGGER.info("Ajout d'un like sur l'objet d'ID "+notesdoc.getObjet());
				
				moy = 0;
				for(i=0; i<notesdoc.getNotes().size();i++) {
						moy = moy + notesdoc.getNotes().get(i).getNote();
				}
				moy = moy/notesdoc.getNotes().size();
				moy = Double.parseDouble(df.format(moy));
				
				return Response.status(200).entity(gson.toJson(new NotesPostReturn(moy,"Ajout"))).build();
			}
			else { // Si l'objet n'a jamais été noté on l'ajoute à la BDD
				NotesDoc notesdoc2 = new NotesDoc();
				
				notesdoc2.setObjet(notespost.getObjet());
				notesdoc2.setNotes(new ArrayList<Votes>());
				notesdoc2.getNotes().add(new Votes(notespost.getUuid(), notespost.getNote()));
				
				resp = gson.toJson(notesdoc2);
				
				mongo.Insert(Document.parse(resp));
				LOGGER.info("Ajout d'un vote sur l'objet d'ID "+notesdoc2.getObjet());
				return Response.status(200).entity(gson.toJson(new NotesPostReturn(notespost.getNote(),"Ajout"))).build();
			}
	}

}
