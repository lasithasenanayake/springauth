package com.sossgrid.mysql;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.text.SimpleDateFormat;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sossgrid.datastore.DataType;
import com.sossgrid.log.Out;
import com.sossgrid.log.Out.LogType;


public class MySqlHelper {
	
	private static String GenerateValueParam(Field field,Object value){
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
			case "java.util.Date":
				SimpleDateFormat formatter = new SimpleDateFormat("MM-dd-yyyy HH:mm:ss");
				strValue= "STR_TO_DATE('"+formatter.format((java.util.Date)value)+"','%m-%d-%Y %H:%i:%s'),";
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
	
	public static String GetInsert(Object someObject,String Name){
		
		
		String strSql="Insert Into "+ Name;
		String strColumn="(";
		String strValues="Values(";
		System.out.println(someObject);
		for (Field field : someObject.getClass().getDeclaredFields()) {
			//field.getAnnotations()
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
		Out.Write( strSql + strColumn + strValues, LogType.DEBUG);
		return strSql + strColumn + strValues;
	}
	
	public static String ConvertSQLtype(String datatype,int datalength,boolean isNull){
		String strValue="";
		
		
		
		//System.out.println(field.getName() + "=" + value + " type " +field.getType().getName());
        switch(datatype){
			case "int":
				strValue="INT "+((isNull)?"":"NOT")+" NULL,";
				break;
			case "float":
				strValue="FLOAT "+((isNull)?"":"NOT")+" NULL,";
				
				break;
			case "double":
				strValue="DECIMAL "+((isNull)?"":"NOT")+" NULL,";
				break;
			case "short":
				strValue="LONG "+((isNull)?"":"NOT")+" NULL,";
				break;
			case "long":
				strValue="LONG "+((isNull)?"":"NOT")+" NULL,";
				break;
			case "java.lang.String":
				if(datalength<3072){
					strValue="VARCHAR("+Integer.toString(datalength)+") "+((isNull)?"":"NOT")+" NULL,";
				}else{
					strValue="TEXT "+((isNull)?"":"NOT")+" NULL,";
				}
				break;
			case "java.util.Date":
				strValue= "DATETIME "+((isNull)?"":"NOT")+" NULL,";
				break;
			case "boolean":
				strValue="VARCHAR(5) "+((isNull)?"":"NOT")+" NULL,";
				break;
			default:
				strValue="TEXT "+((isNull)?"":"NOT")+" NULL,";
				break;
        
        }
		return strValue;
	}
	
	public static void GenerateTable(Object someObject,String Name,Connection con) throws SQLException,Exception{
		
		try {
			ResultSet rs =con.prepareStatement("Select * from "+Name+" limit 0,0;").executeQuery();
			ResultSetMetaData mdata=rs.getMetaData();
			//Alter Table script
			
			throw new SQLException("Not Implemented Alter Table");
			
		} catch (SQLException e) {
			Out.Write(e.getMessage(), LogType.ERROR);
			Out.Write(e.getErrorCode(), LogType.ERROR);
			String createSQl=GetCreateTableStatment(someObject,Name);
			con.createStatement().executeUpdate(createSQl);
		}
		
		//return strSql + strColumn;
	}
	
	public static Boolean isPrimaryOK(Field field){
		switch(field.getType().getName()){
			case "int":
				return true;
				
			case "float":
				return true;
				
			case "double":
				//strValue=value.toString()+",";
				return true;
				
			case "short":
				//strValue=value.toString()+",";
				return true;
				
			case "long":
				//strValue=value.toString()+",";
				return true;
				
			case "java.lang.String":
				return true;
				
			case "java.util.Date":
				return false;
				
			case "boolean":
				return false;
				//strValue="'"+value.toString()+"',";
				
			default:
				return false;    
		}
		
	}
	
	public static int getLength(Field field,int length){
		switch(field.getType().getName()){
			case "int":
				return 0;
				
			case "float":
				return 0;
				
			case "double":
				//strValue=value.toString()+",";
				return 0;
				
			case "short":
				//strValue=value.toString()+",";
				return 0;
				
			case "long":
				//strValue=value.toString()+",";
				return 0;
				
			case "java.lang.String":
				if(length==0){
					return 3072;
				}else{
					return length;
				}
				
			case "java.util.Date":
				return 0;
				
			case "boolean":
				return 0;
				//strValue="'"+value.toString()+"',";
				
			default:
				return 0;    
		}
		
	}
	
	public static String GetCreateTableStatment(Object someObject,String Name) throws Exception{
		String strSql="Create Table "+ Name;
		String strColumn="(";
		String strPrimaryKeys="PRIMARY KEY (";
		//String strValues="Values(";
		System.out.println(someObject);
		for (Field field : someObject.getClass().getDeclaredFields()) {
			boolean isNull=true;
			Annotation[] annotations = field.getAnnotations();
			DataType dType = null;
			int datalength=0;
			for (Annotation a : annotations){
				Out.Write(a.annotationType().getName(), LogType.DEBUG);
				if (a.annotationType().getName().equals("com.sossgrid.datastore.DataType")){
					dType = (DataType)a;
					break;
				}
			}
			
			if (dType !=null){
				if(dType.IsPrimary()){
					if(isPrimaryOK(field)){
						if(dType.MaxLen()<255){
							strPrimaryKeys+=field.getName()+",";
							isNull=false;
						}else{
							throw new Exception(field.getName() + " Primary key is not valied for type "+field.getType().getName() + " Length is too long "+ Integer.toString(datalength));
						}
					}
					
					datalength=getLength(field, dType.MaxLen());
					
				}else{
					throw new Exception(field.getName() + " Primary key is not valied for type "+field.getType().getName());
				}
			}
			
			field.setAccessible(true); // You might want to set modifier to public first.
			try {
				strColumn+=field.getName()+" "+ConvertSQLtype(field.getType().getName(),datalength,isNull);    		    
				
			} catch (IllegalArgumentException e) {
				// TODO Auto-generated catch block
				Out.Write("GetCreateTableStatment "+e.getMessage(), LogType.ERROR);
			} 
			
		    
		}
		if(strPrimaryKeys.equals("PRIMARY KEY (")){
			strColumn=strColumn.substring(0, strColumn.length()-1)+")";
		}else{
			strColumn=strColumn+strPrimaryKeys.substring(0, strPrimaryKeys.length()-1)+ "))";
		}
		Out.Write(strSql + strColumn, LogType.DEBUG);
		return strSql + strColumn;
	}
	
	

}
