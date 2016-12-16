package com.sossgrid.datacore;

import java.util.HashMap;
import java.util.ArrayList;

public class RequestInfo {
	private DataCommand dataCommand;
	private ArrayList<String> logs;
	
	public RequestInfo (DataCommand dataCommand){
		this.dataCommand = dataCommand;
		this.logs = new ArrayList<>();
	}

	public DataCommand getDataCommand() {
		return dataCommand;
	}

	public void LogLine(String logLine){
		logs.add(logLine);
	}

}
