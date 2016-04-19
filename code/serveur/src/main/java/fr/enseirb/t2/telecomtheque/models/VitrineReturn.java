package fr.enseirb.t2.telecomtheque.models;

import java.util.List;

public class VitrineReturn {
	
	private String id;
	private String nom;
	private int nb_obj;
	private String cover;
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
