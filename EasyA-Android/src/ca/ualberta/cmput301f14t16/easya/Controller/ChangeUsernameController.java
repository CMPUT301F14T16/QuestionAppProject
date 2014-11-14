package ca.ualberta.cmput301f14t16.easya.Controller;

import java.io.IOException;

import ca.ualberta.cmput301f14t16.easya.Model.MainModel;
import ca.ualberta.cmput301f14t16.easya.Model.User;
import ca.ualberta.cmput301f14t16.easya.Model.Data.ESClient;

/**
 * Provides a simple controller used to change the username string associated
 * with a specific user id within the elastic search database.
 */
public class ChangeUsernameController {
	/**
	 * The new username string to replace the existing one in the elastic search
	 * database.
	 */
	private String username;
	/**
	 * The {@link User} object, with the associated user ID, that is to be
	 * modified.
	 */
	private User user;

	/**
	 * Protected constructor method.
	 * 
	 * @param username
	 *            Setter for {@link ChangeUsernameController#username}.
	 * @param user
	 *            Setter for {@link ChangeUsernameController#user}.
	 */
	protected ChangeUsernameController(String username, User user) {
		this.username = username;
		this.user = user;
	}

	/**
	 * Factory method.
	 * 
	 * @param username
	 *            Setter for {@link ChangeUsernameController#username}.
	 * @param user
	 *            Setter for {@link ChangeUsernameController#user}.
	 * @return A new ChangeUsernameController object with the given parameters.
	 */
	public static ChangeUsernameController create(String username, User user) {
		return new ChangeUsernameController(username, user);
	}

	/**
	 * Creates a new {@link ESClient} object and attempts to use it to modify
	 * the elastic search database by changing the username associated with
	 * {@link ChangeUsernameController#user} to the new username string,
	 * {@link ChangeUsernameController#username}.
	 * 
	 * @return True if the changes to the elastic search database were
	 *         successful, False if an exception was raised.
	 */
	public boolean submit() {
		try {
			ESClient es = new ESClient();
			if (es.setUsernameById(this.user.getId(), username)) {
				this.user.setUserName(username);
				MainModel.getInstance().saveMainUser(this.user);
				return true;
			} else
				return false;
		} catch (IOException ex) {
			return false;
		} catch (Exception ex) {
			return false;
		}
	}
}
