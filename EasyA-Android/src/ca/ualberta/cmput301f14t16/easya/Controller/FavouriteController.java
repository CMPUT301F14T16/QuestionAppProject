package ca.ualberta.cmput301f14t16.easya.Controller;

import ca.ualberta.cmput301f14t16.easya.Model.MainModel;
import ca.ualberta.cmput301f14t16.easya.Model.User;
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
	 * The {@link ca.ualberta.cmput301f14t16.easya.Model.Question} ID to be
	 * added to {@link User#favourites}.
	 */
	private String qId;

	/**
	 * Protected constructor method.
	 * 
	 * @param qId
	 *            Setter for {@link FavouriteController#qId}.
	 */
	protected FavouriteController(String qId) {
		this.qId = qId;
	}

	/**
	 * Factory method.
	 * 
	 * @param qId
	 *            Setter for {@link FavouriteController#qId}.
	 * @return A new FavouriteController object with the given parameter.
	 */
	public static FavouriteController create(String qId) {
		return new FavouriteController(qId);
	}

	/**
	 * Creates a new {@link ESClient} object and attempts to use it to modify
	 * the elastic search database. This method will find the {@link User} ID
	 * associated with the device that the current instance of the application
	 * is being run on. It will modify the associated entry by appending
	 * {@link FavouriteController#qId} to its {@link User#favourites}.
	 * 
	 * @return True if the changes to the elastic search database were
	 *         successful, False if an exception was raised.
	 */
	public boolean submit() {
		try {
			User u = MainModel.getInstance().getCurrentUser();
			boolean response = u.setFavourite(qId);
			ESClient es = new ESClient();
			// TODO: send the User to ESClient for it to update the favourites
			// es.setUserFavourite();
			//TODO: save the newly favourited question on cache via
			// MainModel.getInstance().saveSingleQuestion();
			return response;
			// }catch(IOException ex){
			// return false;
		} catch (Exception ex) {
			return false;
		}
	}
}
