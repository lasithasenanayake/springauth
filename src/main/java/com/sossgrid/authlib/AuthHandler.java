package com.sossgrid.authlib;

import java.util.ArrayList;
import java.util.HashMap;

import com.sossgrid.common.DataFunction;
import com.sossgrid.datastore.SossData;
import com.sossgrid.datastore.StoreOperation;
import com.sossgrid.exceptions.ServiceException;
import com.sossgrid.exceptions.UnAutherizedException;

public class AuthHandler {
	private SossData c =null;
	
	public AuthHandler(SossData Con){
		c=Con;
	}
	
	public AuthHandler() throws Exception{
		c=new SossData();
	}
	
	public  AuthCertificate CreateSession(
			UserProfile User,
			String Domain,
			String ClientIP ,
			HashMap<String, Object> Otherdata) throws UnAutherizedException{
		if(AuthrizedDomain(Domain, User.getUserid())){
		AuthCertificate session=new AuthCertificate(User.getUserid(), 
				User.getEmail(), 
				Domain.toLowerCase(), 
				DataFunction.GetGUID(), 
				ClientIP, 
				"JWT-Comming Soon", 
				Otherdata);
		c.Store("sessions", session, StoreOperation.InsertRecord);
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
			authcertificate.setDomain(Domain.toLowerCase());
			c.Store("sessions", authcertificate, StoreOperation.InsertRecord);
			return authcertificate;
		}else{
			throw new UnAutherizedException("Access not granted for  "+Domain);
		}
	}
	
	public AuthCertificate GetSession(String Token) throws UnAutherizedException,ServiceException{
		HashMap<String, Object> query=new HashMap<String, Object>();
		query.put("token", Token);
		try{
		ArrayList<AuthCertificate> sessions= c.<AuthCertificate>Retrive("sessions", query, AuthCertificate.class);
		if(sessions.size()!=0){
			return sessions.get(0);
		}else{
			throw new UnAutherizedException("Session not valied");
		}
		}catch(Exception e){
			throw new ServiceException(e.getMessage());
		}
	}

}
