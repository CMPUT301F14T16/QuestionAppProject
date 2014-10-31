package ca.ualberta.cmput301f14t16.easya.test;

import android.test.ActivityInstrumentationTestCase2; 
import android.util.Log;
import ca.ualberta.cmput301f14t16.easya.*;
import ca.ualberta.cmput301f14t16.easya.Model.*;
import ca.ualberta.cmput301f14t16.easya.test.*;

import java.util.Date;
import java.util.List;
import junit.framework.TestCase;


public class PendingTest extends TestCase{
	
	private static final String LOG_TAG = "PendingTest";
	private Pending pending;
	private Date date;
	private String QId;
	private String AId;
	private String RId;
	private Content content;
	
	
	public void setUp() {
		Question q = new Question("Title Submission Test", 
				"Body of Question", "test@ualberta.ca");
		
		Answer a = new Answer("Body of answer", "someone@ualberta.ca");
		q.addAnswer(a);
		
		Reply r = new Reply("Body of reply", "reply@ualberta.ca");
		a.addReply(r);
		q.addReply(r);
		QId = q.getId();
		AId = a.getId();
		RId = r.getId();
		
		// TODO content is empty here, you should pass the question.
		pending = new Pending(QId,AId,content);
	}
	
	public void testGetContent() {
		assertTrue(pending.getContent() == content);
	}
	
	public void testGetQuestionId() {
		assertEquals(pending.getQuestionId(),QId);
	}
	
	public void testGetAnwserId() {
		assertEquals(pending.getAnswerId(),AId);
	}
	
	public void testAddContent() {
		Content con = null;
		pending.addContent(con);
		assertSame(pending.getContent(),con);
	}
}
