package com.sossgrid.datastore.schemarepo;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;

import com.sossgrid.common.FileFunction;
import com.sossgrid.common.JSONFunction;
import com.sossgrid.configuration.ConfigurationManager;
import com.sossgrid.datastore.Schema;

public class LocalSchemaRepo extends AbstractSchemaRepo {

	private HashMap<String, Schema> schemaCache;
	
	public LocalSchemaRepo(){
		this.schemaCache = new HashMap<>();
	}
	
	@Override
	public Schema Get(String tenantId, String name) {
		String key = tenantId + "." + name;

		if (!schemaCache.containsKey(key)){
			String schemaLoc = ConfigurationManager.Get("schemaLocation").toString();
			Path schemaPath = Paths.get(schemaLoc, tenantId, "schemas", name + ".json");
			String schemaData = FileFunction.ReadFile(schemaPath.toString());
			Object schemaObj = JSONFunction.GetObjectFromString(schemaData, Schema.class);
			Schema schema = schemaObj == null ? null : (Schema)(schemaObj);
			if (schema !=null)
				schemaCache.put(key, schema);
			
			return schema;
		}
			
		return schemaCache.get(key);
	}

	@Override
	public void Create(String tenantId, String name) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean Check(String tenantId, String name) {
		// TODO Auto-generated method stub
		return false;
	}

}
