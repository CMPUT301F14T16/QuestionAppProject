package ca.ualberta.cmput301f14t16.easya.Controller;

import android.content.Context;
import ca.ualberta.cmput301f14t16.easya.Exceptions.NoInternetException;
import ca.ualberta.cmput301f14t16.easya.Model.Pending;
import ca.ualberta.cmput301f14t16.easya.Model.PixelBitmap;
import ca.ualberta.cmput301f14t16.easya.Model.Question;

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
	protected NewQuestionController(Pending p, Context ctx) {
		super(p);
		this.ctx = ctx;
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
	 * @param pixelBitmap
	 *            A picture attached to the {@link Question} to be created.
	 * @param authorID
	 *            An identifier used to refer to the unique creator of the
	 *            {@link Question}.
	 * @return The newly created instance of NewQuestionController.
	 */
	public static NewQuestionController create(Context ctx, String title,
			String body, PixelBitmap pixelBitmap, String authorID) {
		Question newQuestion = new Question(title, body, pixelBitmap, authorID);
		Pending newPending = new Pending(newQuestion);
		return new NewQuestionController(newPending, ctx);
	}

	/**
	 * Calls super.submit() if an Internet connection is found, and
	 * super.submitOffline() if not.
	 * 
	 * @see ca.ualberta.cmput301f14t16.easya.Controller.MainController#submit()
	 */
	public boolean submit() {
		try {
			return super.submit();
		} catch (NoInternetException ex) {
			super.submitOffline();
			return true;
		} catch (Exception ex) {
			return false;
		}
	}
}