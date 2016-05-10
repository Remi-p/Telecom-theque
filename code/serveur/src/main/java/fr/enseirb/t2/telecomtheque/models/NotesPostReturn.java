package fr.enseirb.t2.telecomtheque.models;

public class NotesPostReturn {
	private double moy;
	private String msg;
	
	
	
	public NotesPostReturn(double moy, String msg) {
		super();
		this.moy = moy;
		this.msg = msg;
	}
	public double getMoy() {
		return moy;
	}
	public void setMoy(double moy) {
		this.moy = moy;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	
	
}
