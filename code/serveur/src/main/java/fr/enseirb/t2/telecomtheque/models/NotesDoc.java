package fr.enseirb.t2.telecomtheque.models;

import java.util.List;

public class NotesDoc {
	private String objet;
	private List<Votes> notes;
	
	public String getObjet() {
		return objet;
	}
	
	public void setObjet(String objet) {
		this.objet = objet;
	}

	public List<Votes> getNotes() {
		return notes;
	}

	public void setNotes(List<Votes> notes) {
		this.notes = notes;
	}
	

	
}
