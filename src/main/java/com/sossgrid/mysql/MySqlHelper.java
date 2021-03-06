package com.sossgrid.mysql;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map.Entry;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sossgrid.datastore.DataCommand;
import com.sossgrid.datastore.DataRequest;
import com.sossgrid.datastore.ObjectWrapper;
import com.sossgrid.datastore.Schema;
import com.sossgrid.datastore.SchemaAnnotation;
import com.sossgrid.datastore.SchemaField;
import com.sossgrid.datastore.StoreOperation;
import com.sossgrid.datastore.annotations.DataType;
import com.sossgrid.log.Out;
import com.sossgrid.log.Out.LogType;


public class MySqlHelper {
	
	private static String GenerateValueParam(String fieldtype,Object value){
		String strValue="";
		if(value==null){
			return "NULL";
		}
		//System.out.println(field.getName() + "=" + value + " type " +field.getType().getName());
        switch(fieldtype){
			case "int":
				strValue=value.toString()+"";
				break;
			case "float":
				strValue=value.toString()+"";
				break;
			case "double":
				strValue=value.toString()+"";
				break;
			case "short":
				strValue=value.toString()+"";
				break;
			case "long":
				strValue=value.toString()+"";
				break;
			case "class java.lang.String":
			case "java.lang.String":
				strValue="'"+value.toString()+"'";
				break;
			case "class java.util.Date":
			case "java.util.Date":
				if (value instanceof String){
					strValue= "STR_TO_DATE('"+value+"','%m-%d-%Y %H:%i:%s')";
				}else{
					SimpleDateFormat formatter = new SimpleDateFormat("MM-dd-yyyy HH:mm:ss");
					strValue= "STR_TO_DATE('"+formatter.format((java.util.Date)value)+"','%m-%d-%Y %H:%i:%s')";
				}
				break;
			case "boolean":
				strValue="'"+value.toString()+"'";
				break;
			default:
				ObjectMapper ow = new ObjectMapper();
				try{
					String json =ow.writeValueAsString(value);
					strValue="'"+json.toString()+"'";
				}catch (Exception e) {
					strValue="'"+e.getMessage()+"'";
					// TODO: handle exception
				}
				break;
        
        }
		return strValue;
	}
	
	public static String GetInsert(DataRequest request){
		DataCommand command = request.getDataCommand();
		ObjectWrapper someObject = command.getStorageObject();
		
		String strSql="Insert Into "+ command.getClassName();
		String strColumn="(";
		String strValues="Values(";
		System.out.println(someObject);
		for (SchemaField field : command.getSchema().GetAll()) {
			//field.getAnnotations()
		    Object value;
			try {
				//System.out.println(field.getName() + "=" + "value" + " type " +field.getType().getName());
				value = someObject.getValue(field.getName());
				System.out.println(field.getName() + "=" + value + " type " +field.getType());
				strColumn+=field.getName()+",";
			    strValues+=GenerateValueParam(field.getType(),value)+",";    		    
				
				
			} catch (IllegalArgumentException e) {
				throw e;
			} 
		}
		strColumn=strColumn+"sysversionid)  ";
		Out.Write(strValues, LogType.DEBUG);
		strValues=strValues+""+com.sossgrid.common.DataFunction.GetVersionID()+");";
		Out.Write( strSql + strColumn + strValues, LogType.DEBUG);
		return strSql + strColumn + strValues;
	}
	
	public static String GetUpdate(DataRequest request){
		DataCommand command = request.getDataCommand();
		ObjectWrapper storeObject = command.getStorageObject();
		//Object someObject,String Name
		
		String strSql="Update "+ command.getClassName() + " SET ";
		String strColumn="";
		String strWhere=" Where ";
		Schema schema = command.getSchema();
		
		for (SchemaField field : schema.GetAll()) {
		    Object value;
			try {
				
				boolean isprimary=false;
				int datalength=0;
				SchemaAnnotation dType = field.getAnnotations();
				
				if (dType !=null){
					isprimary=((SchemaAnnotation)dType).isPrimary();
				}
				
				value = storeObject.getValue(field.getName());
				System.out.println(field.getName() + "=" + value + " type " +field.getType());
				if(!isprimary){
					strColumn+=field.getName()+"="+GenerateValueParam(field.getType(),value)+",";
				}else{
					String strval =GenerateValueParam(field.getType(),value);
					strval=strval;
					strWhere+=field.getName()+"="+strval + " and ";
				}
				
				
			} catch (IllegalArgumentException e) {
				throw e;
			} 
			
		    
		}
		strColumn=strColumn+"sysversionid="+com.sossgrid.common.DataFunction.GetVersionID();
		Out.Write(strWhere, LogType.DEBUG);
		strWhere=strWhere.substring(0, strWhere.length()-5);
		//strValues=strValues+""+com.sossgrid.common.DataFunction.GetVersionID()+");";
		Out.Write( strSql + strColumn + strWhere, LogType.DEBUG);
		return strSql + strColumn + strWhere;
	}
	
	public static String GetSelect(DataRequest request){
		DataCommand command = request.getDataCommand();
		HashMap<String, Object> requestBody = command.getBody();
		
		String strSelect ="Select * from "+command.getClassName();
		boolean first=true;
		HashMap<String,Object> queryParams = (HashMap<String,Object>)requestBody.get("queryParams");
		
		if (queryParams !=null)
			for(Entry<String, Object> entry : queryParams.entrySet()) {
			    if(first){
			    	strSelect+=" Where "+entry.getKey()+"="+GenerateValueParam(entry.getValue().getClass().getName(),entry.getValue());
			    }else{
			    	strSelect+=" and "+entry.getKey()+"="+GenerateValueParam(entry.getValue().getClass().getName(),entry.getValue());
			    }
		}
		
		Out.Write(strSelect,LogType.DEBUG);
		return strSelect;
		
	}
	
	public static String GetDelete(Object someObject,String Name)throws Exception{
		
		
		String strSql="Delete From "+ Name ;
		//String strColumn="";
		String strWhere=" Where ";
		System.out.println(someObject);
		for (Field field : someObject.getClass().getDeclaredFields()) {
			//field.getAnnotations()
		    field.setAccessible(true); // You might want to set modifier to public first.
		    Object value;
			try {
				Annotation[] annotations = field.getAnnotations();
				DataType dType = null;
				boolean isprimary=false;
				int datalength=0;
				for (Annotation a : annotations){
					Out.Write(a.annotationType().getName(), LogType.DEBUG);
					if (a.annotationType().getName().equals("com.sossgrid.datastore.DataType")){
						dType = (DataType)a;
						break;
					}
				}
				
				if (dType !=null){
					isprimary=dType.IsPrimary();
				}
				
				value = field.get(someObject);
				
				if(isprimary){
					String strval =GenerateValueParam(field.getType().getName(),value);
					strval=strval.substring(0,strval.length()-1);
					strWhere+=field.getName()+"="+strval + " and ";
				}
				
				
			} catch (IllegalAccessException | IllegalArgumentException e) {
				throw e;
			} 
			
		    
		}
		
		Out.Write(strWhere, LogType.DEBUG);
		strWhere=strWhere.substring(0, strWhere.length()-5);
		Out.Write( strSql  + strWhere, LogType.DEBUG);
		return strSql +strWhere;
	}
	
	public static String ConvertSQLtype(String datatype,int datalength,boolean isNull, boolean isPrimary, boolean isAutoIncrement){
		String strValue="";
		
		
		//System.out.println(field.getName() + "=" + value + " type " +field.getType().getName());
        switch(datatype){
			case "int":
				strValue="INT "+((isNull)?"":"NOT")+" NULL ";
				if (isPrimary)
					strValue += "PRIMARY KEY ";
				if (isAutoIncrement)
					strValue += "AUTO_INCREMENT ";
				strValue += ",";
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
				if(datalength==0){
					strValue="TEXT "+((isNull)?"":"NOT")+" NULL,";
				}
				else if(datalength<3072){
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
	
	public static void GenerateTable(DataRequest request,Connection con) throws SQLException,Exception{
		//Object someObject
		//String Name
		
		try {
			ResultSet rs =con.prepareStatement("Select * from "+request.getDataCommand().getClassName()+" limit 0,0;").executeQuery();
			ResultSetMetaData mdata=rs.getMetaData();
			//Alter Table script
			
			throw new SQLException("Not Implemented Alter Table");
			
		} catch (SQLException e) {
			Out.Write(e.getMessage(), LogType.ERROR);
			Out.Write(e.getErrorCode(), LogType.ERROR);
			String createSQl=GetCreateTableStatment(request);
			System.out.println(createSQl);
			con.createStatement().executeUpdate(createSQl);
		}
		
		//return strSql + strColumn;
	}
	
	public static Boolean isPrimaryOK(SchemaField field){
		switch(field.getType()){
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
	
	public static int getLength(SchemaField field,int length){
		switch(field.getType()){
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
	
	public static String GetCreateTableStatment(DataRequest request) throws Exception{
		//Object someObject,String Name
		DataCommand command = request.getDataCommand();
		Schema schema = command.getSchema();
		
		String strSql="Create Table "+ command.getClassName();
		String strColumn="(";
		//String strPrimaryKeys="PRIMARY KEY (";
		//String strValues="Values(";
		for (SchemaField field : schema.GetAll()) {
			boolean isNull=true;

			SchemaAnnotation annotation = field.getAnnotations();
			int datalength=0;
			boolean isPrimary = false;
			boolean isAutoIncrement = false;
			
			if (annotation !=null){ 
				isPrimary = annotation.isPrimary();
				isAutoIncrement = annotation.isAutoIncrement();
				if(isPrimary){
					if(isPrimaryOK(field)){
						if(annotation.getMaxLen()<255){
							//strPrimaryKeys+=field.getName()+",";
							isNull=false;
						}else{
							throw new Exception(field.getName() + " Primary key is not valied for type "+field.getType() + " Length is too long "+ Integer.toString(datalength));
						}
					}
				}
				datalength=getLength(field, annotation.getMaxLen());
			}
			
			try {
				strColumn+=field.getName()+" "+ConvertSQLtype(field.getType(),datalength,isNull, isPrimary, isAutoIncrement);    		    
				
			} catch (IllegalArgumentException e) {
				// TODO Auto-generated catch block
				Out.Write("GetCreateTableStatment "+e.getMessage(), LogType.ERROR);
				throw e;
			} 
			
		    
		}
		strColumn+="sysversionid long NOT NULL)";
		/*
		if(strPrimaryKeys.equals("PRIMARY KEY (")){
			strColumn=strColumn.substring(0, strColumn.length()-1)+")";
		}else{
			strColumn=strColumn+strPrimaryKeys.substring(0, strPrimaryKeys.length()-1)+ "))";
		}
		*/
		Out.Write(strSql + strColumn, LogType.DEBUG);
		return strSql + strColumn;
	}
	
	

}
