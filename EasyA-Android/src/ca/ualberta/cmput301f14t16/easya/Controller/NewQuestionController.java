package ca.ualberta.cmput301f14t16.easya.Controller;

import android.content.Context;
import ca.ualberta.cmput301f14t16.easya.Exceptions.NoInternetException;
import ca.ualberta.cmput301f14t16.easya.Model.Pending;
import ca.ualberta.cmput301f14t16.easya.Model.Question;
import ca.ualberta.cmput301f14t16.easya.Model.Answer;
import ca.ualberta.cmput301f14t16.easya.Model.Reply;
import ca.ualberta.cmput301f14t16.easya.Model.SimpleObjectTrio;

/**
 * Extends {@link MainController} to provide functionality specifically tailored
 * to handling {@link Question} objects.
 */
public class NewQuestionController extends MainController {
	/**
	 * Creates a new NewQuestionController.
	 * 
	 * @param p
	 *            Setter for {@link MainController#pending}.
	 * @param ctx
	 *            Setter for {@link MainController#ctx}.
	 */
	protected NewQuestionController(Pending p, Context ctx, Question q) {
		super(p);
		this.ctx = ctx;
		contentTrio = new SimpleObjectTrio<Question, Answer, Reply>(q, null, null);
	}

	/**
	 * Factory method. Will generate a {@link Pending} object from the given
	 * parameters and save it in {@link MainController#pending}.
	 * 
	 * @param ctx
	 *            Setter for {@link MainController#ctx}.
	 * @param title
	 *            A title String for the newly created {@link Question} to be
	 *            pushed.
	 * @param body
	 *            The data stored by the {@link Question} to be created.
	 * @param bitmap
	 *            A picture attached to the {@link Question} to be created.
	 * @param authorID
	 *            An identifier used to refer to the unique creator of the
	 *            {@link Question}.
	 * @return The newly created instance of NewQuestionController.
	 */
	public static NewQuestionController create(Context ctx, String title,
			String body, byte[] bitmap, String authorID, double[] coordinate, String location) {
		Question newQuestion = new Question(title, body, bitmap, authorID, coordinate, location);		
		Pending newPending = new Pending(newQuestion);
		return new NewQuestionController(newPending, ctx, newQuestion);
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