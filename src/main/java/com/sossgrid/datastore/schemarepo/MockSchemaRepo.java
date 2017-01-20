package com.sossgrid.datastore.schemarepo;

import java.util.ArrayList;

import com.sossgrid.datastore.Schema;
import com.sossgrid.datastore.SchemaAnnotation;
import com.sossgrid.datastore.SchemaField;

public class MockSchemaRepo extends AbstractSchemaRepo {

	@Override
	public Schema Get(String tenantId, String name) {
		Schema tmpSchema = new Schema();
		ArrayList<SchemaField> fields = new ArrayList<SchemaField>();
		tmpSchema.setFields(fields);
		switch(name.toLowerCase()){
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
