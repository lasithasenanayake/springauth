package com.sossgrid.datastore;

import java.util.ArrayList;
import java.util.HashMap;

import com.sossgrid.authlib.AuthCertificate;

public class SOSStore  {
	private String tenantId;
	
	public SOSStore(AuthCertificate ac)throws Exception{
		this.tenantId = ac.getDomain();
	}
	
	public SOSStore()throws Exception{
		this.tenantId = "sys.sossgrid.com";
	}
	
	private DataRequest getDataRequest(String className, HashMap<String,Object> body){
		DataCommand dCommand = new DataCommand();
		dCommand.setClassName(className);
		dCommand.setNamespace(this.tenantId);
		dCommand.setTenantId(this.tenantId);
		
		dCommand.setBody(body);
		dCommand.loadSchema();
		
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
		DataRequest dReq = getDataRequest(Name, reqBody);
		
		return this.processRequest(dReq);
	}
	
	public <T> ArrayList<T> Retrive(String Name,HashMap<String,Object> QueryField,Class<T> c){
		//Schema schema = SchemaManager.Get(c);
		//return datastore.Retrive(Name, QueryField, c, null);
		return null;
	}
}
