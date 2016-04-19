package fr.enseirb.t2.telecomtheque.models;

import java.util.ArrayList;
import java.util.List;

public class ListObjets {
	
	private List<Objets> objets;

	public List<Objets> getObjets() {
		return objets;
	}

	public void setObjets(List<Objets> objets) {
		this.objets = objets;
	}

	public ListObjets() {
		this.objets = new ArrayList<Objets>();
	}

}
