package com.sossgrid.datastore.schemarepo;

import com.sossgrid.configuration.ConfigurationManager;

public class SchemaRepoFactory {
	private static AbstractSchemaRepo repo;
	
	public static AbstractSchemaRepo GetRepository(){
		if (repo == null)
			repo = create();
		
		return repo;
	}
	
	private static AbstractSchemaRepo create(){
		AbstractSchemaRepo repo = null;
		Object sObj = ConfigurationManager.Get("schemaRepo");
		String schemaName = sObj ==null ? "mock" : sObj.toString(); 
		
		switch (schemaName){
			case "local":
				repo = new LocalSchemaRepo();
				break;
			default:
				repo = new MockSchemaRepo();
				break;
		}
		
		return repo;
	}
}
