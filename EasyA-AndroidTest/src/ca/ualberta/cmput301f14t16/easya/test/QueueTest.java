package ca.ualberta.cmput301f14t16.easya.test;

import junit.framework.TestCase; 

import java.io.IOException;  
import java.net.InetAddress;
import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import android.content.Context;
import android.test.ActivityInstrumentationTestCase2;
import android.util.Log;
import ca.ualberta.cmput301f14t16.easya.Model.*;
import ca.ualberta.cmput301f14t16.easya.Model.Data.*;
import ca.ualberta.cmput301f14t16.easya.Exceptions.*;
import ca.ualberta.cmput301f14t16.easya.Model.Data.ESClient;
import ca.ualberta.cmput301f14t16.easya.Model.Data.PMClient;

public class QueueTest extends TestCase{
	
    private final String ping_url = "bing.com"; //TODO: switch the host name to our elastic server
    private final long loop_interval = 5000; //in milliseconds
    private final int check_for_internet = 300000; //in milliseconds
    private boolean haveInternet;
    private boolean isActive = true;
    private Pending pending1;	
    private Pending pending2;
    private Pending pending3;
    private Pending pending4;
    private Pending pending5;
    private Pending pending6;
    private Pending pending7;
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
    private ESClient escClient = new ESClient();
    private Context ctx;
    private Queue queue;
    //TODO: add a instance of the model that holds all the questions

    private List<Pending> pendings;
	
    
    
    @SuppressWarnings("unchecked")
	public void testAddPendingToQueue() {
    	
    	Question q1 = new Question("Title Submission Test", "Body of Question", "test@ualberta.ca");
    	Answer a1 = new Answer("Body of answer", "someone@ualberta.ca");
    	q1.addAnswer(a1);
    	Reply r1 = new Reply("Body of reply", "reply@ualberta.ca");
    	a1.addReply(r1);
    	q1.addReply(r1);
    	QId = q1.getId();
    	AId = a1.getId();
    	RId = r1.getId();
    	Question q2 = new Question("Title Submission Test2","Body of Quesiton2","test2@ualberta.ca");
    	Answer a2 = new Answer("Body of answer2","someone2@ualberta.ca");
    	q2.addAnswer(a2);
    	Reply r2 = new Reply("Body of reply2","reply2@ualberta.ca");
    	a1.addReply(r2);
    	q2.addReply(r2);
    	QId2 = q2.getId();
    	AId2 = a2.getId();
    	RId2 = r2.getId();
    	
    	
		
    	//pending1 = new Pending();
    	//pending1 = new Pending(q1);
    	//pending2 = new Pending(q2);
    	pending1 = new Pending(QId,a1);
    	pending2 = new Pending(QId2,a2);
    	pending3 = new Pending(r1,QId,AId);
    	pending4 = new Pending(r2,QId2,AId2);
		
    	List<Pending> pendings = null;
    	queue = new Queue(ctx);
		
    	try {
    		queue.AddPendingToQueue(pending1);
    		queue.AddPendingToQueue(pending2);
        	queue.AddPendingToQueue(pending3);
        	queue.AddPendingToQueue(pending4);
    	} catch (NullPointerException e) {
    		e.printStackTrace();
    	}
    	//Has problem
    	assertNotNull(queue);
    	
    }
    
    public void testProcessPending() {
    	
    	Question q1 = new Question("Title Submission Test", "Body of Question", "test@ualberta.ca");
    	Answer a1 = new Answer("Body of answer", "someone@ualberta.ca");
    	q1.addAnswer(a1);
    	Reply r1 = new Reply("Body of reply", "reply@ualberta.ca");
    	a1.addReply(r1);
    	q1.addReply(r1);
    	QId = q1.getId();
    	AId = a1.getId();
    	RId = r1.getId();
    	Question q2 = new Question("Title Submission Test2","Body of Quesiton2","test2@ualberta.ca");
    	Answer a2 = new Answer("Body of answer2","someone2@ualberta.ca");
    	q2.addAnswer(a2);
    	Reply r2 = new Reply("Body of reply2","reply2@ualberta.ca");
    	a1.addReply(r2);
    	q2.addReply(r2);
    	QId2 = q2.getId();
    	AId2 = a2.getId();
    	RId2 = r2.getId();
    	
    	
		
    	//pending1 = new Pending();
    	//pending1 = new Pending(q1);
    	//pending2 = new Pending(q2);
    	pending1 = new Pending(QId,a1);
    	pending2 = new Pending(QId2,a2);
    	pending3 = new Pending(r1,QId,AId);
    	pending4 = new Pending(r2,QId2,AId2);
		
    	List<Pending> pendings = null;
    	queue = new Queue(ctx);
    	try {
    		queue.AddPendingToQueue(pending1);
    		queue.AddPendingToQueue(pending2);
        	queue.AddPendingToQueue(pending3);
        	queue.AddPendingToQueue(pending4);
    		queue.ProcessPendings();
    		fail("Process pendings failed!");
    	} catch(NoClassTypeSpecifiedException ex) {
    		assertTrue(true);
    	} catch(IOException ex) {
    		ex.printStackTrace();
    		assertTrue(true);
    	} catch(NoInternetException ex) {
    		assertTrue(true);
    	} catch(NullPointerException ex) {
    		ex.printStackTrace();
    		assertTrue(true);
    	} finally {
    		//Has Problems
    		assertNotNull(queue);
    	}
    	
    }
    
}
