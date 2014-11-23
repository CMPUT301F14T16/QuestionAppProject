package ca.ualberta.cmput301f14t16.easya.Controller;

import java.io.IOException;

import ca.ualberta.cmput301f14t16.easya.Model.MainModel;
import ca.ualberta.cmput301f14t16.easya.Model.Question;
import ca.ualberta.cmput301f14t16.easya.Model.User;
import ca.ualberta.cmput301f14t16.easya.Model.Data.Cache;
import ca.ualberta.cmput301f14t16.easya.Model.Data.ESClient;

/**
 * Provides a simple controller used to add a
 * {@link ca.ualberta.cmput301f14t16.easya.Model.Question} ID to a {@link User}
 * object's {@link User#favourites} list and update the elastic search database
 * to reflect this change. The controller will always modify the data saved for
 * the {@link User} associated with the device that the current instance of the
 * application is running on.
 */
public class FavouriteController {
	/**
	 * The {@link ca.ualberta.cmput301f14t16.easya.Model.Question} to be
	 * added to {@link User#favourites}.
	 */
	private Question q;

	/**
	 * Protected constructor method.
	 * 
	 * @param q
	 *            Setter for {@link FavouriteController#q}.
	 */
	protected FavouriteController(Question q) {
		this.q = q;
	}

	/**
	 * Factory method.
	 * 
	 * @param qId
	 *            Setter for {@link FavouriteController#q}.
	 * @return A new FavouriteController object with the given parameter.
	 */
	public static FavouriteController create(Question q) {
		return new FavouriteController(q);
	}

	/**
	 * Creates a new {@link ESClient} object and attempts to use it to modify
	 * the elastic search database. This method will find the {@link User} ID
	 * associated with the device that the current instance of the application
	 * is being run on. It will modify the associated entry by appending
	 * {@link FavouriteController#q} to its {@link User#favourites}.
	 * 
	 * @return True if the changes to the elastic search database were
	 *         successful, False if an exception was raised.
	 */
	public boolean submit() {
		try {
			User u = MainModel.getInstance().getCurrentUser();
			u.setFavourite(q.getId());
			ESClient es = new ESClient();
			if (es.updateUserFavourites(u)){
				MainModel.getInstance().saveMainUser(u);
				Cache.getInstance().SaveSingleQuestion(q);
				return true;
			}
			return false;
		}catch(IOException ex){
			return false;
		} catch (Exception ex) {
			return false;
		}
	}
}
