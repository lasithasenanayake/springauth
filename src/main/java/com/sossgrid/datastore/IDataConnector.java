package com.sossgrid.datastore;

import java.util.ArrayList;
import java.util.HashMap;

public interface IDataConnector {
	public void CreateConnection(HashMap<String,String> Configuration) throws Exception;
	public DataResponse Store(DataRequest request,StoreOperation commadtype);
	public DataResponse[] Store(DataRequest request);
	public DataResponse Delete(DataRequest request);
	public DataResponse[] Delete(String Name,Object[] Objs);
	public <T> ArrayList<T> Retrive(DataRequest request,Class<T> c);
}
