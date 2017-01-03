package com.sossgrid.datastore;

public class StatusMessage {
	private boolean error;
	private String message;
	private	Object	recievedObject;
	
	public StatusMessage(Boolean Error,String Message,Object RvedObj){
		setError(Error);
		setMessage(Message);
		setRecievedObject(RvedObj);
	}

	public boolean isError() {
		return error;
	}

	public void setError(boolean error) {
		this.error = error;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Object getRecievedObject() {
		return recievedObject;
	}

	public void setRecievedObject(Object recievedObject) {
		this.recievedObject = recievedObject;
	}
	
	
	

}
