package com.sossgrid.authlib;


public class AuthCertificate {
	private String userid;
	private String email;
	private String domain;
	private String token;
	private String clientIP;
	//private Map.Entry<String,String> OtherData
	public AuthCertificate(String UserID,String Email,String Domain,String Token,String ClientIP){
		userid=UserID;
		email=Email;
		domain=Domain;
		token=Token;
		clientIP=ClientIP;
		
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

}
