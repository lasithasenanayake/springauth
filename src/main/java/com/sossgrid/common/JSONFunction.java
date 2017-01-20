package com.sossgrid.common;

import java.io.IOException;
import java.util.HashMap;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;

public class JSONFunction {

	public static HashMap<String,Object> GetMapFromString(String json){
		ObjectMapper mapper = new ObjectMapper();
	
	    TypeReference<HashMap<String,Object>> typeRef = new TypeReference<HashMap<String,Object>>() {};
	
	    HashMap<String, Object> map = null;
		try {
			map = mapper.readValue(json, typeRef);
		} catch (Exception e) {
			
		}
		
		return map;
	}
	
	public static Object GetObjectFromString(String json, Class clsName){
		ObjectMapper mapper = new ObjectMapper();
		try {
			return mapper.readValue(json, clsName);
		} catch (Exception e) {
			return null;
		} 
	}
}
