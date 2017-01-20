package com.sossgrid.common;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class FileFunction {
	
	public static String ReadFile(String path) {
		BufferedReader br=null;
		String outData = null;
	    
		try {
	    	br = new BufferedReader(new FileReader(path));
	        StringBuilder sb = new StringBuilder();
	        String line = br.readLine();

	        while (line != null) {
	            sb.append(line);
	            sb.append("\n");
	            line = br.readLine();
	        }
	        outData = sb.toString();
	    } 
	    catch(Exception ex){
	    	
	    }
	    finally {
	    	if (br!=null){
				try {
					br.close();
				} catch (IOException e) {
					
				}
    		}
	    }
	    
	    return outData;
	}
	

}
