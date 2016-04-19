package fr.enseirb.t2.telecomtheque.endpoints;

import static com.mongodb.client.model.Filters.*;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import org.bson.Document;
import org.bson.types.ObjectId;

import com.google.gson.Gson;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;

import fr.enseirb.t2.telecomtheque.models.ListObjets;
import fr.enseirb.t2.telecomtheque.models.Objets;
import fr.enseirb.t2.telecomtheque.models._id;

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
		
		MongoClient mongoClient = new MongoClient();
		MongoDatabase db = mongoClient.getDatabase("test");
		MongoCollection<Document> collection = db.getCollection("objets");	
		
		// De-serialization
		Gson gson = new Gson();
		//Document myDoc = collection.find().first();
		
		List<Objets> listObjets = new ArrayList<Objets>();
		LOGGER.info("MongoCursor");
		MongoCursor<Document> cursor = collection.find().iterator();
    	int i = 0;

		try {
		    while (cursor.hasNext()) {
		    	
		    	i = i +1;
		    	LOGGER.info(Integer.toString(i));
		    	
		    	Document objet = cursor.next();
				LOGGER.info(objet.toJson());
				Objets objet_java = gson.fromJson(objet.toJson(),Objets.class);

				LOGGER.info("Before add");
				listObjets.add(objet_java);
				LOGGER.info("After add");
		    }
		} finally {
		    cursor.close();
		}
		LOGGER.info("Serialization");

		// myDoc = collection.find().first();
		String resp = gson.toJson(listObjets);
		
		// Serialization
		//String objets_response = gson.toJson(listObjets);
		// return http code  200
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
		
		MongoClient mongoClient = new MongoClient();
		MongoDatabase db = mongoClient.getDatabase("test");
		MongoCollection<Document> collection = db.getCollection("objets");
		LOGGER.info("test1");
		
		_id id_objet = new _id();
		id_objet.set$oid(idobjet);
		
		Document myDoc = collection.find(eq("_id", new ObjectId(idobjet))).first();
		
		String json = myDoc.toJson();
		
		Gson gson = new Gson();

		Objets objet = gson.fromJson(myDoc.toJson(),Objets.class);

		String objets_response = gson.toJson(objet);

		LOGGER.info("test1");

		return Response.status(200).entity(objets_response).build();		

	}

	
}
