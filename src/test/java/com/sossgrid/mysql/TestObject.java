package com.sossgrid.mysql;

import java.sql.Date;

import com.sossgrid.authlib.AuthCertificate;

public class TestObject {
	
	private String name;
	private Date dateTime;
	@Primary
	private int intvalue;
	private float floatvalue;
	private double doublevalue;
	private short shortvalue;
	private long longvalue;
	private boolean booleanvalue;
	private char charvalue;
	private AuthCertificate complexobject;
	
	

	public Date getDateTime() {
		return dateTime;
	}

	public void setDateTime(Date dateTime) {
		this.dateTime = dateTime;
	}

	public int getIntvalue() {
		return intvalue;
	}

	public void setIntvalue(int intvalue) {
		this.intvalue = intvalue;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public float getFloatvalue() {
		return floatvalue;
	}

	public void setFloatvalue(float floatvalue) {
		this.floatvalue = floatvalue;
	}

	public double getDoublevalue() {
		return doublevalue;
	}

	public void setDoublevalue(double doublevalue) {
		this.doublevalue = doublevalue;
	}

	public short getShortvalue() {
		return shortvalue;
	}

	public void setShortvalue(short shortvalue) {
		this.shortvalue = shortvalue;
	}

	public long getLongvalue() {
		return longvalue;
	}

	public void setLongvalue(long longvalue) {
		this.longvalue = longvalue;
	}

	public boolean isBooleanvalue() {
		return booleanvalue;
	}

	public void setBooleanvalue(boolean booleanvalue) {
		this.booleanvalue = booleanvalue;
	}

	public char getCharvalue() {
		return charvalue;
	}

	public void setCharvalue(char charvalue) {
		this.charvalue = charvalue;
	}

	public AuthCertificate getComplexobject() {
		return complexobject;
	}

	public void setComplexobject(AuthCertificate complexobject) {
		this.complexobject = complexobject;
	}

	
	
}
