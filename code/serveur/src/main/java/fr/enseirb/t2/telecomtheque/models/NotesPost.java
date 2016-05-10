package fr.enseirb.t2.telecomtheque.models;

import java.util.List;

public class NotesPost {
	private String objet;
	private String uuid;
	private int note;
	
	public int getNote() {
		return note;
	}
	public void setNote(int note) {
		this.note = note;
	}
	public String getObjet() {
		return objet;
	}
	public void setObjet(String objet) {
		this.objet = objet;
	}
	public String getUuid() {
		return uuid;
	}
	public void setUuid(String uuid) {
		this.uuid = uuid;
	}
	
	
}