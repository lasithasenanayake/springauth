package com.sossgrid.mysql;

import java.util.HashMap;

import com.sossgrid.datastore.*;
import com.sossgrid.datastore.IDataConnector;
import com.sossgrid.datastore.StatusMessage;
import com.sossgrid.log.Out;
import com.sossgrid.log.Out.LogType;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

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
					PreparedStatement insertStatment =con.prepareStatement(insertSQL);
					insertStatment.executeQuery();
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
						
					}
				}
				
			}
			break;
		case UpdateRecord:
		
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
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public StatusMessage[] Delete(String Name, Object[] Objs) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <T> T[] Retrive(String Name, HashMap<String, Object> QueryField) {
		// TODO Auto-generated method stub
		return null;
	}

	
}
