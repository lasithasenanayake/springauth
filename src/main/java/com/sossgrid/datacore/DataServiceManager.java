package com.sossgrid.datacore;

public class DataServiceManager {

	public static DataResponse Process(RequestInfo info){
		DataResponse outData = new DataResponse("Greetings!!!");
			
		return outData;
	}
	
	public static DataResponse SendError (String errorMessage){
		DataResponse outData = new DataResponse(errorMessage);
		outData.setSuccess(false);
		return outData;
	}
}
