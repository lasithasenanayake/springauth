package com.sossgrid.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value=HttpStatus.BAD_REQUEST)
public class ServiceException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2L;
	
	public ServiceException(String Reason){
		//this.reason
		super(Reason);
		
	}

}
