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
 * to handling {@link Answer} objects.
 */
public class NewAnswerController extends MainController {
	/**
	 * Creates a new NewAnswerController.
	 * 
	 * @param p
	 *            Setter for {@link MainController#pending}.
	 * @param ctx
	 *            Setter for {@link MainController#ctx}.
	 */
	protected NewAnswerController(Pending p, Context ctx, Question q, Answer a) {
		super(p);
		this.ctx = ctx;
		contentTrio = new SimpleObjectTrio<Question, Answer, Reply>(q, a, null);
	}

	/**
	 * Factory method. Will generate a {@link Pending} object from the given
	 * parameters and save it in {@link MainController#pending}.
	 * 
	 * @param ctx
	 *            Setter for {@link MainController#ctx}.
	 * @param q
	 *            The unique ID of the {@link Question} parent (if any) of the
	 *            Content to be submitted.
	 * @param body
	 *            The data stored by the {@link Answer} to be created.
	 * @param bitmap
	 *            A picture attached to the {@link Answer} to be created.
	 * @param authorID
	 *            An identifier used to refer to the unique creator of the
	 *            {@link Answer}.
	 * @return The newly created instance of NewAnswerController.
	 */
	public static NewAnswerController create(Context ctx, Question q,
			String body, byte[] bitmap, String authorID,double[] coordinate, String location) {
		Answer newAnswer = new Answer(body, bitmap, authorID, coordinate, location);
		Pending newPending = new Pending(q.getId(), newAnswer);
		return new NewAnswerController(newPending, ctx, q, newAnswer);
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
