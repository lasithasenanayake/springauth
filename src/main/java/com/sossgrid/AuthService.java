package com.sossgrid;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sossgrid.exceptions.UnAutherizedException;

@Controller
public class AuthService {
	
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
}
