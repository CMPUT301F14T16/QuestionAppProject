package ca.ualberta.cmput301f14t16.easya.test;

import java.util.Date; 
import java.util.List; 
import java.util.UUID;
import android.test.ActivityInstrumentationTestCase2;
import junit.framework.TestCase;
import ca.ualberta.cmput301f14t16.easya.Model.User;

public class UserTest extends TestCase{
	
	private static final String LOG_TAG = "UserTest";
	private String username;
	private String email;
	private String userid;
	private List<Integer> submittedContent;
	private Date date;
	private User user;
	private String newname;
	
	
	//public void UserTestSetup() {
		
	//}
	
	public void testSetUsername() {
		username = "Lingbo";
		email = "lingbo19.tang@gmail.com";
		user = new User(email,username);
		newname = "Lingbo747";
		user.setUserName(newname);
		assertEquals(user.getUsername(),newname);
	}
	
	public void testGetEmail() {
		username = "Lingbo";
		email = "lingbo19.tang@gmail.com";
		user = new User(email,username);
		assertEquals(user.getEmail(),email);
	}
	
	public void testGetUserId() {
		username = "Lingbo";
		email = "lingbo19.tang@gmail.com";
		user = new User(email,username);
		userid = user.getId();
		assertEquals(user.getId(),userid);
	}
	
}
