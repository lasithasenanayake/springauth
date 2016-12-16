package com.sossgrid.datatest;

import static org.junit.Assert.assertEquals;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

<<<<<<< HEAD
import com.sossgrid.datastore.SossData;
=======
import com.sossgrid.datastore.SOSStore;
>>>>>>> 22f44b4f06afadea0e7ee1f9e65b18286b81653f
import com.sossgrid.datastore.StoreOperation;
import com.sossgrid.datastore.DataResponse;
import com.sossgrid.mysql.TestObject;

//@RunWith(SpringRunner.class)
//@SpringBootTest
public class DataConnectorTest {
	/*
	@Test
	public void OpenNewSystemConnection() throws Exception {
<<<<<<< HEAD
			SossData c =new SossData();
			TestObject testobj=new TestObject();
			testobj.setBooleanvalue(true);
			testobj.setName(new SimpleDateFormat("yyyyMMddHHmmss").format(new java.util.Date()));
			//testobj.setDateTime(new java.util.Date());
=======
			SOSStore c =new SOSStore();
			TestObject testobj=new TestObject();
			testobj.setBooleanvalue(true);
			testobj.setName(new SimpleDateFormat("yyyyMMddHHmmss").format(new java.util.Date()));
			testobj.setDateTime(new java.util.Date());
>>>>>>> 22f44b4f06afadea0e7ee1f9e65b18286b81653f
			DataResponse st=c.Store("Connector_Table", testobj,StoreOperation.InsertRecord);
			System.out.println("Connection Error Message");
			System.out.println(st.getResponse());
			assertEquals(st.isSuccess(), true);
		
	}
	
	@Test
	public void OpenNewConnection() throws Exception {
		//try{
<<<<<<< HEAD
			SossData c =new SossData();
			TestObject testobj=new TestObject();
			testobj.setBooleanvalue(true);
			testobj.setName(new SimpleDateFormat("yyyyMMddHHmmss").format(new java.util.Date()));
			//testobj.setDateTime(new java.util.Date());
=======
			SOSStore c =new SOSStore();
			TestObject testobj=new TestObject();
			testobj.setBooleanvalue(true);
			testobj.setName(new SimpleDateFormat("yyyyMMddHHmmss").format(new java.util.Date()));
			testobj.setDateTime(new java.util.Date());
>>>>>>> 22f44b4f06afadea0e7ee1f9e65b18286b81653f
			DataResponse st=c.Store("Connector_Table_2", testobj,StoreOperation.InsertRecord);
			System.out.println("Connection Error Message");
			System.out.println(st.getResponse());
			assertEquals(st.isSuccess(), true);
			
			HashMap<String, Object> hasQuery =new HashMap<String,Object>();
			hasQuery.put("Name", testobj.getName());
			ArrayList<TestObject> t= c.<TestObject>Retrive("Connector_Table_2", hasQuery, TestObject.class);
			assertEquals(t.size(), 1);
<<<<<<< HEAD
	}*/
=======
	}
>>>>>>> 22f44b4f06afadea0e7ee1f9e65b18286b81653f
}
