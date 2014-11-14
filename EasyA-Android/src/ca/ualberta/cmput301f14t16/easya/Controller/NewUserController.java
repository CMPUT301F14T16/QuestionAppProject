package ca.ualberta.cmput301f14t16.easya.Controller;

import java.io.IOException;

import ca.ualberta.cmput301f14t16.easya.Exceptions.NoInternetException;
import ca.ualberta.cmput301f14t16.easya.Model.Content;
import ca.ualberta.cmput301f14t16.easya.Model.MainModel;
import ca.ualberta.cmput301f14t16.easya.Model.Pending;
import ca.ualberta.cmput301f14t16.easya.Model.Queue;
import ca.ualberta.cmput301f14t16.easya.Model.User;
import ca.ualberta.cmput301f14t16.easya.Model.Data.ESClient;

/**
 * Provides a simple controller used to add a {@link User} object to the elastic
 * search database.
 */
public class NewUserController {
	/**
	 * The {@link User} to be added to the database.
	 */
	private User u;

	/**
	 * Creates a new NewUserController object.
	 * 
	 * @param u
	 *            setter for {@link NewUserController#u}.
	 */
	protected NewUserController(User u) {
		this.u = u;
	}

	/**
	 * Factory method. Will generate a {@link User} object from the given
	 * parameters and save it in {@link NewUserController#u}.
	 * 
	 * @param email
	 *            Acts as the user's login credential. A password of sorts,
	 *            however extremely insecure.
	 * @param username
	 *            A mutable, and non-unique identifier for the user. This
	 *            {@link String} acts as the user's screen name.
	 * @return A new NewUserController object using the given parameters.
	 */
	public static NewUserController create(String email, String username) {
		User u = new User(email, username);
		return new NewUserController(u);
	}

	/**
	 * Attempts to submit {@link NewUserController#u} to the elastic search
	 * database.
	 * 
	 * @return True if {@link NewUserController#u} was successfully submitted.
	 *         False if not.
	 */
	public boolean submit() {
		try {
			ESClient es = new ESClient();
			if (es.submitUser(this.u)) {
				MainModel.getInstance().saveMainUser(this.u);
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
