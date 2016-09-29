package com.sossgrid.log;

public class Out {
	
	public enum Type {
		DEBUG, ERROR,INFORMATION
	}
	
	public static void Write(String str,Type Info){
		System.out.println(str);
	}
	
	public static void Write(Object obj,Type Info){
		System.out.println(obj);
	}

}
