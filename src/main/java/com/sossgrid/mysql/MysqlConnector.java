package com.sossgrid.mysql;

import java.util.ArrayList;
import java.util.HashMap;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sossgrid.datastore.*;
import com.sossgrid.log.Out;
import com.sossgrid.log.Out.LogType;

import java.lang.reflect.Field;
import java.rmi.activation.ActivationGroupDesc.CommandEnvironment;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MysqlConnector implements IDataConnector{
	private Connection con;
	//private HashMap<String,String> config;
	
	@Override
	public void CreateConnection(HashMap<String, String> Configuration) throws Exception {
		// TODO Auto-generated method stub
		try{
			Class.forName("com.mysql.jdbc.Driver");
			con=DriverManager.getConnection("jdbc:mysql://"+Configuration.get("server")+"/"+Configuration.get("database")+"?user="+Configuration.get("username")+"&password="+Configuration.get("password")+"");
			//config=Configuration;
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
	public DataResponse Store(DataRequest request,StoreOperation commadtype) {
		DataResponse status;
		DataCommand command = request.getDataCommand();
		
		switch(commadtype){
		case InsertRecord:
			if(isDbConnected()){
				String insertSQL=MySqlHelper.GetInsert(request);
				try {
					con.createStatement().executeUpdate(insertSQL);
					//insertStatment.executeQuery();
					status=new DataResponse(command.getStorageObject().getRaw());
					return status;
				} catch (SQLException e) {
					Out.Write(e.getErrorCode(), LogType.ERROR);
					Out.Write(e.getMessage(), LogType.ERROR);
					//e.printStackTrace();
					if(e.getErrorCode()==1146){
						try {
							Out.Write("Creating or altering table", LogType.DEBUG);
							MySqlHelper.GenerateTable(request, con);
							Out.Write("Retry SQL command.", LogType.DEBUG);
							Store(request,commadtype);
							status=new DataResponse(command.getStorageObject().getRaw());
							return status;
							
						} catch (SQLException e1) {
							// TODO Auto-generated catch block
							status =new DataResponse(e1);
							return status;
							
						}catch (Exception e2) {
							// TODO: handle exception
							status =new DataResponse(e2);
							return status;
						}
					}
				}
				
			}
			break;
		case UpdateRecord:
			String updateSQL =MySqlHelper.GetUpdate(request);
			try {
				con.createStatement().executeUpdate(updateSQL);
				status=new DataResponse(command.getStorageObject().getRaw());
				return status;
			} catch (SQLException e) {
				Out.Write(e.getErrorCode(), LogType.ERROR);
				Out.Write(e.getMessage(), LogType.ERROR);
				//e.printStackTrace();
				try {
					Out.Write("Creating or altering table", LogType.DEBUG);
					MySqlHelper.GenerateTable(request, con);
					Out.Write("Retry SQL command.", LogType.DEBUG);
					Store(request,commadtype);
					status=new DataResponse(command.getStorageObject().getRaw());
					return status;
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					status =new DataResponse(e1);
					return status;
					
				}catch (Exception e2) {
					// TODO: handle exception
					status =new DataResponse(e2);
					return status;
				}
			}
			//break;
		
		}
		status =new DataResponse(new Exception("Error Database Conenection is not established"));
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
	public DataResponse[] Store(DataRequest request) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public DataResponse Delete(DataRequest request) {
		DataResponse status;
		DataCommand command = request.getDataCommand();
		String updateSQL =MySqlHelper.GetUpdate(request);
		try {
			con.createStatement().executeUpdate(updateSQL);
			status=new DataResponse(command.getStorageObject().getRaw());
			return status;
		} catch (SQLException e) {
			Out.Write(e.getErrorCode(), LogType.ERROR);
			Out.Write(e.getMessage(), LogType.ERROR);
		}
		
		
		status =new DataResponse(new Exception("Error Database Conenection is not established"));
		return status;
		
	}

	@Override
	public DataResponse[] Delete(String Name, Object[] Objs) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <T> ArrayList<T> Retrive(DataRequest request,Class<T> c) {
		DataCommand command = request.getDataCommand();
		ArrayList<T> newlistOfRecords =new ArrayList<T>();
		
		try {
			if(isDbConnected()){
				
				ResultSet rs=con.prepareStatement(MySqlHelper.GetSelect(request)).executeQuery();
				while (rs.next()) {
					ObjectWrapper obj = request.getDataCommand().newStorageObject();
					
					
					for (SchemaField field : command.getSchema().GetAll()) {
						Out.Write(rs.getString(field.getName()), LogType.DEBUG);
						 switch(field.getType()){
							case "int":
								obj.setValue(field.getName(), rs.getInt(field.getName()));
								break;
							case "float":
								obj.setValue(field.getName(), rs.getFloat(field.getName()));
								break;
							case "double":
								obj.setValue(field.getName(), rs.getDouble(field.getName()));
								break;
							case "short":
								obj.setValue(field.getName(), rs.getShort(field.getName()));
								break;
							case "long":
								obj.setValue(field.getName(), rs.getString(field.getName()));
								break;
							case "class java.lang.String":
							case "java.lang.String":
								obj.setValue(field.getName(), rs.getString(field.getName()));
								break;
							case "class java.util.Date":
							case "java.util.Date":
								java.sql.Date d=rs.getDate(field.getName());
								//java.util.Date d2=d;
								obj.setValue(field.getName(), d);
								break;
							case "boolean":
								obj.setValue(field.getName(), Boolean.parseBoolean(rs.getString(field.getName())));
								break;
							default:
								ObjectMapper ow = new ObjectMapper();
								
								/*
								try{
									Object x=ow.readValue(rs.getString(field.getName()), field.getType());
									obj.setValue(field.getName(), x);
								}catch (Exception e) {
									Out.Write(e.getMessage(), LogType.ERROR);
								}
								*/
								break;
				        
						 }
						
						
					}
					 newlistOfRecords.add((T)(obj.getRaw()));
			    }
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			request.setError(e);
		}
		//T a[]=new T[newlistOfRecords.size()];
		
		return newlistOfRecords;
	}

	
}
