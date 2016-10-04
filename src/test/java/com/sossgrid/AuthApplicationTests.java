package com.sossgrid;

import static org.junit.Assert.assertEquals;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.HashMap;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.mysql.jdbc.log.Log;
import com.sossgrid.authlib.AuthCertificate;
import com.sossgrid.datastore.DataStoreCommandType;
import com.sossgrid.datastore.StatusMessage;
import com.sossgrid.file.Store;
import com.sossgrid.log.Out;
import com.sossgrid.log.Out.LogType;
import com.sossgrid.mysql.MysqlConnector;
import com.sossgrid.mysql.MysqlTest;
import com.sossgrid.mysql.TestObject;

import junit.framework.Assert;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AuthApplicationTests {
	
	MysqlConnector mysql=new MysqlConnector();

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
		
		
		MysqlTest mytest =new MysqlTest();
		TestObject someObject=new TestObject();
		someObject.setComplexobject(authc);
		//System.out.println(someObject);
		String strInsert =mytest.GenerateInsert(someObject, "Nameddddd");
		System.out.println(strInsert.toString());
		String strCreate =mytest.GenerateCreate(someObject, "Name");//(someObject, "Nameddddd");
		java.util.Date myDate = new java.util.Date();
		System.out.println(myDate);
		System.out.println(new SimpleDateFormat("MM-dd-yyyy").format(myDate));
		System.out.println(new SimpleDateFormat("yyyyMMddHHmmss").format(myDate));
		System.out.println(myDate);
		//System.out.println(mytest.DateConvertSqlString(new java.util.Date()));
	}
	
	@Test 
	public void TestMySqlComponetInsert() throws Exception{
		HashMap<String,String> o=new HashMap<String,String>();
		o.put("server", "localhost");
		o.put("database", "supun");
		o.put("username", "root");
		o.put("password", "sossgrid");
		o.put("dataadapter", "com.sossgrid.mysql.MysqlConnector");
		
		mysql.CreateConnection(o);
		
		
		TestObject testobj=new TestObject();
		testobj.setBooleanvalue(true);
		testobj.setName("Lasitha Senanayake1");
		testobj.setDateTime(new java.util.Date());
		StatusMessage st=mysql.Store("supun3", testobj,DataStoreCommandType.InsertRecord);
		
		assertEquals(st.isError(), false);
		System.out.println(st.getMessage());
		
		//mysql.
		
		
	}
	
	

}
