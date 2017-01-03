package com.sossgrid.exceptions;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value=HttpStatus.UNAUTHORIZED)
public class UnAutherizedException extends Exception{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public UnAutherizedException(String Reason){
		//this.reason
		super(Reason);
		
	}
	

	
	

}
