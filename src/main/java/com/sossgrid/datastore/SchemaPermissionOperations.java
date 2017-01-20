package com.sossgrid.datastore;

import com.fasterxml.jackson.annotation.JsonProperty;

public class SchemaPermissionOperations {
	
	@JsonProperty("store")
	private boolean store;
	
	@JsonProperty("insert")
    private boolean insert;
	
	@JsonProperty("update")
    private boolean update;
	
	@JsonProperty("delete")
    private boolean delete;
	
	@JsonProperty("get")
    private boolean get;
	
	@JsonProperty("createSchema")
    private boolean createSchema;
	
	@JsonProperty("updateSchema")
    private boolean updateSchema;
	
	@JsonProperty("deleteSchema")
    private boolean deleteSchema;
	
	@JsonProperty("getSchema")
    private boolean getSchema;
    
    
	public boolean isStore() {
		return store;
	}
	
	public void setStore(boolean store) {
		this.store = store;
	}
	
	public boolean isInsert() {
		return insert;
	}
	
	public void setInsert(boolean insert) {
		this.insert = insert;
	}
	
	public boolean isUpdate() {
		return update;
	}
	
	public void setUpdate(boolean update) {
		this.update = update;
	}
	
	public boolean isDelete() {
		return delete;
	}
	
	public void setDelete(boolean delete) {
		this.delete = delete;
	}
	
	public boolean isGet() {
		return get;
	}
	
	public void setGet(boolean get) {
		this.get = get;
	}
	
	public boolean isCreateSchema() {
		return createSchema;
	}
	
	public void setCreateSchema(boolean createSchema) {
		this.createSchema = createSchema;
	}
	
	public boolean isUpdateSchema() {
		return updateSchema;
	}
	
	public void setUpdateSchema(boolean updateSchema) {
		this.updateSchema = updateSchema;
	}
	
	public boolean isDeleteSchema() {
		return deleteSchema;
	}
	
	public void setDeleteSchema(boolean deleteSchema) {
		this.deleteSchema = deleteSchema;
	}
	
	public boolean isGetSchema() {
		return getSchema;
	}
	
	public void setGetSchema(boolean getSchema) {
		this.getSchema = getSchema;
	}
    
	public boolean Validate(DataOperation operation){
		if (operation == null) return false;
		
		switch (operation){
			case GetSchema:
				return this.getSchema;
			case Get:
				return this.get;
			case Insert:
				return this.insert;
			case Update:
				return this.update;
			case Store:
				return this.store;
			case Delete:
				return this.delete;
			default:
				return false;
		}
	}
    
}
