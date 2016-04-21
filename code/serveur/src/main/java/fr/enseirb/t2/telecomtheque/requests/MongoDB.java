package fr.enseirb.t2.telecomtheque.requests;

import static com.mongodb.client.model.Filters.*;

import org.bson.Document;
import org.bson.types.ObjectId;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;

public class MongoDB {
	
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
	 * Methode qui retourne les dates minimales et maximales des objets
	 * pour afficher le curseur de séléction lors d'une recherche
	 */
	public void DatesPourCurseurs() {
		
	}
	
	/**
	 * Deconnexion de la base de donnée
	 */
	public void Deconnexion() {
		this.client.close();
	}
	
	
}
