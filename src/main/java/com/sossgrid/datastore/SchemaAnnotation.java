package com.sossgrid.datastore;

import com.sossgrid.datastore.annotations.DataType;

public class SchemaAnnotation {
	private String fieldName = "";
	private int minLen = 0;
	private int maxLen = 0;
	private boolean isPrimary = false;
	
	public SchemaAnnotation (DataType dt){
		this.fieldName = dt.FieldName();
		this.minLen = dt.MinLen();
		this.maxLen = dt.MaxLen();
		this.isPrimary = dt.IsPrimary();
	}
	
	public SchemaAnnotation(){
		
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
	
	
}

