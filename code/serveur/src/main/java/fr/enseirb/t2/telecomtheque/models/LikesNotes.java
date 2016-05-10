package fr.enseirb.t2.telecomtheque.models;

public class LikesNotes {
	private String like;
	private String vote;
	private long nb;
	private double moy;
	private long nbv;
	private int note;
	
	
	public int getNote() {
		return note;
	}
	public void setNote(int note) {
		this.note = note;
	}
	public long getNbv() {
		return nbv;
	}
	public void setNbv(long nbv) {
		this.nbv = nbv;
	}
	public String getVote() {
		return vote;
	}
	public void setVote(String vote) {
		this.vote = vote;
	}
	public double getMoy() {
		return moy;
	}
	public void setMoy(double moy) {
		this.moy = moy;
	}
	public String getLike() {
		return like;
	}
	public void setLike(String like) {
		this.like = like;
	}
	public long getNb() {
		return nb;
	}
	public void setNb(long nb) {
		this.nb = nb;
	}

}
