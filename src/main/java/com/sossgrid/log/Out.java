package com.sossgrid.log;

public class Out {
	
	public enum LogType {
		DEBUG, ERROR,INFORMATION
	}
	
	public static void Write(String str,LogType Info){
		System.out.println(str);
	}
	
	public static void Write(Object obj,LogType Info){
		System.out.println(obj);
	}

}
