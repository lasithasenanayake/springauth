package com.sossgrid.datastore;

import java.util.HashMap;

import com.sossgrid.authlib.AuthCertificate;

import java.util.ArrayList;

public class DataRequest {
	private DataCommand dataCommand;
	private ArrayList<String> logs;
	private Exception error;
	
	public Exception getError(){
		return this.error;
	}
	
	public boolean isError(){
		return error !=null;
	}
	
	public void setError(Exception e){
		this.error = e;
	}
	
	public DataRequest (DataCommand dataCommand){
		this.dataCommand = dataCommand;
		this.logs = new ArrayList<>();
	}

	public DataCommand getDataCommand() {
		return dataCommand;
	}

	public void logLine(String logLine){
		logs.add(logLine);
	}

	public HashMap<String,String> getOperationParameters(){
		return this.dataCommand.getHeaders();
	}

}
