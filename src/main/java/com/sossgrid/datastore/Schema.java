package com.sossgrid.datastore;

import static org.mockito.Matchers.anyBoolean;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.ArrayList;

import com.sossgrid.datastore.annotations.DataType;

public class Schema {
	private ArrayList <SchemaField> fields;
	
	public Schema(){
		//deserialized from JSON
	}
	
	public void setFields (ArrayList<SchemaField> f){
		this.fields = f;
	}

	public Schema(Class cls){
		this.fields = new ArrayList <SchemaField>();
		this.extractFromObject(cls);
	}
	
	private void extractFromObject(Class cls){
		for(Field f : cls.getDeclaredFields()){
			SchemaAnnotation schemaAnnotation = null;

			for (Annotation a : f.getAnnotations())
			if (a.getClass().toString().equals("com.sossgrid.datastore.annotations.DataType")){
				DataType dtAnnotation = (DataType)a;
				schemaAnnotation = new SchemaAnnotation(dtAnnotation);
				break;
			}
			
			SchemaField scField = new SchemaField(f.getName(), f.getType().toString());
			scField.setAnnotations(schemaAnnotation);
			this.fields.add(scField);
		}	
	}
	
	public SchemaField Get(String fieldName){
		for (Object obj: fields.toArray()){
			SchemaField f = (SchemaField)obj;
			if (f.getName().equals(fieldName))
				return f;
		}
		return null;
	}
	
	public SchemaField[] GetAll(){
		SchemaField[] outArray = new SchemaField[fields.size()];
		outArray = fields.toArray(outArray);
		return outArray;
	}

}
