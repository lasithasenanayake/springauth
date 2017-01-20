package com.sossgrid.datacore;

public class DataResponse {
	private boolean isSuccess;
	private Object response;
	private Exception error;
	
	public DataResponse(Exception error){
		this.isSuccess = false;
		this.setError(error);
	}
	
	public DataResponse(Object obj){
		this.setSuccess(true);
		this.setResponse(obj);
	}
	public boolean isSuccess() {
		return isSuccess;
	}
	public void setSuccess(boolean isSuccess) {
		this.isSuccess = isSuccess;
	}
	public Object getResponse() {
		return response;
	}
	public void setResponse(Object response) {
		this.response = response;
	}

	public Exception getError() {
		return error;
	}

	public void setError(Exception error) {
		this.error = error;
	}
	
}
