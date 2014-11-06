package ca.ualberta.cmput301f14t16.easya.Controller;

import android.content.Context;
import ca.ualberta.cmput301f14t16.easya.Exceptions.MissingContentException;
import ca.ualberta.cmput301f14t16.easya.Exceptions.NoInternetException;
import ca.ualberta.cmput301f14t16.easya.Model.Answer;
import ca.ualberta.cmput301f14t16.easya.Model.Pending;


public class NewAnswerController extends MainController {
	protected NewAnswerController(Pending p, Context ctx){
		super(p);
		this.ctx = ctx;
	}
	
	public static NewAnswerController create(Context ctx, String qId, String body, String authorID) throws MissingContentException{
		if (body.equals(""))
			throw new MissingContentException("body");
		if (authorID.equals(""))
			throw new MissingContentException("authorId");
		
		Answer newAnswer = new Answer(body, authorID);
		Pending newPending = new Pending(qId, newAnswer);
		return new NewAnswerController(newPending, ctx);
	}	
	
	public boolean submit(){
		try{
			return super.submit();
		}catch(NoInternetException ex){
			super.submitOffline();			
			return true;
		}catch(Exception ex){
			return false;
		}
	}
}
