package android.support.v7.appcompat.test;

import junit.framework.TestCase;
import android.view.View;
import java.util.ArrayList;

public class setUserNameTest {
	
	String username = "";
	String newusername = "Lingbo";
	
	if (connection == 0) {
		assertTrue(setReconnect());
	}
		
	username.changeusername(newusername);
	assertTrue(username == newusername);
	
}
