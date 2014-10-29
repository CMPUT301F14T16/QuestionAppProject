package ca.ualberta.cmput301f14t16.easya.test;

import java.util.Date;
import java.util.List; 
import java.util.UUID;
import android.test.ActivityInstrumentationTestCase2;
import junit.framework.TestCase;
import ca.ualberta.cmput301f14t16.easya.User;

public class UserTest extends TestCase{
	
	private static final String LOG_TAG = "PendingTest";
	private String username;
	private String email;
	private String userid;
	private List<Integer> submittedContent;
	private Date date;
	private User user;
	private String newname;
	private int a;
	private int b;
	private int c;
	
	public void setup() {
		username = "Lingbo";
		email = "lingbo19.tang@gmail.com";
		a = 1;
		b = 2;
		c = 3;
		submittedContent.add(a);
		submittedContent.add(b);
		submittedContent.add(c);
		user = new User(email,username,submittedContent);
	}
	
	public void testSetUsername() {
		newname = "Lingbo747";
		user.setUserName(newname);
		assertEquals(user.getUserName(),newname);
	}
	
	public void testGetEmail() {
		assertEquals(user.getEmail(),email);
	}
	
	public void testGetUserId() {
		assertEquals(user.getUserId(),userid);
	}
	
	public void testGetNsubmittedContent() {
		assertSame(user.getNsubmittedContent().get(1),1);
	}
}
