/*
 * Java Authentication Service(Base : Spring)
 * Initiated By : lasithasenanayake
 * Date 		: 28/09/2016
 */
package com.sossgrid;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.sossgrid.authlib.*;
import com.sossgrid.exceptions.UnAutherizedException;

@Controller
public class AuthService {
	
	//Login Represents Authenticating users 
	@RequestMapping(value="/login/{Email}/{Password}/{Domain}")
	public @ResponseBody AuthCertificate Login(
			@PathVariable String Email,
			@PathVariable String Password,
			@PathVariable String Domain) throws UnAutherizedException{
		
		if(Email.equals("admin") && Password.equals("admin")){
			AuthCertificate authc=new AuthCertificate("123", Email, "Domain", "Token", "ClientIP");
			return authc;
		}else{
			throw new UnAutherizedException("email or password Incorrect.");
		}
	}
	
	//GetSession Represents validating security token or obtaining a new token for a specific domain
	@RequestMapping(value="/getsession/{Token}/{Domain}")
	public @ResponseBody AuthCertificate GetSession(
			@PathVariable String Token,
			@PathVariable String Domain
			) throws UnAutherizedException{
		throw new UnAutherizedException("Not Implemented");
	}
	
	//CreateUser Represents Creation of the user 
	@RequestMapping(value="/createuser/")
	public @ResponseBody AuthCertificate CreateUser(
			@PathVariable String Token,
			@PathVariable String Domain
			) throws UnAutherizedException{
		throw new UnAutherizedException("Not Implemented");
	}
	

	//CreateUser Represents Activate of the user account
	@RequestMapping(value="/activate/{Token}")
	public @ResponseBody AuthCertificate Activate(
			@PathVariable String Token
			) throws UnAutherizedException{
		throw new UnAutherizedException("Not Implemented");
	}
}
