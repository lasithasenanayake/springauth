package com.sossgrid.datatest;

import static org.junit.Assert.assertEquals;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.sossgrid.datastore.SossData;
import com.sossgrid.datastore.StoreOperation;
import com.sossgrid.datastore.DataResponse;
import com.sossgrid.mysql.TestObject;

//@RunWith(SpringRunner.class)
//@SpringBootTest
public class DataConnectorTest {
	/*
	@Test
	public void OpenNewSystemConnection() throws Exception {
			SossData c =new SossData();
			TestObject testobj=new TestObject();
			testobj.setBooleanvalue(true);
			testobj.setName(new SimpleDateFormat("yyyyMMddHHmmss").format(new java.util.Date()));
			//testobj.setDateTime(new java.util.Date());
			DataResponse st=c.Store("Connector_Table", testobj,StoreOperation.InsertRecord);
			System.out.println("Connection Error Message");
			System.out.println(st.getResponse());
			assertEquals(st.isSuccess(), true);
		
	}
	
	@Test
	public void OpenNewConnection() throws Exception {
		//try{
			SossData c =new SossData();
			TestObject testobj=new TestObject();
			testobj.setBooleanvalue(true);
			testobj.setName(new SimpleDateFormat("yyyyMMddHHmmss").format(new java.util.Date()));
			//testobj.setDateTime(new java.util.Date());
			DataResponse st=c.Store("Connector_Table_2", testobj,StoreOperation.InsertRecord);
			System.out.println("Connection Error Message");
			System.out.println(st.getResponse());
			assertEquals(st.isSuccess(), true);
			
			HashMap<String, Object> hasQuery =new HashMap<String,Object>();
			hasQuery.put("Name", testobj.getName());
			ArrayList<TestObject> t= c.<TestObject>Retrive("Connector_Table_2", hasQuery, TestObject.class);
			assertEquals(t.size(), 1);
	}*/
}
