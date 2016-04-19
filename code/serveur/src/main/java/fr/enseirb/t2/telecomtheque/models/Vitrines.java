package fr.enseirb.t2.telecomtheque.models;

import java.util.List;

public class Vitrines {

	private _id _id;
	private String nom;
	private List<String> objets;
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
	public List<String> getObjets() {
		return objets;
	}
	public void setObjets(List<String> objets) {
		this.objets = objets;
	}
	
}
