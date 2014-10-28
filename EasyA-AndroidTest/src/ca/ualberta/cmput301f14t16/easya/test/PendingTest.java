package ca.ualberta.cmput301f14t16.easya.test;

import android.test.ActivityInstrumentationTestCase2;
import android.util.Log;
import ca.ualberta.cmput301f14t16.easya.*;

import java.util.Date;
import java.util.List;
import junit.framework.TestCase;


public class PendingTest {
	
	private static final String LOG_TAG = "PendingTest";
	private Pending pending;
	private Date date;
	
	public void setUp() {
		pending = new Pending();
		Question q = new Question("Title Submission Test", 
				"Body of Question", "test@ualberta.ca");
		
		Answer a = new Answer("Body of answer", "someone@ualberta.ca");
		q.addAnswer(a);
		
		Reply r = new Reply("Body of reply", "reply@ualberta.ca");
		a.addReply(r);
		q.addReply(r);
		pending.addContent(q);
		
		
	}
	
	public void testGetContent() {
		assert pending.getContent() != null;
	}
	
	public void testGetBody() {
		assert pending.getContent().getBody() != null;
	}
	
	public void testGetAuthorId() {
		assert pending.getContent().getAuthorId() != null;
		assert pending.getContent().getAuthorId() == "test@ualberta.ca";
	}
	
	public void testgetDate() {
		assert pending.getContent().getDate() != null;
	}
}
