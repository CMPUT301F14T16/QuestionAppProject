package ca.ualberta.cmput301f14t16.easya.Controller;

import java.io.IOException;

import org.apache.http.message.BasicNameValuePair;

import ca.ualberta.cmput301f14t16.easya.Model.User;
import ca.ualberta.cmput301f14t16.easya.Model.Data.ESClient;

/**
 * Provides a simple controller that toggles the upvote status of a
 * {@link ca.ualberta.cmput301f14t16.easya.Model.Question} or
 * {@link ca.ualberta.cmput301f14t16.easya.Model.Answer} object in the elastic
 * search database.
 */
public class UpvoteController {
	/**
	 * The ID of a {@link ca.ualberta.cmput301f14t16.easya.Model.Question} to be
	 * updated, or the ID of the parent of an
	 * {@link ca.ualberta.cmput301f14t16.easya.Model.Answer} to be updated.
	 */
	private String qId;
	/**
	 * The ID of an {@link ca.ualberta.cmput301f14t16.easya.Model.Answer} to be
	 * updated.
	 */
	private String aId;
	/**
	 * The ID of the user who's upvote is to be toggled.
	 */
	private String uId;

	/**
	 * Creates a new UpvoteController object.
	 * 
	 * @param qId
	 *            Setter for {@link UpvoteController#qId}
	 * @param aId
	 *            Setter for {@link UpvoteController#aId}
	 * @param uId
	 *            Setter for {@link UpvoteController#uId}
	 */
	protected UpvoteController(String qId, String aId, String uId) {
		this.qId = qId;
		this.aId = aId;
		this.uId = uId;
	}

	/**
	 * Factory method.
	 * 
	 * @param vp
	 *            A value pair containing the data for
	 *            {@link UpvoteController#qId} as a name, and the data for
	 *            {@link UpvoteController#aId} as a value.
	 * @param u
	 *            The user ID to be toggled.
	 * @return A new UpvoteController object with the given parameters.
	 */
	public static UpvoteController create(BasicNameValuePair vp, String u) {
		return new UpvoteController(vp.getName(), vp.getValue(), u);
	}

	/**
	 * Creates a new {@link ESClient} object and attempts to use it to modify
	 * the elastic search database. This method with use the {@link ESClient} to
	 * find the appropriate
	 * {@link ca.ualberta.cmput301f14t16.easya.Model.Answer} or
	 * {@link ca.ualberta.cmput301f14t16.easya.Model.Question} object, and
	 * toggle the upvote statuse associated with the appropriate {@link User}.
	 * 
	 * @return True if the changes to the elastic search database were
	 *         successful, False if an exception was raised.
	 */
	public boolean submit() {
		try {
			ESClient es = new ESClient();
			if (aId == null)
				return es.setQuestionUpvote(uId, qId);
			else
				return es.setAnswerUpvote(uId, qId, aId);
		} catch (IOException ex) {
			return false;
		} catch (Exception ex) {
			return false;
		}
	}
}
