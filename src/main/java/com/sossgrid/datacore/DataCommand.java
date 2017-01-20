package com.sossgrid.datacore;

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
