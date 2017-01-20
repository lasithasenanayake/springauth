package com.sossgrid;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.Context;

import org.glassfish.jersey.server.validation.ValidationError;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sossgrid.authlib.AuthCertificate;
import com.sossgrid.common.JSONFunction;
import com.sossgrid.configuration.ConfigurationManager;
import com.sossgrid.datastore.*;
import com.sossgrid.exceptions.SossDataException;
import com.sossgrid.exceptions.UnAutherizedException;

@Controller
public class DataService {

	@RequestMapping(value="/data/*")
	public @ResponseBody DataResponse ProcessSingleParam(@Context HttpServletRequest request){
		return this.processRequest(request);
	}

	private DataResponse processRequest(HttpServletRequest request){
		DataRequest dataRequest = null;
		try {
			DataCommand sendCommand = getDataCommand(request);
			dataRequest = new DataRequest(sendCommand);
			return (new DataProcessor(dataRequest)).Process();
		} catch (Exception e) {
			if (dataRequest !=null)
					return DataProcessor.SendError(dataRequest, e);
			else
				return DataProcessor.SendError(e);
		}			

	}
	
	private DataCommand getDataCommand(HttpServletRequest request) throws Exception{
		DataCommand dataCommand = new DataCommand();
		
		Object canValidateAuth = ConfigurationManager.Get("authValidateForData");
		if (canValidateAuth != null)
			if (((boolean)canValidateAuth) == true)
				validateAuthCertificate(dataCommand, request);
				
		String tenantId = request.getServerName();
		String method = request.getMethod(); 
		String nsAndClass = request.getRequestURI().replace("/data/", "").trim();
		String qString = request.getQueryString();
		HashMap<String,String> headers = new HashMap<>();
		
		if (nsAndClass.indexOf('/') != -1)
			throw new SossDataException ("Class is not set for data layer");
		
		if (nsAndClass.length() ==0)
			throw new SossDataException ("Classname not entered");
		
		if (dataCommand.isValid()){
			if (qString !=null){
				String[] keyVals = qString.split("&");
				
				for (String kv : keyVals){
					if (kv.contains("=")){
						String[] tmpSplit = kv.split("=");
						headers.put(tmpSplit[0], tmpSplit[1]);
					}else headers.put(kv, null);
				}
			}
			
			String namespace;
			String className;
			
			int lIndex = nsAndClass.lastIndexOf('.');
			
			if (lIndex == -1){
				namespace = tenantId;
				className = nsAndClass;
			}else{
				namespace = nsAndClass.substring(0, lIndex);
				className = nsAndClass.substring(lIndex);			
			}
			
			 
			dataCommand.setNamespace(namespace);
			dataCommand.setClassName(className);
			dataCommand.setTenantId(tenantId);
			dataCommand.setHeaders(headers);
			
			if (!method.equals("GET"))
				try {
					dataCommand.setBody(this.getRequestBody(request));
				} catch (Exception e) {
					throw new SossDataException ("Unable to Parse JSON body : " + e.getMessage());
				}
			else 
				dataCommand.setBody(new HashMap<String,Object>());
			
			dataCommand.loadSchema();
			
			switch (method){
				case "GET":
					if (qString ==null){
						dataCommand.setOperation(DataOperation.Get);
					}else {
						if (headers.containsKey("query")){
							HashMap<String,Object> fullRequest = new HashMap<>();
							HashMap<String,Object> queryObject = new HashMap<>();
							String query = headers.get("query");
							String[] splitData = query.split(",");
							for (String si: splitData){
								String[] splitData1 = si.split(":");
								queryObject.put(splitData1[0], splitData1[1]);
							}
							fullRequest.put("queryParams", queryObject);
							dataCommand.setBody(fullRequest);
							dataCommand.setOperation(DataOperation.Get);
						} else if (headers.containsKey("schema"))
							dataCommand.setOperation(DataOperation.GetSchema); 						
					}
					
					break;
				case "POST":
					if (qString ==null)
						dataCommand.setOperation(DataOperation.Insert);
					/*
					else {
						if (headers.containsKey("schema"))
							dataCommand.setOperation(DataOperation.CreateSchema);
						else if (headers.containsKey("store"))
							dataCommand.setOperation(DataOperation.Store);
					}
					*/
					break;
				case "PUT":
					if (qString ==null)
						dataCommand.setOperation(DataOperation.Update);
					/*
					else {
						if (headers.containsKey("schema"))
							dataCommand.setOperation(DataOperation.UpdateSchema);
					}
					*/
					break;
				case "PATCH":
					if (qString ==null)
						dataCommand.setOperation(DataOperation.Update);
					/*
					else {
						if (headers.containsKey("schema"))
							dataCommand.setOperation(DataOperation.UpdateSchema);
					}
					*/
					break;
				case "DELETE":
					if (qString ==null)
						dataCommand.setOperation(DataOperation.Delete);
					/*
					else {
						if (headers.containsKey("schema"))
							dataCommand.setOperation(DataOperation.DeleteSchema);
					}
					*/
					break;
				default:
					dataCommand.setValid(false);
					dataCommand.setErrorMessage("Bad Request Type");
					break;
			}
		}

		dataCommand.checkSchemaPermission();
		
		return dataCommand;
	}
	
	private HashMap<String,Object> getRequestBody(HttpServletRequest request) throws Exception{
		ObjectMapper mapper = new ObjectMapper();
		String postBody = request.getReader().lines().collect(Collectors.joining(System.lineSeparator()));
	
	    TypeReference<HashMap<String,Object>> typeRef = new TypeReference<HashMap<String,Object>>() {};
	
	    HashMap<String,Object> map = mapper.readValue(postBody, typeRef); 
		return map;
	}
	
	private void validateAuthCertificate(DataCommand dCommand, HttpServletRequest hReq) throws Exception{
		String authCookie;
		
		authCookie = hReq.getHeader("sossAuth");
		Cookie[] cookies = hReq.getCookies();
		
		if (authCookie == null && cookies !=null)
		for (Cookie c : cookies)
		if (c.getName().equals("sossAuth")){
			authCookie = c.getValue();
			break;
		}
		
		if (authCookie == null){
			throw new SossDataException ("Auth certificatte not available in cookies or headers");
		}else{
			Object authCert = JSONFunction.GetObjectFromString(authCookie, AuthCertificate.class);
			if (authCert == null){
				throw new SossDataException ("Incorrect format in auth certificatte in headers or cookies");				
			}else
				dCommand.setAuthCertificate((AuthCertificate)authCert);
		}
		
	}
	
}
