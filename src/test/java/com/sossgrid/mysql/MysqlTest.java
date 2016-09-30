package com.sossgrid.mysql;

import static org.assertj.core.api.Assertions.catchThrowable;

import java.io.StringWriter;
import java.lang.reflect.Field;
import java.util.Date;
import java.util.HashMap;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.sossgrid.log.Out;
import com.sossgrid.log.Out.LogType;


//import java.lang.Reflect.Field;

public class MysqlTest {

	public void ConnectDatabase(HashMap<String,String> o) throws Exception{
		MysqlConnector c =new MysqlConnector();
		c.CreateConnection(o);
	}
	
	public String GenerateValueParam(Field field,Object value){
		String strValue="";
		if(value==null){
			return "NULL,";
		}
		System.out.println(field.getName() + "=" + value + " type " +field.getType().getName());
        switch(field.getType().getName()){
			case "int":
				strValue=value.toString()+",";
				break;
			case "float":
				strValue=value.toString()+",";
				break;
			case "double":
				strValue=value.toString()+",";
				break;
			case "short":
				strValue=value.toString()+",";
				break;
			case "long":
				strValue=value.toString()+",";
				break;
			case "java.lang.String":
				strValue="'"+value.toString()+"',";
				break;
			case "java.sql.Date":
				strValue= "'"+((java.sql.Date)value).toString()+"',";
				break;
			case "boolean":
				strValue="'"+value.toString()+"',";
				break;
			default:
				ObjectMapper ow = new ObjectMapper();
				try{
					String json =ow.writeValueAsString(value);
					strValue="'"+json.toString()+"',";
				}catch (Exception e) {
					strValue="'"+e.getMessage()+"',";
					// TODO: handle exception
				}
				break;
        
        }
		return strValue;
	}
	
	public String GenerateInsert(Object someObject,String Name){
		String strSql="Insert Into "+ Name;
		String strColumn="(";
		String strValues="Values(";
		System.out.println(someObject);
		for (Field field : someObject.getClass().getDeclaredFields()) {
			
		    field.setAccessible(true); // You might want to set modifier to public first.
		    Object value;
			try {
				//System.out.println(field.getName() + "=" + "value" + " type " +field.getType().getName());
				value = field.get(someObject);
				System.out.println(field.getName() + "=" + value + " type " +field.getType().getName());
				strColumn+=field.getName()+",";
			    strValues+=GenerateValueParam(field,value);    		    
				
				
			} catch (IllegalArgumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
			
		    
		}
		strColumn=strColumn.substring(0, strColumn.length()-1)+") ";
		Out.Write(strValues, LogType.DEBUG);
		strValues=strValues.substring(0, strValues.length()-1)+");";
		Out.Write(strValues, LogType.DEBUG);
		return strSql + strColumn + strValues;
	}
	
	public String ConvertSQLtype(String datatype){
		String strValue="";
		
		//System.out.println(field.getName() + "=" + value + " type " +field.getType().getName());
        switch(datatype){
			case "int":
				strValue="INT NOT NULL,";
				break;
			case "float":
				strValue="FLOAT NULL,";
				break;
			case "double":
				strValue="DECIMAL NULL,";
				break;
			case "short":
				strValue="SHORT NULL,";
				break;
			case "long":
				strValue="LONG NULL,";
				break;
			case "java.lang.String":
				strValue="VARCHAR(500) NULL,";
				break;
			case "java.sql.Date":
				strValue= "DATETIME NULL,";
				break;
			case "boolean":
				strValue="VARCHAR(5) NULL,";
				break;
			default:
				strValue="TEXT NULL,";
				break;
        
        }
		return strValue;
	}
	
	public String GenerateCreate(Object someObject,String Name){
		String strSql="Create Table "+ Name;
		String strColumn="(";
		//String strValues="Values(";
		System.out.println(someObject);
		for (Field field : someObject.getClass().getDeclaredFields()) {
			
		    field.setAccessible(true); // You might want to set modifier to public first.
		    Object value;
			try {
				//System.out.println(field.getName() + "=" + "value" + " type " +field.getType().getName());
				value = field.get(someObject);
				System.out.println(field.getName() + "=" + value + " type " +field.getType().getName());
				strColumn+=field.getName()+" "+ConvertSQLtype(field.getType().getName());
			    //strValues+=GenerateValueParam(field,value);    		    
				
				
			} catch (IllegalArgumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
			
		    
		}
		strColumn=strColumn.substring(0, strColumn.length()-1)+") ";
		return strSql + strColumn;
	}
	
}
