package fr.enseirb.t2.telecomtheque.models;

public class Votes {
	private String uuid;
	private int note;
	
	public Votes(String uuid, int note) {
		super();
		this.uuid = uuid;
		this.note = note;
	}
	public String getUuid() {
		return uuid;
	}
	public void setUuid(String uuid) {
		this.uuid = uuid;
	}
	public int getNote() {
		return note;
	}
	public void setNote(int note) {
		this.note = note;
	}
	
	

}
