package com.sossgrid.datastore;

import java.util.HashMap;

public class AnnotationInfo {

	private HashMap <String, AnnotationFieldInfo> fieldInfo;
	
	public AnnotationInfo (){
		this.fieldInfo = new HashMap<>();
	}
}
