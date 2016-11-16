package com.sossgrid.datastore;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;

public class ObjectWrapper {
	
	private HashMap<String, Object> mapObj;
	private Object defObj;
	private Field[] defObjFields;
	private Schema schema;
	
	public ObjectWrapper (Schema schema, Object obj){
		this.schema = schema;
		System.out.println(obj.getClass().getName());
		String className =obj.getClass().getName();
		if (className.equals("java.util.LinkedHashMap") || className.equals("java.util.HashMap")){
			this.mapObj = (HashMap<String, Object>)obj;
		}else defObj = obj;
	}
	
	public Object getValue (String field){
		if (defObj !=null){
			if (defObjFields ==null){
				defObjFields = defObj.getClass().getFields();
			}
			
			for (Field f : defObjFields){
				if (f.getName().equals(field)){
					try {
						return f.get(field);
					} catch (IllegalArgumentException | IllegalAccessException e) {
						e.printStackTrace();
					}
				}
			}
		}else if (mapObj !=null){
			return mapObj.get(field);
		}
		return null;
	}
	
	public void setValue (String field, Object value){
		if (defObj !=null){
			
		}else if (mapObj !=null){
			mapObj.put(field, value);
		}
	}
	
	public Object getRaw (){
		return defObj != null ? defObj : mapObj; 
	}
	
	/*
	public String[] getFields (){
		ArrayList <String> fields = new ArrayList<String>();
		
		if (defObj !=null){
			for (Field f : defObj.getClass().getDeclaredFields()){
				fields.add(f.getName());
			}
		}else if (mapObj !=null){
			HashMap<String,Object> objMap = (HashMap<String,Object>)mapObj.get("object");
			
			for(Entry<String, Object> entry : objMap.entrySet()){
				fields.add(entry.getKey());
			}
		}
		
		String[] fieldArray = new String[fields.size()];
		fields.toArray(fieldArray);
		return fieldArray;
	}
	*/
}
