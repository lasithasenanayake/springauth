package com.sossgrid.datastore;

import java.util.ArrayList;
import java.util.HashMap;

import com.sossgrid.authlib.AuthCertificate;

public class SossData  {
	private String tenantId;
	
	public SossData(AuthCertificate ac)throws Exception{
		this.tenantId = ac.getDomain();
	}
	
	public SossData()throws Exception{
		this.tenantId = "sys.sossgrid.com";
	}
	
	private DataRequest getDataRequest(String className, HashMap<String,Object> body, DataOperation operation){
		DataCommand dCommand = new DataCommand();
		dCommand.setOperation(operation);
		dCommand.setClassName(className);
		dCommand.setNamespace(this.tenantId);
		dCommand.setTenantId(this.tenantId);
		
		dCommand.setBody(body);
		
		return new DataRequest(dCommand);
	}
	
	private DataResponse processRequest(DataRequest request){
		try {
			DataProcessor processor = new DataProcessor (request);
			return processor.Process();
		} catch (Exception e) {
			return DataProcessor.SendError(e);
		}
	}
	
	public  DataResponse Store(String Name,Object Obj,StoreOperation comandtype){
		HashMap <String, Object> reqBody = new HashMap<>();
		reqBody.put("object", Obj);
		DataRequest dReq = getDataRequest(Name, reqBody, DataOperation.Insert);
		
		return this.processRequest(dReq);
	}
	
	public <T> ArrayList<T> Retrive(String Name,HashMap<String,Object> QueryField,Class<T> c) throws Exception{
		
		HashMap <String,Object> fullRequest = new HashMap<String,Object>();
		fullRequest.put("queryParams", QueryField);
		DataRequest dReq = getDataRequest(Name, fullRequest, DataOperation.Get);
		Schema schema = SchemaManager.Get(this.tenantId, c);
		DataCommand command = dReq.getDataCommand(); 
		command.setSchema(schema);
		command.setClassObject(c);
		DataResponse dr = this.processRequest(dReq);
		
		if (dr !=null){
			if (!dr.isSuccess()) throw dr.getError();
			return (ArrayList<T>)dr.getResponse();
		}
		
		return new ArrayList<>();
	}
}
