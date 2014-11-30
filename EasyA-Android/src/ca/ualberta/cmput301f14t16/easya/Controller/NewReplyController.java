package ca.ualberta.cmput301f14t16.easya.Controller;

import android.content.Context;
import ca.ualberta.cmput301f14t16.easya.Exceptions.NoInternetException;
import ca.ualberta.cmput301f14t16.easya.Model.Answer;
import ca.ualberta.cmput301f14t16.easya.Model.Pending;
import ca.ualberta.cmput301f14t16.easya.Model.Question;
import ca.ualberta.cmput301f14t16.easya.Model.Reply;
import ca.ualberta.cmput301f14t16.easya.Model.SimpleObjectTrio;

/**
 * Extends {@link MainController} to provide functionality specifically tailored
 * to handling {@link Reply} objects.
 */
public class NewReplyController extends MainController {
	/**
	 * Creates a new NewReplyController.
	 * 
	 * @param p
	 *            Setter for {@link MainController#pending}.
	 * @param ctx
	 *            Setter for {@link MainController#ctx}.
	 */
	protected NewReplyController(Pending p, Context ctx, Question q, Answer a, Reply r) {
		super(p);
		this.ctx = ctx;
		contentTrio = new SimpleObjectTrio<Question, Answer, Reply>(q, a, r);
	}

	/**
	 * Factory method. Will generate a {@link Pending} object from the given
	 * parameters and save it in {@link MainController#pending}.
	 * 
	 * @param ctx
	 *            Setter for {@link MainController#ctx}.
	 * @param q
	 *            The unique {@link Question} parent of the
	 *            {@link Reply} to be submitted.
	 * @param body
	 *            The data stored by the {@link Reply} to be created.
	 * @param authorID
	 *            An identifier used to refer to the unique creator of the
	 *            {@link Reply}.
	 * @return The newly created instance of NewReplyController.
	 */
	public static NewReplyController create(Context ctx, Question q,
			Answer a, String body, String authorID) {
		Reply newReply = new Reply(body, authorID);
		Pending newPending = new Pending(newReply, q != null ? q.getId() : null, a != null ? a.getId() : null);
		return new NewReplyController(newPending, ctx, q, a, newReply);
	}

	/**
	 * Calls super.submit() if an Internet connection is found, and
	 * super.submitOffline() if not.
	 * 
	 * @see ca.ualberta.cmput301f14t16.easya.Controller.MainController#submit()
	 */
	public boolean submit() {
		try {			
			if (super.submit()){
				saveOfflineContent();
				return true;
			}
			return false;
		} catch (NoInternetException ex) {
			super.submitOffline();
			saveOfflineContent();
			return true;
		} catch (Exception ex) {
			return false;
		}
	}
}