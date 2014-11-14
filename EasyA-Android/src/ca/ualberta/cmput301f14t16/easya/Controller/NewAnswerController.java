package ca.ualberta.cmput301f14t16.easya.Controller;

import android.content.Context;
import ca.ualberta.cmput301f14t16.easya.Exceptions.NoInternetException;
import ca.ualberta.cmput301f14t16.easya.Model.Answer;
import ca.ualberta.cmput301f14t16.easya.Model.Content;
import ca.ualberta.cmput301f14t16.easya.Model.Pending;
import ca.ualberta.cmput301f14t16.easya.Model.PixelBitmap;
import ca.ualberta.cmput301f14t16.easya.Model.Question;

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
	protected NewAnswerController(Pending p, Context ctx) {
		super(p);
		this.ctx = ctx;
	}

	/**
	 * Factory method. Will generate a {@link Pending} object from the given
	 * parameters and save it in {@link MainController#pending}.
	 * 
	 * @param ctx
	 *            Setter for {@link MainController#ctx}.
	 * @param qId
	 *            The unique ID of the {@link Question} parent (if any) of the
	 *            Content to be submitted.
	 * @param body
	 *            The data stored by the {@link Answer} to be created.
	 * @param pixelBitmap
	 *            A picture attached to the {@link Answer} to be created.
	 * @param authorID
	 *            An identifier used to refer to the unique creator of the
	 *            {@link Answer}.
	 * @return The newly created instance of NewAnswerController.
	 */
	public static NewAnswerController create(Context ctx, String qId,
			String body, PixelBitmap pixelBitmap, String authorID) {
		Answer newAnswer = new Answer(body, pixelBitmap, authorID);
		Pending newPending = new Pending(qId, newAnswer);
		return new NewAnswerController(newPending, ctx);
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
