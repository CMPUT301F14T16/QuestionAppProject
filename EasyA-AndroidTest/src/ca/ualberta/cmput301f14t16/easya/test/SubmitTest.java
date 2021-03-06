package ca.ualberta.cmput301f14t16.easya.test;


//This test submission of content into the server. 
//This test covers the Usecase of Submit Q, Submit A and Submit R
import junit.framework.TestCase; 
import java.io.IOException;
import android.content.Context;
import ca.ualberta.cmput301f14t16.easya.Controller.MainController;
import ca.ualberta.cmput301f14t16.easya.Exceptions.NoClassTypeSpecifiedException;
import ca.ualberta.cmput301f14t16.easya.Exceptions.NoInternetException;
import ca.ualberta.cmput301f14t16.easya.Model.Pending;
import ca.ualberta.cmput301f14t16.easya.Model.Data.ESClient;
import ca.ualberta.cmput301f14t16.easya.View.MainActivity;
import ca.ualberta.cmput301f14t16.easya.Model.Answer;
import ca.ualberta.cmput301f14t16.easya.Model.Question;
import ca.ualberta.cmput301f14t16.easya.Model.Queue;
import ca.ualberta.cmput301f14t16.easya.Model.Content;
import ca.ualberta.cmput301f14t16.easya.Model.Reply;
import ca.ualberta.cmput301f14t16.easya.Model.InternetCheck;
import ca.ualberta.cmput301f14t16.easya.Exceptions.*;


/**
 * 
 *  
 * @author Lingbo Tang,Klinton W Shmeid 
 *
 *
 */

public class SubmitTest extends TestCase {
	
	// Variable initialization can be done in another way
	private Pending pending;
	private Context ctx;
	public boolean sumitedOffline = false;
	private MainController mc;
	private String QId;
	private String AId;
	private String RId;
	private String QId2;
	private String AId2;
	private String RId2;
	private String QId3;
	private Pending pending1;
	private Pending pending2;
	private Pending pending3;
	private Pending pending4;
	private Pending pending5;
	private Pending pending6;
	private Pending pending7;
	
	
	
	public void testSubmitTest() throws NoInternetException {
		
		//Parse the value to the variabe for actural test 
		ESClient es = new ESClient();
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
		Question q3 = new Question("Title Submission Test3", "Body of Question3", "test3@ualberta.ca");
		QId3 = q3.getId();
		
		
		// answer and reply. To test all case, pass all case.
		pending1 = new Pending(q1);
		pending2 = new Pending(QId2,a2);
		pending3 = new Pending(r1,QId,AId);
		pending = new Pending(q3);
		
		//Test if they can be passed to pending (Online cache) at first
		assertTrue(pending1.getContent() == q1);
		assertTrue(pending2.getContent() == a2);
		assertTrue(pending3.getContent() == r1);
		assertTrue(pending.getContent() == q3);
		
		
		
		if (InternetCheck.haveInternet()) {
		try {
			//Try test if content are correctly submitted
				Content c = pending.getContent();
			if(c instanceof Question){
				assertTrue(es.submitQuestion((Question)c));
			}else if (c instanceof Answer){
				assertTrue(es.submitAnswer((Answer)c, pending.getQuestionId()));
			}else if (c instanceof Reply){
				if (pending.getAnswerId() != null && !pending.getAnswerId().isEmpty()){
					assertTrue(es.submitAnswerReply((Reply)c, pending.getQuestionId(), pending.getAnswerId()));
				}else{
					assertTrue(es.submitQuestionReply((Reply)c, pending.getQuestionId()));
				}
			}else{
				//Test if our app can identify the No Class type error
				throw new NoClassTypeSpecifiedException();
			}
		}catch(IOException ex){
			//Test if our app can identify the No Internet special case
			if (InternetCheck.haveInternet()){
				assertFalse(false);
				throw new NoInternetException();
			}
		}catch(NoClassTypeSpecifiedException ex){
			assertFalse(false);
		//Test if our app can identify the other special cases and do the right thing
		}catch(Exception ex){
			assertFalse(false);
		}
		}else{
			assertFalse(false);
		}
	}
}
