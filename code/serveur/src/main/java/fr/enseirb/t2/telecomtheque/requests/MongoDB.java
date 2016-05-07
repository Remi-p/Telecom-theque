package fr.enseirb.t2.telecomtheque.requests;

import static com.mongodb.client.model.Filters.*;

import java.util.Arrays;
import java.util.Iterator;
import java.util.logging.Logger;

import org.bson.BsonDocument;
import org.bson.BsonString;
import org.bson.Document;
import org.bson.types.ObjectId;

import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Projections;

public class MongoDB {

	// get the global logger
	Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

	private MongoClient client;
	private MongoCollection<Document> collection;

	/**
	 * Constructeur
	 * Connexion à une base de donnée MongoDB et à une de ses collections
	 *
	 * @param nom_db
	 * 		  nom_collection
	 */
	public MongoDB(String nom_db, String nom_collection) {

		this.client = new MongoClient();
		MongoDatabase dbase = this.client.getDatabase(nom_db);
		this.collection = dbase.getCollection(nom_collection);

	}

	/**
	 * Selection d'un document dans une collection par son id
	 *
	 * @param id : du document à séléctionner
	 * 		  
	 * @return Objet de type Document
	 */
	public Document SelectionParId(String id) {

		Document doc = this.collection.find(eq("_id", new ObjectId(id))).first();
		return doc;
	}

	/**
	 * Selection d'un document dans une collection par son id
	 *
	 * @param id : du document à séléctionner
	 * 		  
	 * @return Objet de type Document
	 */
	public boolean TestExistenceDocument(String id) {
		
		if(TestObjectID(id)) {
			long count =  this.collection.count(eq("_id", new ObjectId(id)));
			if(count>0){
				return true; 
			}
			else {
				return false;
			}
		}
		else
			return false;
	}

	/**
	 * Obtention de la liste de tous les documents d'une collection
	 *
	 * @return Objet de type MongoCursor<Document> à parcourir
	 */
	public MongoCursor<Document> ObtentionListe() {
		MongoCursor<Document> cursor = this.collection.find().iterator();
		return cursor;
	}

	/**
	 * Obtention de la liste de tous les documents d'une collection
	 * 
	 * @param nom : nom de l'objet
	 * 				amin : année minimum
	 * 				amax : année maximum
	 * @return Objet de type MongoCursor<Document> à parcourir
	 */
	public MongoCursor<Document> Recherche(String nom, int amin, int amax) {

		// Filtre les dates
		// Filtre aussi avec une regex
		// L'option i permet de ne pas prendre en compte la casse

		nom.replaceAll(" ", "[\\s]"); // to handle space

		MongoCursor<Document> cursor = this.collection.find(and(regex("nom", nom, "i"), gte("annee", amin), lte("annee", amax))).iterator();

		return cursor;
	}

	/**
	 * Retourne l'année minimum des objets
	 * 		  
	 * @return Objet de type Document
	 */
	public Document MinAnnee() {

		Document first = null;

		FindIterable<Document> min = this.collection.find()
				.limit(1)
				.sort(new Document("annee", 1));

		for (Document doc : min) {
			first = doc;
			break;
		}

		return first;

	}

	/**
	 * Retourne l'année maximum des objets
	 * 		  
	 * @return Objet de type Document
	 */
	public Document MaxAnnee() {

		Document first = null;

		FindIterable<Document> max = this.collection.find()
				.limit(1)
				.sort(new Document("annee", -1));

		for (Document doc : max) {
			first = doc;
			break;
		}

		return first;

	}

	/**
	 * Deconnexion de la base de donnée
	 */
	public void Deconnexion() {
		this.client.close();
	}

	/**
	 * Test si le format d'un id est bon
	 */
	private static boolean TestObjectID(String id) {
		
		if(id.length() != 24) {
			return false;
		}
		else {
			return true;
		}
	}
	
}
