package com.sossgrid.datastore;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;


public class SchemaField {
	
	@JsonProperty("fieldName")
	private String fieldName;
	
	@JsonProperty("dataType")
	private String dataType;
	
	@JsonProperty("annotations")
	private SchemaAnnotation annotations;
	
	public SchemaField(){
		//deserialize from JSON
	}
	
	public SchemaField(String fieldName, String dataType){
		this.setFieldName(fieldName);
		this.dataType = dataType;
	}
	
	public String getName (){
		return getFieldName();
	}
	
	public String getFieldName() {
		return fieldName;
	}

	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
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
