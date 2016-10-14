package com.sossgrid.common;

import java.text.SimpleDateFormat;

public class DataFunction {

	public static long GetVersionID(){
		long versionid;
		versionid=Long.parseLong(new SimpleDateFormat("yyyyMMddHHmmss").format(new java.util.Date()));
		return versionid;
	}
	
	public static String GetGUID(){
		return java.util.UUID.randomUUID().toString().replace("-", "");
	}
}
