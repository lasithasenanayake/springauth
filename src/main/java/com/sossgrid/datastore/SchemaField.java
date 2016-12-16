package com.sossgrid.datastore;

public class SchemaField {
	private String fieldName;
	private String dataType;
	private SchemaAnnotation annotations;
	
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
	
	public SchemaAnnotation getAnnotations() {
		return annotations;
	}

	public SchemaAnnotation getOrDefaultAnnotations() {
		return (annotations == null) ? new SchemaAnnotation() : annotations;
	}
	
	public void setAnnotations(SchemaAnnotation annotations) {
		this.annotations = annotations;
	}
}
