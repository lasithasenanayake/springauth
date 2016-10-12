package com.sossgrid.datastore;

import java.util.ArrayList;
import java.util.HashMap;

import com.sossgrid.authlib.AuthCertificate;

public class Connector  {
	
	IDataConnector datastore;
	
	public Connector(AuthCertificate ac)throws Exception{
		loadDataConnectors(ac.getDomain());
	}
	
	public Connector()throws Exception{
		loadDataConnectors("sys.sossgrid.com");
	}
	
	private void loadDataConnectors(String Domain) throws Exception{
		HashMap<String, String> conts=ResourceExtractor.GetDataStoreConfig(Domain);
		
			try {
				IDataConnector i= (IDataConnector)Class.forName(conts.get("dataadapter")).newInstance();
				i.CreateConnection(conts);
				datastore=i;
			} catch (Exception e) {
				// TODO Auto-generated catch block
				datastore=null;
				throw e;
				//e.printStackTrace();
			}
		
	}
	
	public  StatusMessage Store(String Name,Object Obj,DataStoreCommandType comandtype){
		
		return datastore.Store(Name, Obj, comandtype);
	}
	
	public <T> ArrayList<T> Retrive(String Name,HashMap<String,Object> QueryField,Class<T> c){
		return datastore.Retrive(Name, QueryField, c);
	}
	
	
	
	

}
