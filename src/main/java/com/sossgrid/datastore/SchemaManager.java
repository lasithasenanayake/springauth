package com.sossgrid.datastore;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;

import com.sossgrid.authlib.AuthCertificate;
import com.sossgrid.common.FileFunction;
import com.sossgrid.common.JSONFunction;
import com.sossgrid.configuration.ConfigurationManager;
import com.sossgrid.datastore.schemarepo.*;
import com.sossgrid.exceptions.SossDataException;

public class SchemaManager {
	
	private static HashMap<Class, Schema> schemaClassMapping = new HashMap<>();
	private static HashMap<String, Schema> schemaStringMapping = new HashMap<>();
	
	private static Schema getSchemaFromRepo(String tenantId, String clsName){
		AbstractSchemaRepo repo = SchemaRepoFactory.GetRepository(); 
		return repo.Get(tenantId, clsName);
	}
	
	private static Schema getSchemaFromClass (String tenantId, Class cls){
		return new Schema(cls);
	}
	
	public static Schema Get (String tenantId, String clsName) throws SossDataException{
		if (schemaStringMapping.containsKey(clsName))
			return schemaStringMapping.get(clsName);
		else{
			Schema s = getSchemaFromRepo(tenantId, clsName);
			if (s == null)
				throw new SossDataException("The schema for "  + clsName + " not found in " + tenantId);
			
			schemaStringMapping.put(clsName, s);
			return s;
		}
	}
	
	public static Schema Get (String tenantId, Class cls){
		if (schemaClassMapping.containsKey(cls))
			return schemaClassMapping.get(cls);
		else{
			Schema s = getSchemaFromClass(tenantId, cls);
			schemaClassMapping.put(cls, s);
			return s;
		}
	}
	
	public static void GetPermission(DataCommand dataCommand, String tenantId, String className) throws SossDataException{
		AuthCertificate authCertificate = dataCommand.getAuthCertificate();
		
		if (!isConfigLoaded)
			loadConfigsForPermission(authCertificate);
		
		if (isLocalMode)
			getPermissionLocally(dataCommand, tenantId, className);
		else
			getPermissionRemotely(dataCommand, tenantId, className);
	}
	
	private static SchemaPermission getPermissionObject(Path filePath) throws SossDataException{
		String fileData = FileFunction.ReadFile(filePath.toString());
		
		if (fileData !=null){
			Object permObject = JSONFunction.GetObjectFromString(fileData, SchemaPermission.class);
			
			if (permObject != null){
				return (SchemaPermission)permObject;
				
			}else throw new SossDataException ("User's permission file is corrupted. Probably the JSON format is incorrect.");
			
		} else throw new SossDataException("Unable to access user's permission file. Probably file doesn't have access or it is corrupted");
		
	}
	
	private static void getPermissionLocally(DataCommand dataCommand,String tenantId, String className) throws SossDataException{
		Path filePath = Paths.get(localSchemaFolder, tenantId + "." + className  + "." + dataCommand.getAuthCertificate().getEmail() + ".json");
		
		if (exceptionIfFound){ 
			if (Files.exists(filePath)){
				SchemaPermission permObject = getPermissionObject (filePath);
				boolean canThrowException = permObject.checkValid(dataCommand.getOperation());
				if (canThrowException)
					throw new SossDataException ("You are not authorized to access this operation");
			}
		}else{
			if (Files.exists(filePath)){
				SchemaPermission permObject = getPermissionObject (filePath);
				boolean canThrowException = !permObject.checkValid(dataCommand.getOperation());
				if (canThrowException)
					throw new SossDataException ("You are not authorized to access this operation");
				
			}else throw new SossDataException ("You are not authorized to access this class.");		
		}
	}
	
	private static void getPermissionRemotely(DataCommand dataCommand, String tenantId, String className) throws SossDataException{
		
	}
		
	private static boolean exceptionIfFound = false;
	private static boolean isLocalMode = true;
	private static String localSchemaFolder = null;
	private static String remoteRedisLocation = null;
	private static boolean isConfigLoaded = false;
	
	private static synchronized void loadConfigsForPermission(AuthCertificate authCertificate) throws SossDataException{
		
		Object canRestrict = ConfigurationManager.Get("restrictSchmasForUsers");
		
		if (canRestrict != null)
		if (((boolean)canRestrict) == true){
			if (authCertificate !=null){
				
				Object defaultRestrict = ConfigurationManager.Get("defaultRestriction");		
				
				if (defaultRestrict !=null){
					if (((String)defaultRestrict).equals("allow"))
						exceptionIfFound = true;
					
					Object permissionLocation = ConfigurationManager.Get("permissionLocation");
					
					if (permissionLocation !=null){
						if (permissionLocation.equals("remote")){
							isLocalMode = false;
							
							Object rRedisLocation = ConfigurationManager.Get("remoteRedisLocation");
							
							if (rRedisLocation !=null){
								remoteRedisLocation = (String)rRedisLocation;
								isConfigLoaded = true;
							}else
								throw new SossDataException ("You have to configure the parameter in config : remoteRedisLocation (should be set to a redis server ip/hostname)");
							
						}else{
							Object lSchemaFolder = ConfigurationManager.Get("schemaPermissionFolder");
							
							if (lSchemaFolder  !=null){
								localSchemaFolder = (String)lSchemaFolder;
								isConfigLoaded = true;
							}else
								throw new SossDataException ("You have to configure the parameter in config : localSchemaFolder (should be set to a folder location)");
						}
						
					}else 
						throw new SossDataException ("You have to configure the parameter in config : permissionLocation (should be local or remote)");
					
				}else 
					throw new SossDataException ("You have to configure the parameter in config : defaultRestriction (should be allow or deny)");
				
			}else throw new SossDataException ("You have to enable the parameter in config : authValidateForData");
		}		
	}
}
