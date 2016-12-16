package com.sossgrid.mysql;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import com.sossgrid.datastore.*;

public class SossStoreTest {

	public static void main (String[] args){
		testRetrieve();
	}
	
	private static void testRetrieve(){
		try {
			SossData d = new SossData();
			HashMap<String,Object> qData = new HashMap<>();
			qData.put("name", "supun");
			ArrayList<TestObject> dr = d.<TestObject>Retrive("testobject", qData, TestObject.class);
			for (TestObject to : dr){
				System.out.println(to.getName());
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private static void testStore(){
		try {
			SossData d = new SossData();
			TestObject to = new TestObject();
			to.setBooleanvalue(true);
			to.setName("dissanayake");
			to.setFloatvalue(10);
			to.setIntvalue(29);
			//to.setDateTime(new Date());
			
			DataResponse resp = d.Store("testobject", to, StoreOperation.InsertRecord);
			System.out.println(resp);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
