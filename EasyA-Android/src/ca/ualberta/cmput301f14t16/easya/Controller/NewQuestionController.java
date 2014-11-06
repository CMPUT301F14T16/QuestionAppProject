package ca.ualberta.cmput301f14t16.easya.Controller;

import android.content.Context;
import ca.ualberta.cmput301f14t16.easya.Exceptions.MissingContentException;
import ca.ualberta.cmput301f14t16.easya.Exceptions.NoInternetException;
import ca.ualberta.cmput301f14t16.easya.Model.Pending;
import ca.ualberta.cmput301f14t16.easya.Model.Question;
import ca.ualberta.cmput301f14t16.easya.Model.Data.Cache;
import ca.ualberta.cmput301f14t16.easya.View.MainActivity;


public class NewQuestionController extends MainController {
	protected NewQuestionController(Pending p, Context ctx){
		super(p);
		this.ctx = ctx;
	}
	
	public static NewQuestionController create(Context ctx, String title, String body, String authorID) throws MissingContentException{
		if (title.equals(""))
			throw new MissingContentException("title");
		if (body.equals(""))
			throw new MissingContentException("body");
		if (authorID.equals(""))
			throw new MissingContentException("authorId");
		
		Question newQuestion = new Question(title, body, authorID);
		Pending newPending = new Pending(newQuestion);
		return new NewQuestionController(newPending, ctx);
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