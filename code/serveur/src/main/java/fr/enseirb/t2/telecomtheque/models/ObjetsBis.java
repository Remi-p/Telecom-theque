package fr.enseirb.t2.telecomtheque.models;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.annotations.SerializedName;

public class ObjetsBis {

	private _id _id;
	private String annee;
    private String nom;
    private String description;
    private List<Src> imgs = new ArrayList<Src>();
    
    
	public ObjetsBis(Objets objets) {
		super();
		this._id = objets.get_id();
		this.annee = objets.getDisp_annee();
		this.nom = objets.getNom();
		this.description = objets.getDescription();
		this.imgs = objets.getImgs();
	}
	public String getAnnee() {
		return annee;
	}
	public void setAnnee(String annee) {
		this.annee = annee;
	}
	public List<Src> getImgs() {
		return imgs;
	}
	public void setImgs(List<Src> imgs) {
		this.imgs = imgs;
	}

	public _id get_id() {
		return _id;
	}
	public void set_id(_id _id) {
		this._id = _id;
	}


	public String getNom() {
		return nom;
	}
	public void setNom(String nom) {
		this.nom = nom;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
}
