package com.sossgrid.configuration;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import com.sossgrid.common.*;

public class ConfigurationManager {
	
	private static HashMap<String, Object> configuration;
	
	public static void Load(){
		loadFromFile();
	}
	
	public static Object Get(String key){
		Object outData = null;
		if (configuration.containsKey(key))
			outData = configuration.get(key);
		
		return outData;
	}
	
	private static void loadFromFile(){
		String errorMsg = null;
		String currentFolder = System.getProperty("user.dir");
		
		Path filePath = Paths.get(currentFolder, "sossgrid.conf");
		if (Files.exists(filePath)){
			String fileData = FileFunction.ReadFile(filePath.toString());
			
			if (fileData !=null){
				configuration = JSONFunction.GetMapFromString(fileData);
			} else errorMsg = "sossgrid.conf is probably coropted";
				
			if (configuration ==null)
				errorMsg = "sossgrid.conf probably has a invalid JSON";
		}else errorMsg = "Unable to find sossgrid.conf in folder " + currentFolder;

		
		if (errorMsg !=null){
			System.out.println("");
			System.out.println("");
			System.out.println("CRITICAL ERROR");
			System.out.println("");
			System.out.println(errorMsg);
			System.out.println("");
			System.out.println("");
			System.out.println("");
			System.out.println("");
			System.exit(123);
		}
	}
}
