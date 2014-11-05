package ca.ualberta.cmput301f14t16.easya.Controller;

import ca.ualberta.cmput301f14t16.easya.Model.Answer;
import ca.ualberta.cmput301f14t16.easya.Model.Pending;
import ca.ualberta.cmput301f14t16.easya.View.MainActivity;


public class NewAnswerController {
	private String questionID;
	private String body;
	private String authorID;
	
	
	public NewAnswerController(String questionID, String body, String authorID){
		this.authorID = authorID;
		this.body = body ;
		this.questionID=questionID;
	}
	
	public Pending creatAndPushPending(){
		Answer newAnswer = new Answer(body,authorID);
		Pending newPending = new Pending(questionID,newAnswer);	
		MainActivity.mQueueThread.AddPendingToQueue(newPending);
		return newPending;
		
	}

}
