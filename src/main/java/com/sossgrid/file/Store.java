package com.sossgrid.file;

import java.io.File;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.sossgrid.log.Out;

public class Store {
	
	public  static <T> void ObjectWrite(T obj,String FileName){
		File f=new File(FileName);
		if(f.exists()){
			f.delete();
		}else{
			try{
			///f.createNewFile();
			f.createNewFile();
			ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
			ow.writeValue(f, obj);
			
			}catch (Exception e) {
				// TODO: handle exception
				Out.Write(e.getMessage(), Out.LogType.ERROR);
			}
		}
	}
	
	public <T> void  Append(T obj,String FileName){
		
	}

}
