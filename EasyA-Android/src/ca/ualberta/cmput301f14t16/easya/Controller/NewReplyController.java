package ca.ualberta.cmput301f14t16.easya.Controller;

import ca.ualberta.cmput301f14t16.easya.Model.Pending;
import ca.ualberta.cmput301f14t16.easya.Model.Reply;
import ca.ualberta.cmput301f14t16.easya.View.MainActivity;

public class NewReplyController {
	private String body;
	private String authorID;
	private String questionID;
	private String answerID;
	
	
	public NewReplyController(String questionID, String answerID, String body, String authorID){
		this.authorID = authorID;
		this.body = body ;
		this.questionID = questionID;
		this.answerID = answerID;
	}
	
	public Pending creatAndPushPending(){
		Reply newReply = new Reply(body, authorID);
		Pending newPending = new Pending(newReply, questionID, answerID);	
		MainActivity.mQueueThread.AddPendingToQueue(newPending);
		return newPending;
		
	}


}
