package ca.ualberta.cmput301f14t16.easya.test;

import java.io.IOException; 
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import android.test.ActivityInstrumentationTestCase2;
import junit.framework.TestCase;
import ca.ualberta.cmput301f14t16.easya.*;

public class QueueTest extends TestCase{
	
	private final String ping_url = "bing.com"; //TODO: switch the host name to our elastic server
	private final long loop_interval = 5000; //in milliseconds
	private final int check_for_internet = 300000; //in milliseconds
	private Date lastCheck;
    private boolean haveInternet;
    private boolean isActive = true;
    private Date date;
	private String QId;
	private String AId;
	private String RId;
    private ESClient escClient = new ESClient();
    private Pending pending1;
    private Pending pending2;
    private Pending pending3;
    private Pending pending4;
    
    //TODO: add a instance of the model that holds all the questions

    private List<Pending> pendings;
	
    
    
    public void testAddPendingToQueue() {
    	
    	Question q1 = new Question("Title Submission Test", 
				"Body of Question", "test@ualberta.ca");
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
		
		pending1 = new Pending(QId,AId,q1);
		pending2 = new Pending(q2);
		pending3 = new Pending(QId,a1);
		pending4 = new Pending(r1,QId,AId);
		
		pendings = (List<Pending>) new Pending();
		pendings.add(pending1);
		pendings.add(pending2);
		pendings.add(pending3);
		pendings.add(pending4);
		
		Queue queue = new Queue();
		
    	queue.AddPendingToQueue(pending1);
    	queue.AddPendingToQueue(pending2);
    	queue.AddPendingToQueue(pending3);
    	queue.AddPendingToQueue(pending4);
    	
    	assertEquals((ArrayList<Pending>)queue.get(2) == pending2);
    	
    }
    

}
