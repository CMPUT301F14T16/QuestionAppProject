package ca.ualberta.cmput301f14t16.easya.Controller;

import ca.ualberta.cmput301f14t16.easya.Model.Pending;
import ca.ualberta.cmput301f14t16.easya.Model.Question;
import ca.ualberta.cmput301f14t16.easya.View.MainActivity;


public class NewQuestionController {
	private String body;
	private String authorID;
	private String title;
	
	
	public NewQuestionController(String title, String body, String authorID){
		this.authorID = authorID;
		this.body = body ;
		this.title = title;
	}
	
	public Pending creatAndPushPending(){
		Question newQuestion = new Question(title, body, authorID);
		Pending newPending = new Pending(newQuestion);	
		MainActivity.mQueueThread.AddPendingToQueue(newPending);
		return newPending;
		
	}


}
