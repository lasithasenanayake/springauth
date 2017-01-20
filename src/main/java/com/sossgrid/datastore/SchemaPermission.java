package com.sossgrid.datastore;

import com.fasterxml.jackson.annotation.JsonProperty;

public class SchemaPermission {
	@JsonProperty("operations")
	private SchemaPermissionOperations operations;
	
	@JsonProperty("filterFields")
	private String[] filterFields;
	
	public String[] getFilterFields() {
		return filterFields;
	}
	
	public void setFilterFields(String[] filterFields) {
		this.filterFields = filterFields;
	}
	
	public SchemaPermissionOperations getOperations() {
		return operations;
	}
	
	public void setOperations(SchemaPermissionOperations operations) {
		this.operations = operations;
	}
	
	public boolean checkValid(DataOperation operation){
		if (operations !=null)
			return operations.Validate(operation);
		
		return false;
	}
	
}
