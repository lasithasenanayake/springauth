package com.sossgrid.datastore;

public class SchemaField {
	private String fieldName;
	private String dataType;
	
	public SchemaField(String fieldName, String dataType){
		this.fieldName =fieldName;
		this.dataType = dataType;
	}
	
	public String getName (){
		return fieldName;
	}
	
	public String getType(){
		return this.dataType;
	}
	
	public Object getAnnotation(String name){
		return null;
	}
}
