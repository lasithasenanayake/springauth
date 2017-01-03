package com.sossgrid.datastore;

import java.util.ArrayList;
import java.util.HashMap;

import com.sossgrid.configuration.ConfigurationManager;

public class ResourceExtractor {
	
	public static HashMap<String,String> GetDataStoreConfig(String Domain){ 
		HashMap<String,String> o=new HashMap<String,String>();

		o.put("server", ConfigurationManager.Get("mysql_server").toString());
		o.put("database", "s_"+Domain.replace('.', '_'));
		o.put("username", ConfigurationManager.Get("mysql_username").toString());
		o.put("password", ConfigurationManager.Get("mysql_password").toString());
		o.put("dataadapter", ConfigurationManager.Get("data_adapter").toString());
		return o;
	}

}
