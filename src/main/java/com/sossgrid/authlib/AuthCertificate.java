package com.sossgrid.authlib;

import java.util.HashMap;

import com.sossgrid.datastore.annotations.DataType;

public class AuthCertificate {
	@DataType(MaxLen=50)
	private String userid;
	@DataType(MaxLen=100)
	private String email;
	@DataType(MaxLen=100)
	private String domain;
	@DataType(IsPrimary=true,MaxLen=50)
	private String token;
	@DataType(MaxLen=30)
	private String clientIP;
	@DataType(MaxLen=5000)
	private String jwt;
	private HashMap<String,Object> otherdata;
	//private Map.Entry<String,String> OtherData
	public AuthCertificate(String UserID,String Email,String Domain,String Token,String ClientIP,String JWT,HashMap<String,Object> OtherData){
		userid=UserID;
		email=Email;
		domain=Domain;
		token=Token;
		clientIP=ClientIP;
		otherdata=OtherData;
		jwt =JWT;
		
	}
	
	public AuthCertificate(){
		
	}
	
	public String getUserid() {
		return userid;
	}
	public void setUserid(String userid) {
		this.userid = userid;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getDomain() {
		return domain;
	}
	public void setDomain(String domain) {
		this.domain = domain;
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public String getClientIP() {
		return clientIP;
	}
	public void setClientIP(String clientIP) {
		this.clientIP = clientIP;
	}

	public HashMap<String,Object> getOtherData() {
		return otherdata;
	}

	public void setOtherData(HashMap<String,Object> otherData) {
		otherdata = otherData;
	}

	public String getJwt() {
		return jwt;
	}

	public void setJwt(String jwt) {
		this.jwt = jwt;
	}

}
