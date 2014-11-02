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
	private Pending pending1;
	private Pending pending2;
	private Pending pending3;
	private Pending pending4;
	private String QId;
	private String AId;
	private String RId;
	private String QId2;
	private String AId2;
	private String RId2;
	private Question q1;
	private Answer a1;
	private Reply r1;
	private Question q2;
	private Answer a2;
	private Reply r2;
	
	
	public void setUp() {
		Question q1 = new Question("Title Submission Test", "Body of Question", "test@ualberta.ca");
		
		Answer a1 = new Answer("Body of answer", "someone@ualberta.ca");
		q1.addAnswer(a1);
		
		Reply r1 = new Reply("Body of reply", "reply@ualberta.ca");
		a1.addReply(r1);
		q1.addReply(r1);
		QId = q1.getId();
		AId = a1.getId();
		RId = r1.getId();
		Question q2 = new Question("Title Submission Test2", "Body of Question2", "test2@ualberta.ca");
		Answer a2 = new Answer("Body of answer2","someone2@ualberta.ca");
		
		Reply r2 = new Reply("Body of reply","reply@ualberta.ca");
		a2.addReply(r2);
		q2.addReply(r2);
		QId2 = q2.getId();
		AId2 = a2.getId();
		RId2 = r2.getId();
		
		// TODO content is empty here, you should pass the question.
		pending1 = new Pending(q1);
		pending2 = new Pending(QId2,a2);
		pending3 = new Pending(r1,QId,AId);
		pending4 = new Pending();
		
	}
	
	public void testGetContent() {
		assertTrue(pending1.getContent() == q1);
		assertTrue(pending2.getContent() == a2);
		assertTrue(pending3.getContent() == r1);
	}
	
	public void testGetId() {
		assertEquals(pending1.getQuestionId(),QId);
		assertEquals(pending2.getAnswerId(),AId2);
		assertEquals(pending3.getQuestionId(),QId2);
		assertEquals(pending1.getQuestionId(),pending3.getQuestionId());
	}
	
	public void testPendingEquals() {
		assertTrue(pending1.equals(q1));
		assertFalse(pending1.equals(a2));
		assertFalse(pending1.equals(pending4));
	}
	
}
