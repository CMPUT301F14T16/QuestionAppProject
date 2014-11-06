package ca.ualberta.cmput301f14t16.easya.Controller;

import android.content.Context;
import ca.ualberta.cmput301f14t16.easya.Exceptions.MissingContentException;
import ca.ualberta.cmput301f14t16.easya.Exceptions.NoInternetException;
import ca.ualberta.cmput301f14t16.easya.Model.Pending;
import ca.ualberta.cmput301f14t16.easya.Model.Reply;

public class NewReplyController extends MainController {
	protected NewReplyController(Pending p, Context ctx){
		super(p);
		this.ctx = ctx;
	}
	
	public static NewReplyController create(Context ctx, String qId, String aId, String body, String authorID) throws MissingContentException{
		if (qId.equals(""))
			throw new MissingContentException("qId");
		if (aId.equals(""))
			throw new MissingContentException("aId");
		if (body.equals(""))
			throw new MissingContentException("body");
		if (authorID.equals(""))
			throw new MissingContentException("authorId");
		
		Reply newReply = new Reply(body, authorID);
		Pending newPending = new Pending(newReply, qId, aId);
		return new NewReplyController(newPending, ctx);
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