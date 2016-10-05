package com.sossgrid.mysql;

import java.util.ArrayList;
import java.util.HashMap;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sossgrid.datastore.*;
import com.sossgrid.datastore.IDataConnector;
import com.sossgrid.datastore.StatusMessage;
import com.sossgrid.log.Out;
import com.sossgrid.log.Out.LogType;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;

public class MysqlConnector implements IDataConnector{
	private Connection con;
	private HashMap<String,String> config;
	
	@Override
	public void CreateConnection(HashMap<String, String> Configuration) throws Exception {
		// TODO Auto-generated method stub
		try{
		
		 con=DriverManager.getConnection("jdbc:mysql://"+Configuration.get("server")+"/"+Configuration.get("database")+"?user="+Configuration.get("username")+"&password="+Configuration.get("password")+"");
		 config=Configuration;
		 
		}catch(SQLException ex){
			Out.Write(ex,LogType.ERROR);
			Out.Write(ex.getErrorCode(),LogType.ERROR);
			if(ex.getErrorCode()==1049){
				try{
					con=DriverManager.getConnection("jdbc:mysql://"+Configuration.get("server")+"/"+"?user="+Configuration.get("username")+"&password="+Configuration.get("password")+"");
					Out.Write("Creating Database " +Configuration.get("database") ,LogType.DEBUG);
					con.createStatement().executeUpdate("CREATE DATABASE "+Configuration.get("database")+"");
					Out.Write("Created Database " +Configuration.get("database") ,LogType.DEBUG);
					con.close();
					CreateConnection(Configuration);
				}catch(SQLException ex1){
					Out.Write(ex1,LogType.ERROR);
					Out.Write(ex1.getErrorCode(),LogType.ERROR);
					throw new Exception(ex1.getMessage());
				}
			}else{
				throw new Exception(ex.getMessage());
			}
			
		}
	}

	@Override
	public StatusMessage Store(String Name, Object Obj,DataStoreCommandType commadtype) {
		StatusMessage status;
		
		switch(commadtype){
		case InsertRecord:
			if(isDbConnected()){
				String insertSQL=MySqlHelper.GetInsert(Obj, Name);
				try {
					con.createStatement().executeUpdate(insertSQL);
					//insertStatment.executeQuery();
					status=new StatusMessage(false,"", Obj);
					return status;
				} catch (SQLException e) {
					Out.Write(e.getErrorCode(), LogType.ERROR);
					Out.Write(e.getMessage(), LogType.ERROR);
					//e.printStackTrace();
					try {
						Out.Write("Creating or altering table", LogType.DEBUG);
						MySqlHelper.GenerateTable(Obj, Name, con);
						Out.Write("Retry SQL command.", LogType.DEBUG);
						Store(Name,Obj,commadtype);
						
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						status =new StatusMessage(true, e1.getMessage(), Obj);
						return status;
						
					}catch (Exception e2) {
						// TODO: handle exception
						status =new StatusMessage(true, e2.getMessage(), Obj);
						return status;
					}
				}
				
			}
			break;
		case UpdateRecord:
			String updateSQL =MySqlHelper.GetUpdate(Obj, Name);
			try {
				con.createStatement().executeUpdate(updateSQL);
				status=new StatusMessage(false,"", Obj);
				return status;
			} catch (SQLException e) {
				Out.Write(e.getErrorCode(), LogType.ERROR);
				Out.Write(e.getMessage(), LogType.ERROR);
				//e.printStackTrace();
				try {
					Out.Write("Creating or altering table", LogType.DEBUG);
					MySqlHelper.GenerateTable(Obj, Name, con);
					Out.Write("Retry SQL command.", LogType.DEBUG);
					Store(Name,Obj,commadtype);
					
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					status =new StatusMessage(true, e1.getMessage(), Obj);
					return status;
					
				}catch (Exception e2) {
					// TODO: handle exception
					status =new StatusMessage(true, e2.getMessage(), Obj);
					return status;
				}
			}
			break;
		
		}
		status =new StatusMessage(true, "Error Database Conenection is not established", Obj);
		return status;
	}
	
	private  boolean isDbConnected() {
	    //final String CHECK_SQL_QUERY = "SELECT 1";
	    try {
			if(!con.isClosed() || con!=null){
				return true;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			return false;
		}
	    return false;
		
	}

	@Override
	public StatusMessage[] Store(String Name, Object[] Objs) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public StatusMessage Delete(String Name, Object Obj) {
		StatusMessage status;
		String updateSQL =MySqlHelper.GetUpdate(Obj, Name);
		try {
			con.createStatement().executeUpdate(updateSQL);
			status=new StatusMessage(false,"", Obj);
			return status;
		} catch (SQLException e) {
			Out.Write(e.getErrorCode(), LogType.ERROR);
			Out.Write(e.getMessage(), LogType.ERROR);
		}
		
		status =new StatusMessage(true, "Error Database Conenection is not established", Obj);
		return status;
		
	}

	@Override
	public StatusMessage[] Delete(String Name, Object[] Objs) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <T> ArrayList<T> Retrive(String Name, HashMap<String, Object> QueryField,Class<T> c) {
		// TODO Auto-generated method stub
		ArrayList<T> newlistOfRecords =new ArrayList<T>();
		
		try {
			if(isDbConnected()){
				
				ResultSet rs=con.prepareStatement(MySqlHelper.GetSelect(Name, QueryField)).executeQuery();
				while (rs.next()) {
					Object obj=c.newInstance();
					
					for (Field field : c.getDeclaredFields()) {
						field.setAccessible(true);
						Out.Write(rs.getString(field.getName()), LogType.DEBUG);
						 switch(field.getType().getName()){
							case "int":
								field.setInt(obj, rs.getInt(field.getName()));
								break;
							case "float":
								field.setFloat(obj, rs.getFloat(field.getName()));
								break;
							case "double":
								field.setDouble(obj, rs.getDouble(field.getName()));
								break;
							case "short":
								field.setShort(obj, rs.getShort(field.getName()));
								break;
							case "long":
								field.setLong(obj, rs.getLong(field.getName()));
								break;
							case "java.lang.String":
								field.set(obj, rs.getString(field.getName()));
								break;
							case "java.util.Date":
								//field.set(obj,Date.parse(s)(rs.getDate(field.getName())));
								break;
							case "boolean":
								//field.setDouble(obj, rs.getDouble(field.getName()));
								break;
							default:
								ObjectMapper ow = new ObjectMapper();
								/*
								try{
									String json =ow.readValue(rs.getString(field.getName()),);
									strValue="'"+json.toString()+"',";
								}catch (Exception e) {
									strValue="'"+e.getMessage()+"',";
									// TODO: handle exception
								}*/
								break;
				        
				        }
						newlistOfRecords.add((T)obj);
						
					}
			    }
			}
			
		} catch (InstantiationException | IllegalAccessException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//T a[]=new T[newlistOfRecords.size()];
		
		return newlistOfRecords;
	}

	
}
