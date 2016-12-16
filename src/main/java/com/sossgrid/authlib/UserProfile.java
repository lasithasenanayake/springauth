package com.sossgrid.authlib;

import java.util.HashMap;

import com.sossgrid.datastore.annotations.DataType;

public class UserProfile {
	@DataType(IsPrimary=true,MaxLen=50)
	private String userid;
	@DataType(MaxLen=100)
	private String email;
	@DataType(MaxLen=20)
	private String username;
	@DataType(MaxLen=200)
	private String name;
	@DataType(MaxLen=200)
	private String password;
	private HashMap<String,Object> otherdata;
	
	public UserProfile(){
		
	}
	
	public UserProfile(String UserID,String Email,String UserName,String Name,String PassWord,HashMap<String,Object> otherdata){
		setEmail(Email);
		setPassword(PassWord);
		setName(Name);
		setOtherdata(otherdata);
		setUserid(UserID);
	}
	
	public HashMap<String,Object> getOtherdata() {
		return otherdata;
	}
	public void setOtherdata(HashMap<String,Object> otherdata) {
		this.otherdata = otherdata;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getUserid() {
		return userid;
	}
	public void setUserid(String userid) {
		this.userid = userid;
	}
	
}
