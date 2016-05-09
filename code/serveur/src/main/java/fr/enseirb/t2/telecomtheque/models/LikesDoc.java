package fr.enseirb.t2.telecomtheque.models;

import java.util.List;

public class LikesDoc {
	private String objet;
	private List<String> uuid;
	
	public String getObjet() {
		return objet;
	}
	
	public void setObjet(String objet) {
		this.objet = objet;
	}
	
	public List<String> getUuid() {
		return uuid;
	}
	
	public void setUuid(List<String> uuid) {
		this.uuid = uuid;
	}
	
}
