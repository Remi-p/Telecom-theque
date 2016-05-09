package fr.enseirb.t2.telecomtheque.models;

public class RequestError {
	
	private String error;
	
	public RequestError(String error) {
		this.error = error;
	}

	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
	}

}
