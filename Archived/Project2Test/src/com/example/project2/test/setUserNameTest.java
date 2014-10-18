package com.example.project2.test;

import junit.framework.TestCase;
import android.view.View;
import java.util.ArrayList;

public class setUserNameTest extends TestCase {
	
	//String username = "";
	//String newusername = "Lingbo";
	MainModel mainmodel = new MainModel();
	mainModel.setUserName("123@sample.com","userName");
	assertNotNull(mainModel.getUserName());
	
	public void testPending() {
		MainModel mainModel= new MainModel();
		try{
			mainModel.setUserName("123@sample.com","userName");
		}
		catch (Exception e){
			mainModel.addPending("123@sample.com","userName");
		}
		Boolean connect=false;
		while !connect{
			try{
				mainModel.pushPending();
			}
			else{
				connect=true;
			}
		}
		assertEqual(Mainmodel.haspending(), false);	
		assertNotNull(Mainmodel.getUserName());
	}
	
}
