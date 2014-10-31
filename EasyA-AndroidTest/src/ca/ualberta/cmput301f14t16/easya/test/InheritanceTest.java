package ca.ualberta.cmput301f14t16.easya.test;

import java.util.ArrayList;
import java.util.List;

import ca.ualberta.cmput301f14t16.easya.Model.Answer;
import ca.ualberta.cmput301f14t16.easya.View.MainActivity;
import ca.ualberta.cmput301f14t16.easya.Model.Question;
import ca.ualberta.cmput301f14t16.easya.Model.Topic;
import ca.ualberta.cmput301f14t16.easya.Model.User;
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
		List<Integer> NSC = null;
		NSC.add(1);
		NSC.add(2);
		User u = new User("commande@ualberta.ca", "Brett");
		
		// Create the question authored by that user.
		Question q = new Question("My Question", 
								  "Clarification", 
								  u.getEmail());
		
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
	
	public void testMixedTopicList() {
		List<Topic> topics = new ArrayList<Topic>();
		
		topics.add(new Question("Title", "Body", "name@email.com"));
		topics.add(new Answer("Body", "name@email.com"));
		
		assertTrue(topics.get(0) instanceof Question);
		assertTrue(topics.get(1) instanceof Answer);
		
	}
}
