package com.sossgrid.datastore;

import java.util.ArrayList;

public class SchemaMockData {
	public static Schema getMockSchema(String tenantId, String clsName){
		Schema tmpSchema = new Schema();
		ArrayList<SchemaField> fields = new ArrayList<SchemaField>();
		fields.add(new SchemaField("id","int"));
		fields.add(new SchemaField("name","java.lang.String"));
		fields.add(new SchemaField("address","java.lang.String"));
		
		tmpSchema.setFields(fields);
		
		return tmpSchema;
	}
}
