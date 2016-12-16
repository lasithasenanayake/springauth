package com.sossgrid.datastore;

import java.util.HashMap;

import com.sossgrid.datastore.SOSStore;

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
			// TODO Auto-generated catch block
			dataStore=null;
			throw e;
			//e.printStackTrace();
		}
	}
	
	public DataResponse Process(){
		DataResponse outData = new DataResponse("Greetings!!!");
		DataCommand dataCommand  = this.request.getDataCommand();
		
		try {
			switch (dataCommand.getOperation()){
				case CreateSchema:
					break;
				case DeleteSchema:
					break;
				case GetSchema:
					break;
				case UpdateSchema:
					 break;
				case Get:
					Object queryData =this.dataStore.Retrive(this.request, dataCommand.getClassObject());
					outData.setResponse(queryData);
					break;
				case Insert:
					this.dataStore.Store(request, StoreOperation.InsertRecord);
					break;
				case Update:
					break;
				case Store:
					//datastore.Store(Name, Objs, schema)
					break;
				case Delete:
					break;
			}
			
			if (request.isError())
				outData.setError(request.getError());
			
		} catch (Exception e) {
			outData.setError(e);
		}
			
		return outData;
	}
	
	public static DataResponse SendError (String errorMessage){
		DataResponse outData = new DataResponse(errorMessage);
		outData.setSuccess(false);
		return outData;
	}
	
	public static DataResponse SendError (Exception ex){
		DataResponse outData = new DataResponse(ex.getMessage());
		outData.setSuccess(false);
		return outData;
	}
	
}
