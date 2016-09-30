package com.sossgrid;

import java.util.HashMap;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.sossgrid.authlib.AuthCertificate;
import com.sossgrid.file.Store;
import com.sossgrid.mysql.MysqlTest;

import junit.framework.Assert;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AuthApplicationTests {

	@Test
	public void contextLoads() {
		HashMap<String,Object> map=new HashMap<String,Object>();
		map.put("JWT", "value");
		AuthCertificate authc=new AuthCertificate("123", "Email", "Domain", "Token", "ClientIP","",map);
		Store.ObjectWrite(authc, "FileName.txt");
		MysqlTest mysql=new MysqlTest();
		
		HashMap<String,String> o=new HashMap<String,String>();
		o.put("server", "localhost");
		o.put("database", "test");
		o.put("username", "root");
		o.put("password", "sossgrid");
		//System.out.println();
		try{
			mysql.ConnectDatabase(o);
		}catch (Exception e) {
			// TODO: handle exception
		}
		
		
		
		
	}

}
