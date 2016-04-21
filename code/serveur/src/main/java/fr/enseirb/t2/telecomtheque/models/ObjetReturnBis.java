package fr.enseirb.t2.telecomtheque.models;

public class ObjetReturnBis {
	
	private String id;
	private String nom;
	private String annee;
	private String cover;
	
	public ObjetReturnBis(Objets objet) {
		this.id = objet.get_id().get$oid();
		this.annee = objet.getDisp_annee();
		this.nom = objet.getNom();
		this.cover = objet.getImgs().get(0).getSrc();
	}
	
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
	public String getAnnee() {
		return annee;
	}
	public void setAnnee(String annee) {
		this.annee = annee;
	}
	public String getCover() {
		return cover;
	}
	public void setCover(String cover) {
		this.cover = cover;
	}
	
	
	
}
