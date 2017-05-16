package com.sossgrid.datastore;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.sossgrid.datastore.annotations.DataType;

public class SchemaAnnotation {
	@JsonProperty("fieldName")
	private String fieldName;
	
	@JsonProperty("minLen")
	private int minLen;
	
	@JsonProperty("maxLen")
	private int maxLen;
	
	@JsonProperty("isPrimary")
	private boolean isPrimary;

	@JsonProperty("autoIncrement")
	private boolean autoIncrement;
	
	public SchemaAnnotation (DataType dt){
		this.fieldName = dt.FieldName();
		this.minLen = dt.MinLen();
		this.maxLen = dt.MaxLen();
		this.isPrimary = dt.IsPrimary();
		this.autoIncrement = dt.AutoIncrement();
	}
	
	public SchemaAnnotation(){
		//deserialize form JSON
	}
	
	public String getFieldName() {
		return fieldName;
	}
	
	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}
	
	public int getMinLen() {
		return minLen;
	}
	
	public void setMinLen(int minLen) {
		this.minLen = minLen;
	}
	
	public int getMaxLen() {
		return maxLen;
	}
	
	public void setMaxLen(int maxLen) {
		this.maxLen = maxLen;
	}
	
	public boolean isPrimary() {
		return isPrimary;
	}
	
	public void setPrimary(boolean isPrimary) {
		this.isPrimary = isPrimary;
	}
	
	public boolean isAutoIncrement() {
		return autoIncrement;
	}
	
	public void setAutoIncrement(boolean isPrimary) {
		this.autoIncrement = isPrimary;
	}
	
	
}

