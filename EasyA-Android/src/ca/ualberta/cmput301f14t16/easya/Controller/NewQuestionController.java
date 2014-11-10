package ca.ualberta.cmput301f14t16.easya.Controller;

import android.content.Context;
import ca.ualberta.cmput301f14t16.easya.Exceptions.NoInternetException;
import ca.ualberta.cmput301f14t16.easya.Model.Pending;
import ca.ualberta.cmput301f14t16.easya.Model.PixelBitmap;
import ca.ualberta.cmput301f14t16.easya.Model.Question;

public class NewQuestionController extends MainController {
	protected NewQuestionController(Pending p, Context ctx){
		super(p);
		this.ctx = ctx;
	}
	
	public static NewQuestionController create(Context ctx, String title, String body, PixelBitmap pixelBitmap, String authorID){
		Question newQuestion = new Question(title, body, pixelBitmap, authorID);
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