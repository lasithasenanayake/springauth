/*
 * Java Authentication Service(Base : Spring)
 * Initiated By : lasithasenanayake
 * Date 		: 28/09/2016
 */
package com.sossgrid;


import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.Context;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.sossgrid.authlib.*;
import com.sossgrid.common.DataFunction;
import com.sossgrid.datastore.Connector;
import com.sossgrid.datastore.DataStoreCommandType;
import com.sossgrid.datastore.StatusMessage;
import com.sossgrid.exceptions.ServiceException;
import com.sossgrid.exceptions.UnAutherizedException;

@Controller
public class AuthService {
	
	//Login Represents Authenticating users 
	@RequestMapping(value="/login/{Email}/{Password}/{Domain}")
	public @ResponseBody AuthCertificate Login(
			@PathVariable String Email,
			@PathVariable String Password,
			@PathVariable String Domain,
			@Context HttpServletRequest req,
			@Context HttpServletResponse response) throws UnAutherizedException,ServiceException{
		try{
			Connector c=new Connector();
			
			HashMap<String, Object> query=new HashMap<String, Object>();
			query.put("email", Email);
			ArrayList<UserProfile> users= c.<UserProfile>Retrive("users", query, UserProfile.class);
			if(users.size()==1){
				if(users.get(0).getPassword().equals(Password)){
					HashMap<String, Object> Otherdata =new HashMap<String,Object>();
					Otherdata.put("RequestDomain", req.getServerName());
					String	ClientIP = req.getRemoteAddr();
					
					AuthHandler a =new AuthHandler(c);
					AuthCertificate authc=a.CreateSession(users.get(0), Domain, ClientIP, Otherdata);
					Cookie cookie=new Cookie("sosskey", authc.getToken());
					cookie.setPath("/");
					//cookie.setDomain(authc.getDomain());
					response.addCookie(cookie);
					
					return a.CreateSession(users.get(0), Domain, ClientIP, Otherdata);
				}else{
					throw new UnAutherizedException("email or password Incorrect.");
				}
			}else{
				throw new UnAutherizedException("email or password Incorrect.");
			}
		}catch(Exception ex){
			throw new ServiceException(ex.getMessage());
		}
	}
	
	//GetSession Represents validating security token or obtaining a new token for a specific domain
	@RequestMapping(value="/getsession/{Token}")
	public @ResponseBody AuthCertificate GetSession(
			@PathVariable String Token
			) throws UnAutherizedException,ServiceException{
		try{
			AuthHandler a =new AuthHandler();
			return a.GetSession(Token);
		}catch(UnAutherizedException e){
			throw e;
		}catch(Exception e){
			throw new ServiceException(e.getMessage());
		}
		
	}
	
	//GetSession Represents validating security token or obtaining a new token for a specific domain
		@RequestMapping(value="/getsession/")
		public @ResponseBody AuthCertificate GetSession(@CookieValue("sosskey") String fooCookie,boolean test) throws UnAutherizedException,ServiceException{
			try{
				AuthHandler a =new AuthHandler();
				return a.GetSession(fooCookie);
			}catch(UnAutherizedException e){
				throw e;
			}catch(Exception e){
				throw new ServiceException(e.getMessage());
			}
			
		}
	
	@RequestMapping(value="/getsession/{Token}/{Domain}")
	public @ResponseBody AuthCertificate GetSession(
			@PathVariable String Token,
			@PathVariable String Domain,
			@Context HttpServletRequest req
			) throws UnAutherizedException,ServiceException{
		try{
			AuthHandler a =new AuthHandler();
			AuthCertificate auth=a.GetSession(Token);
			if(auth.getDomain().equals(Domain.toLowerCase())){
				return auth;
			}else{
				String	ClientIP = req.getRemoteAddr();
				return a.CreateSession(auth, Domain, ClientIP);
			}
		}catch(UnAutherizedException e){
			throw e;
		}catch(Exception e){
			throw new ServiceException(e.getMessage());
		}
		
	}
	
	//CreateUser Represents Creation of the user 
	@PostMapping(value="/createuser/")
	public @ResponseBody UserProfile CreateUser(
			@RequestBody UserProfile User
			) throws ServiceException{
		
		//UserProfile u=new UserProfile(User.getUserid())
		try{
			Connector c=new Connector();
			HashMap<String, Object> query=new HashMap<String, Object>();
			query.put("email", User.getEmail());
			ArrayList<UserProfile> users= c.<UserProfile>Retrive("users", query, UserProfile.class);
			if(users.size()==0){
				User.setUserid(DataFunction.GetGUID());
				User.setEmail(User.getEmail().toLowerCase());
				StatusMessage st= c.Store("users", User, DataStoreCommandType.InsertRecord);
				if(!st.isError()){
					return User;
				}else{
					throw new ServiceException(st.getMessage());
				}
			}else{
				throw new ServiceException("Already User Registered");
			}
		}catch(Exception ex){
			throw new ServiceException(ex.getMessage());
		}
		
	}
	
	@RequestMapping(value="/resetpassword/{oldpassword}/{newpassword}")
	public @ResponseBody boolean ResetPassword(
			@PathVariable String oldpassword,
			@PathVariable String newpassword,
			@PathVariable String confirmpassword,
			@CookieValue("sosskey") String sossCookie
			) throws UnAutherizedException,ServiceException{
		try{
			AuthHandler a =new AuthHandler();
			AuthCertificate auth=a.GetSession(sossCookie);
			
			return false;
		}catch(Exception e){
			throw new ServiceException(e.getMessage());
		}
	}
	

	//CreateUser Represents Activate of the user account
	@RequestMapping(value="/activate/{Token}")
	public @ResponseBody AuthCertificate Activate(
			@PathVariable String Token
			) throws UnAutherizedException{
		throw new UnAutherizedException("Not Implemented");
	}
}
