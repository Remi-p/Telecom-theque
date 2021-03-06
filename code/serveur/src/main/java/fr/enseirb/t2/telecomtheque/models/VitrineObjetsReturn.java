package fr.enseirb.t2.telecomtheque.models;

import java.util.ArrayList;
import java.util.List;

public class VitrineObjetsReturn {
	
	private String vitrine;
	private String nom;
	private List<Object> objets;
	public String getVitrine() {
		return vitrine;
	}
	public void setVitrine(String vitrine) {
		this.vitrine = vitrine;
	}
	public String getNom() {
		return nom;
	}
	public void setNom(String nom) {
		this.nom = nom;
	}
	public List<Object> getObjets() {
		return objets;
	}
	public void setObjets(List<Object> objets) {
		this.objets = objets;
	}
	
	public VitrineObjetsReturn() {
		this.objets = new ArrayList<Object>();
	}
}
