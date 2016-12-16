package com.sossgrid;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.Context;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sossgrid.datastore.*;

@Controller
@RequestMapping(value="*")
public class DataService {

	@RequestMapping(value="/data/*")
	public @ResponseBody DataResponse ProcessSingleParam(@Context HttpServletRequest request){
		return this.processRequest(request);
	}

	private DataResponse processRequest(HttpServletRequest request){
		DataCommand sendCommand = getDataCommand(request);

		if (sendCommand.isValid()){
			DataRequest reqInfo = new DataRequest(sendCommand);
			try {
				return (new DataProcessor(reqInfo)).Process();
			} catch (Exception e) {
				return DataProcessor.SendError(e);	
			}			
		}else{
			return DataProcessor.SendError(sendCommand.getErrorMessage());
		}
	}
	
	private DataCommand getDataCommand(HttpServletRequest request){
		DataCommand dataCommand = new DataCommand();
		
		String tenantId = request.getServerName();
		String method = request.getMethod(); 
		String nsAndClass = request.getRequestURI().replace("/data/", "").trim();
		String qString = request.getQueryString();
		HashMap<String,String> headers = new HashMap<>();
		
		if (nsAndClass.indexOf('/') != -1){
			dataCommand.setValid(false);
			dataCommand.setErrorMessage("Class is not set for data layer");
		}
		
		if (nsAndClass.length() ==0){
			dataCommand.setValid(false);
			dataCommand.setErrorMessage("Classname not entered");
		}
		
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
					dataCommand.setValid(false);
					dataCommand.setErrorMessage("Unable to Parse JSON body : " + e.getMessage());
					return dataCommand;
				}
			else 
				dataCommand.setBody(new HashMap<String,Object>());
			
			dataCommand.loadSchema();
			
			switch (method){
				case "GET":
					if (qString ==null){
						if (headers.containsKey("schema"))
							dataCommand.setOperation(DataOperation.GetSchema);
						else
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
						}
						
						dataCommand.setOperation(DataOperation.Get);
					}
					
					break;
				case "POST":
					if (qString ==null)
						dataCommand.setOperation(DataOperation.Insert);
					else {
						if (headers.containsKey("schema"))
							dataCommand.setOperation(DataOperation.CreateSchema);
						else if (headers.containsKey("store"))
							dataCommand.setOperation(DataOperation.Store);
					}
					break;
				case "PUT":
					if (qString ==null)
						dataCommand.setOperation(DataOperation.Update);
					else {
						if (headers.containsKey("schema"))
							dataCommand.setOperation(DataOperation.UpdateSchema);
					}
					break;
				case "PATCH":
					if (qString ==null)
						dataCommand.setOperation(DataOperation.Update);
					else {
						if (headers.containsKey("schema"))
							dataCommand.setOperation(DataOperation.UpdateSchema);
					}
					break;
				case "DELETE":
					if (qString ==null)
						dataCommand.setOperation(DataOperation.Delete);
					else {
						if (headers.containsKey("schema"))
							dataCommand.setOperation(DataOperation.DeleteSchema);
					}
					break;
				default:
					dataCommand.setValid(false);
					dataCommand.setErrorMessage("Bad Request Type");
					break;
			}
		}

		return dataCommand;
	}
	
	private HashMap<String,Object> getRequestBody(HttpServletRequest request) throws Exception{
		ObjectMapper mapper = new ObjectMapper();
		String postBody = request.getReader().lines().collect(Collectors.joining(System.lineSeparator()));
	
	    TypeReference<HashMap<String,Object>> typeRef = new TypeReference<HashMap<String,Object>>() {};
	
	    HashMap<String,Object> map = mapper.readValue(postBody, typeRef); 
		return map;
	}
	
}
