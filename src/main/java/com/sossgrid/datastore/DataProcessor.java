package com.sossgrid.datastore;

import java.util.HashMap;

import com.sossgrid.datastore.SossData;

public class DataProcessor {

	private IDataConnector dataStore;
	private DataRequest request;
		
	public DataProcessor(DataRequest request) throws Exception {
		this.request = request;
		loadDataConnectors(request.getDataCommand().getTenantId());	
	}	
	
	private void loadDataConnectors(String Domain) throws Exception{
		HashMap<String, String> conts=ResourceExtractor.GetDataStoreConfig(Domain);
		
		try {
			IDataConnector i= (IDataConnector)Class.forName(conts.get("dataadapter")).newInstance();
			i.CreateConnection(conts);
			dataStore=i;
		} catch (Exception e) {
			dataStore=null;
			throw e;
		}
	}
	
	public DataResponse Process() throws Exception {
		Object operationResponse = null;
		DataCommand dataCommand  = this.request.getDataCommand();
		
		switch (dataCommand.getOperation()){
			case GetSchema:
				operationResponse = dataCommand.getSchema();
				break;
			case Get:
				operationResponse = this.dataStore.Retrive(this.request, dataCommand.getClassObject());
				break;
			case Insert:
				operationResponse = this.dataStore.Store(request, StoreOperation.InsertRecord);
				break;
			case Update:
				operationResponse = this.dataStore.Store(request, StoreOperation.UpdateRecord);
				break;
			case Store:
				//datastore.Store(Name, Objs, schema)
				break;
			case Delete:
				break;
		}
		
		DataResponse outData;
		
		if (operationResponse !=null)
			outData = new DataResponse(operationResponse);
		else{
			outData = new DataResponse("Operation not implemented!!!");
			outData.setSuccess(false);
		}
		
		return outData;
	}
	
	
	public static DataResponse SendError (DataRequest request, Exception ex){
		DataResponse outData = new DataResponse(ex);
		outData.setSuccess(false);
		String message = ex.getLocalizedMessage() != null ? ex.getLocalizedMessage() : ex.getMessage();
		outData.setResponse(message);
		outData.setError(ex);
		return outData;
	}
	
	public static DataResponse SendError (Exception ex){
		DataResponse outData = new DataResponse(ex.getMessage());
		outData.setSuccess(false);
		String message = ex.getLocalizedMessage() != null ? ex.getLocalizedMessage() : ex.getMessage();
		outData.setResponse(message);
		outData.setError(ex);
		return outData;
	}
	
}
