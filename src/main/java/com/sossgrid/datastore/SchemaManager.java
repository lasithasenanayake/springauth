package com.sossgrid.datastore;

import java.util.HashMap;

import com.sossgrid.datastore.schemarepo.*;

public class SchemaManager {
	
	private static HashMap<Class, Schema> schemaClassMapping = new HashMap<>();
	private static HashMap<String, Schema> schemaStringMapping = new HashMap<>();
	
	private static Schema getSchemaFromRepo(String tenantId, String clsName){
		AbstractSchemaRepo repo = SchemaRepoFactory.GetRepository(); 
		return repo.Get(tenantId, clsName);
	}
	
	private static Schema getSchemaFromClass (String tenantId, Class cls){
		return new Schema(cls);
	}
	
	public static Schema Get (String tenantId, String clsName){
		if (schemaStringMapping.containsKey(clsName))
			return schemaStringMapping.get(clsName);
		else{
			Schema s = getSchemaFromRepo(tenantId, clsName);
			schemaStringMapping.put(clsName, s);
			return s;
		}
	}
	
	public static Schema Get (String tenantId, Class cls){
		if (schemaClassMapping.containsKey(cls))
			return schemaClassMapping.get(cls);
		else{

			Schema s = getSchemaFromClass(tenantId, cls);
			schemaClassMapping.put(cls, s);
			return s;
		}
	} 
}
