package ca.ualberta.cmput301f14t16.easya.test;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import ca.ualberta.cmput301f14t16.easya.MainActivity;
import ca.ualberta.cmput301f14t16.easya.Question;
import ca.ualberta.cmput301f14t16.easya.Topic;
import ca.ualberta.cmput301f14t16.easya.User;
import android.test.ActivityInstrumentationTestCase2;

/**
 * 
 * @author Brett Commandeur
 *
 */
public class InheritanceTest extends ActivityInstrumentationTestCase2<MainActivity> {

	public InheritanceTest() {
		super(MainActivity.class);
	}
	
	public void testQuestionCreation () {
		// Create a user.
		User u = new User("commande@ualberta.ca", "Brett");
		String id = UUID.randomUUID().toString();
		
		// Create the question authored by that user.
		Question q = new Question("My Question", 
								  "Clarification", 
								  u.getEmail(),
								  new Date(), 
								  id);
		
		// Ensure question has been created.
		assertNotNull(q.getId());
		assertTrue(q.getBody().equals("Clarification"));
		
		// Mix question in with answers.
		List<Topic> qlist = new ArrayList<Topic>();
		qlist.add(q);
		
		// Get back a question or answer.
		Topic p = qlist.get(0);
		
		// Check to see if you're a question or answer.
		assertTrue(p instanceof Question);
	}
}
