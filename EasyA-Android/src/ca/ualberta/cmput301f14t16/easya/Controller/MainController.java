package ca.ualberta.cmput301f14t16.easya.Controller;

import java.io.IOException;

import android.content.Context;
import ca.ualberta.cmput301f14t16.easya.Exceptions.NoClassTypeSpecifiedException;
import ca.ualberta.cmput301f14t16.easya.Exceptions.NoInternetException;
import ca.ualberta.cmput301f14t16.easya.Model.Answer;
import ca.ualberta.cmput301f14t16.easya.Model.Content;
import ca.ualberta.cmput301f14t16.easya.Model.Question;
import ca.ualberta.cmput301f14t16.easya.Model.Pending;
import ca.ualberta.cmput301f14t16.easya.Model.Queue;
import ca.ualberta.cmput301f14t16.easya.Model.Reply;
import ca.ualberta.cmput301f14t16.easya.Model.Time;
import ca.ualberta.cmput301f14t16.easya.Model.Data.ESClient;

/**
 * Provides a simple controller used to add a {@link Content} object to the
 * elastic search database.
 */
public abstract class MainController {
	/**
	 * The object to be added to the database.
	 */
	protected Pending pending;
	protected Context ctx;
	/**
	 * Set to True if {@link MainController#submitOffline()} is called.
	 */
	public boolean submitedOffline = false;

	/**
	 * Creates a new MainController object.
	 * 
	 * @param c
	 *            setter for {@link MainController#pending}.
	 */
	protected MainController(Pending c) {
		this.pending = c;
	}

	/**
	 * Attempts to submit {@link MainController#pending} to the elastic search
	 * database. This method will initially attempt to check for an Internet
	 * connection using {@link Queue#haveInternetConnection()}, and will fail if
	 * it returns False.
	 * 
	 * @return True if {@link MainController#pending} was successfully submitted.
	 *         False if not.
	 * @throws NoInternetException
	 *             If the method's attempt to check for an Internet connection
	 *             using {@link Queue#haveInternetConnection()} returns False.
	 */
	protected boolean submit() throws NoInternetException {
		ESClient es = new ESClient();
		if (Queue.getInstance().haveInternetConnection()) {
			try {
				Content c = pending.getContent();				
				c.setDate(Time.getDate());
				System.out.println(Time.getDate());
				System.out.println(c.getDate());
				if (c instanceof Question) {
					return es.submitQuestion((Question) c);
				} else if (c instanceof Answer) {
					return es.submitAnswer((Answer) c, pending.getQuestionId());
				} else if (c instanceof Reply) {
					if (pending.getAnswerId() != null
							&& !pending.getAnswerId().isEmpty()) {
						return es.submitAnswerReply((Reply) c,
								pending.getQuestionId(), pending.getAnswerId());
					} else {
						return es.submitQuestionReply((Reply) c,
								pending.getQuestionId());
					}
				} else {
					throw new NoClassTypeSpecifiedException();
				}
			} catch (IOException ex) {
				if (Queue.getInstance().haveInternetConnection())
					return false;
				throw new NoInternetException();
			} catch (NoClassTypeSpecifiedException ex) {
				return false;
			} catch (Exception ex) {
				return false;
			}
		} else {
			throw new NoInternetException();
		}
	}

	/**
	 * Pushes {@link MainController#pending} to the currently running instance
	 * of {@link Queue}. Sets {@link MainController#submitedOffline} to True.
	 */
	protected void submitOffline() {
		Queue.getInstance().AddPendingToQueue(pending);
		submitedOffline = true;
	}

	/**
	 * @return {@link MainController#pending}'s {@link Pending#questionId} field
	 *         if the contained content is an {@link Answer} with a parent
	 *         {@link Question}, or the contained Content's own ID otherwise.
	 */
	public String getQuestionId() {
		try {
			return this.pending.getQuestionId() == null ? this.pending
					.getContent().getId() : this.pending.getQuestionId();
		} catch (Exception ex) {
			return "";
		}
	}
}