package com.sossgrid.authlib;

import java.util.ArrayList;
import java.util.HashMap;

import com.sossgrid.common.DataFunction;
import com.sossgrid.datastore.Connector;
import com.sossgrid.datastore.DataStoreCommandType;
import com.sossgrid.exceptions.UnAutherizedException;

public class AuthHandler {
	private Connector c =null;
	
	public AuthHandler(Connector Con){
		c=Con;
	}
	
	public AuthHandler() throws Exception{
		c=new Connector();
	}
	
	public  AuthCertificate CreateSession(
			UserProfile User,
			String Domain,
			String ClientIP ,
			HashMap<String, Object> Otherdata) throws UnAutherizedException{
		if(AuthrizedDomain(Domain, User.getUserid())){
		AuthCertificate session=new AuthCertificate(User.getUserid(), 
				User.getEmail(), 
				Domain, 
				DataFunction.GetGUID(), 
				ClientIP, 
				"JWT-Comming Soon", 
				Otherdata);
		c.Store("sessions", session, DataStoreCommandType.InsertRecord);
		return session;	
		}else{
			throw new UnAutherizedException("Access not granted for  "+Domain);
		}
		
	}
	
	public boolean AuthrizedDomain(String Domain,String ID){
		return true;
	}
	
	public AuthCertificate CreateSession(AuthCertificate authcertificate,String Domain,String ClientIP) throws UnAutherizedException{
		if(AuthrizedDomain(Domain, authcertificate.getUserid())){
			authcertificate.setToken(DataFunction.GetGUID());
			authcertificate.setClientIP(ClientIP);
			c.Store("sessions", authcertificate, DataStoreCommandType.InsertRecord);
			return authcertificate;
		}else{
			throw new UnAutherizedException("Access not granted for  "+Domain);
		}
	}
	
	public AuthCertificate GetSession(String Token) throws UnAutherizedException{
		HashMap<String, Object> query=new HashMap<String, Object>();
		ArrayList<AuthCertificate> sessions= c.<AuthCertificate>Retrive("sessions", query, AuthCertificate.class);
		if(sessions.size()!=0){
			return sessions.get(0);
		}else{
			throw new UnAutherizedException("Session not valied");
		}
	}

}
