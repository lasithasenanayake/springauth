package com.sossgrid.datastore;

import java.util.ArrayList;

public class SchemaMockData {
	public static Schema getMockSchema(String tenantId, String clsName){
		Schema tmpSchema = new Schema();
		ArrayList<SchemaField> fields = new ArrayList<SchemaField>();
		tmpSchema.setFields(fields);
		switch(clsName.toLowerCase()){
			case "customer":
				fields.add(new SchemaField("id","int"));
				fields.add(new SchemaField("name","java.lang.String"));
				fields.add(new SchemaField("address","java.lang.String"));
				break;
			case "products":
				SchemaField pField = new SchemaField("itemid","int");
				SchemaAnnotation pkAnnotation = new SchemaAnnotation();
				pkAnnotation.setPrimary(true);
				pField.setAnnotations(pkAnnotation);
				fields.add(pField);
				
				fields.add(new SchemaField("name","java.lang.String"));
				fields.add(new SchemaField("caption","java.lang.String"));
				fields.add(new SchemaField("price","double"));
				fields.add(new SchemaField("imgurl","java.lang.String"));
				fields.add(new SchemaField("catogory","java.lang.String"));
				break;
		}

		return tmpSchema;
	}
}
