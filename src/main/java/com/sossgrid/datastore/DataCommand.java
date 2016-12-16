package com.sossgrid.datastore;

import java.util.ArrayList;
import java.util.HashMap;

public class DataCommand {
	private boolean isValid;
	private String errorMessage;
	
	private String tenantId;
	private String namespace;
	private String className;
	
	private DataOperation dataOperation;
	private HashMap <String, String> headers;
	
	private HashMap <String, Object> body;
	
	private ObjectWrapper saveObj;
	private Schema schema;
	
	private Class classObject;
	
	public DataCommand(){
		this.setValid(true);
	}
	
	public String getTenantId() {
		return tenantId;
	}
	
	public void setTenantId(String tenantId){
		this.tenantId = tenantId;
	} 
	
	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public Class getClassObject(){
		try {
			if (this.classObject ==null)
				return Class.forName("java.util.HashMap").getClass();
		
			return this.classObject;
		} catch (ClassNotFoundException e) {
			return null;
		}
	}
	
	public void setClassObject (Class cls){
		this.classObject = cls;
	}

	public String getNamespace() {
		return this.namespace;
	}

	public void setNamespace(String namespace) {
		this.namespace = namespace;
	}
	
	public HashMap <String, Object> getBody() {
		return body;
	}
	
	public void setBody(HashMap <String, Object> body) {
		this.body = body;
	}
	
	public ObjectWrapper newStorageObject(){
		Object initObject = null;
		System.out.println(this.classObject);
		if (this.classObject !=null)
			try {
				initObject = this.classObject.newInstance();
			} catch (InstantiationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		else
			initObject = new HashMap<String,Object>();
		
		ObjectWrapper outData = new ObjectWrapper(this.getSchema(), initObject);
		return outData;
	}
	
	public ObjectWrapper getStorageObject(){
		if (this.body.containsKey("object")){
			if (saveObj == null){
				Object inputObj = this.body.get("object");
				if (this.schema==null) 
					loadSchema(inputObj);
				saveObj = new ObjectWrapper(this.schema, inputObj);
			}

			return saveObj;
		}
		else
			return null;
	}
	
	public void loadSchema(Object obj){
		this.schema = SchemaManager.Get(this.tenantId, obj.getClass());
	}
	
	
	public void loadSchema(){
		this.schema = SchemaManager.Get(this.tenantId, this.className);
	}
	
	public Schema getSchema(){
		return this.schema;
	}
	
	public void setSchema (Schema s){
		this.schema = s;
	}
	
	public HashMap <String, String> getHeaders() {
		return headers;
	}
	
	public void setHeaders (HashMap <String, String> header) {
		this.headers = header;
	}
	
	public DataOperation getOperation() {
		return dataOperation;
	}
	public void setOperation(DataOperation dataOperation) {
		this.dataOperation = dataOperation;
	}

	public boolean isValid() {
		return isValid;
	}

	public void setValid(boolean isValid) {
		this.isValid = isValid;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}
	
	
}
