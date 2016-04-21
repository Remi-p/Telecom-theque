package fr.enseirb.t2.telecomtheque.models;

import static com.mongodb.client.model.Filters.eq;

import org.bson.Document;
import org.bson.types.ObjectId;

public class VitrineReturn {
	
	private String id;
	private String nom;
	private int nb_obj;
	private String cover;
	

	public VitrineReturn(Vitrines vitrine) {
		super();
		
		this.id = vitrine.get_id().get$oid();
		this.nom = vitrine.getNom();
		this.nb_obj = vitrine.getObjets().size();
		
	}
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getNom() {
		return nom;
	}
	public void setNom(String nom) {
		this.nom = nom;
	}
	public int getNb_obj() {
		return nb_obj;
	}
	public void setNb_obj(int nb_obj) {
		this.nb_obj = nb_obj;
	}
	public String getCover() {
		return cover;
	}
	public void setCover(String cover) {
		this.cover = cover;
	}
	
	
	
}
