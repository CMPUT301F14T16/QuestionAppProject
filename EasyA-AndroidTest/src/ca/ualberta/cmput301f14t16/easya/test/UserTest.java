package ca.ualberta.cmput301f14t16.easya.test;

import java.util.Date; 
import java.util.List; 
import java.util.UUID;
import android.test.ActivityInstrumentationTestCase2;
import junit.framework.TestCase;
import ca.ualberta.cmput301f14t16.easya.Model.User;

//This test covers the use case of Set Username
//Simply test User creation and change username
public class UserTest extends TestCase{
	
	private static final String LOG_TAG = "UserTest";
	private String username;
	private String email;
	private String userid;
	private List<Integer> submittedContent;
	private Date date;
	private User user;
	private String newname;
	
	
	//Fixed path building problems
	
	//Test if we can set user name again
	public void testSetUsername() {
		username = "Lingbo";
		email = "lingbo19.tang@gmail.com";
		user = new User(email,username);
		newname = "Lingbo747";
		user.setUserName(newname);
		assertEquals(user.getUsername(),newname);
	}
	
	//Test if we can get the user's email
	public void testGetEmail() {
		username = "Lingbo";
		email = "lingbo19.tang@gmail.com";
		user = new User(email,username);
		assertEquals(user.getEmail(),email);
	}
	
	
	//Test if we can set userid
	public void testGetUserId() {
		username = "Lingbo";
		email = "lingbo19.tang@gmail.com";
		user = new User(email,username);
		userid = user.getId();
		assertEquals(user.getId(),userid);
	}
	
	//Test if we can get Creation date
	public void testCreateOn() {
		username = "Lingbo";
		email = "lingbo19.tang@gmail.com";
		user = new User(email,username);
		date = user.getCreatedOn();
		assertEquals(user.getCreatedOn(),date); 
	}
	
}
