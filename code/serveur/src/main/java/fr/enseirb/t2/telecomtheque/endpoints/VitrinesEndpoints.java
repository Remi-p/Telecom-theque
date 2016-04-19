package fr.enseirb.t2.telecomtheque.endpoints;

import static com.mongodb.client.model.Filters.eq;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import com.google.gson.Gson;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;

import fr.enseirb.t2.telecomtheque.models.ListVitrines;
import fr.enseirb.t2.telecomtheque.models.ObjetReturn;
import fr.enseirb.t2.telecomtheque.models.Objets;
import fr.enseirb.t2.telecomtheque.models.VitrineObjetsReturn;
import fr.enseirb.t2.telecomtheque.models.VitrineReturn;
import fr.enseirb.t2.telecomtheque.models.Vitrines;
import fr.enseirb.t2.telecomtheque.models._id;

import org.bson.Document;
import org.bson.types.ObjectId;

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
		
		MongoClient mongoClient = new MongoClient();
		MongoDatabase db = mongoClient.getDatabase("test");
		MongoCollection<Document> vitrines_collection = db.getCollection("vitrines");	
		MongoCollection<Document> objets_collection = db.getCollection("objets");	

		
		// De-serialization
		Gson gson = new Gson();
		
		List<VitrineReturn> listVitrinesReturn = new ArrayList<VitrineReturn>();
		MongoCursor<Document> cursor = vitrines_collection.find().iterator();
		
		// Boucle sur les vitrines
		try {
		    while (cursor.hasNext()) {
		    			    	
		    	Document vitrine = cursor.next();
		    	// Deserialization of the vitrine
				Vitrines objet_java = gson.fromJson(vitrine.toJson(),Vitrines.class);
				
				VitrineReturn vit_return = new VitrineReturn();
				vit_return.setId(objet_java.get_id().get$oid());
				vit_return.setNom(objet_java.getNom());
				vit_return.setNb_obj(objet_java.getObjets().size());
				
				Document objet_for_cover = objets_collection.find(eq("_id", new ObjectId(objet_java.getObjets().get(0)))).first();
				Objets objet = gson.fromJson(objet_for_cover.toJson(),Objets.class);
				
				vit_return.setCover(objet.getImgs().get(0).getSrc());
				
				listVitrinesReturn.add(vit_return);
		    }
		} finally {
		    cursor.close();
		}
		
		String resp = gson.toJson(listVitrinesReturn);

		return Response.status(200).entity(resp).build();		
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
	@Produces("application/json")
	public Response GetVitrine(@PathParam("idvitrine") final String idvitrine){
		
		MongoClient mongoClient = new MongoClient();
		MongoDatabase db = mongoClient.getDatabase("test");
		MongoCollection<Document> vitrines_collection = db.getCollection("vitrines");
		MongoCollection<Document> objets_collection = db.getCollection("objets");


		Document myDoc = vitrines_collection.find(eq("_id", new ObjectId(idvitrine))).first();
		String json = myDoc.toJson();
		Gson gson = new Gson();
		Vitrines vitrine = gson.fromJson(myDoc.toJson(),Vitrines.class);

		
		VitrineObjetsReturn vit_return = new VitrineObjetsReturn();
		vit_return.setVitrine(vitrine.get_id().get$oid());
		vit_return.setNom(vitrine.getNom());
		
		// Boucle sur les id des objets
		 for(String objs_id:vitrine.getObjets()) {
			
			ObjetReturn obj_return = new ObjetReturn();
			
			obj_return.setId(objs_id);
			
			Document objet_for_cover = objets_collection.find(eq("_id", new ObjectId(objs_id))).first();
			Objets objet = gson.fromJson(objet_for_cover.toJson(),Objets.class);
			
			obj_return.setNom(objet.getNom());
			obj_return.setAnnee(objet.getAnnee());
			obj_return.setCover(objet.getImgs().get(0).getSrc());
			
			vit_return.getObjets().add(obj_return);
		 }
		
		
		String vitrines_response = gson.toJson(vit_return);

		return Response.status(200).entity(vitrines_response).build();	
	}
}
