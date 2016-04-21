package fr.enseirb.t2.telecomtheque.requests;

import static com.mongodb.client.model.Filters.eq;

import org.bson.Document;
import org.bson.types.ObjectId;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;

public class MongoDB {
	
	private MongoClient client;
	private MongoCollection<Document> collection;
	
	public MongoDB(String nom_db, String nom_collection) {
		
		this.client = new MongoClient();
		MongoDatabase dbase = this.client.getDatabase(nom_db);
		this.collection = dbase.getCollection(nom_collection);

	}
	
	public Document SelectionParId(String id) {
		
		Document doc = this.collection.find(eq("_id", new ObjectId(id))).first();
		return doc;
	}
	
	public MongoCursor<Document> ObtentionListe() {
		MongoCursor<Document> cursor = this.collection.find().iterator();
		return cursor;
	}
	
	public void Deconnexion() {
		this.client.close();
	}
	
	
}
