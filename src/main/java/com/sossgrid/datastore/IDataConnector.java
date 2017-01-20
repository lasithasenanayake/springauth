package com.sossgrid.datastore;

import java.util.ArrayList;
import java.util.HashMap;

public interface IDataConnector {
	public void CreateConnection(HashMap<String,String> Configuration) throws Exception;
	public Object Store(DataRequest request,StoreOperation commadtype) throws Exception;
	public Object[] Store(DataRequest request) throws Exception;
	public Object Delete(DataRequest request) throws Exception;
	public Object[] Delete(String Name,Object[] Objs) throws Exception;
	public <T> ArrayList<T> Retrive(DataRequest request,Class<T> c) throws Exception;
}
