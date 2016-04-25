package fr.enseirb.t2.telecomtheque.models;

import java.util.ArrayList;
import java.util.List;


public class Objets {

	// Expose fields to manage fiels in serialization or not
	
	private _id _id;
	private int annee;
	private String nom;
	private String description;
	private List<Src> imgs = new ArrayList<Src>();
    private String disp_annee;
    
	public Objets() {
		
	}
	
	public String getDisp_annee() {
		return disp_annee;
	}
	public void setDisp_annee(String disp_annee) {
		this.disp_annee = disp_annee;
	}
	public List<Src> getImgs() {
		return imgs;
	}
	public void setImgs(List<Src> imgs) {
		this.imgs = imgs;
	}
	public void setAnnee(int annee) {
		this.annee = annee;
	}
	public _id get_id() {
		return _id;
	}
	public void set_id(_id _id) {
		this._id = _id;
	}
	public int getAnnee() {
		return annee;
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
