package ca.ualberta.cmput301f14t16.easya.Controller.ATasks;

import java.util.List;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import ca.ualberta.cmput301f14t16.easya.Exceptions.NoContentAvailableException;
import ca.ualberta.cmput301f14t16.easya.Model.MainModel;
import ca.ualberta.cmput301f14t16.easya.Model.QuestionList;
import ca.ualberta.cmput301f14t16.easya.Model.Data.PMClient;
import ca.ualberta.cmput301f14t16.easya.View.MainView;

/**
 * Provides an {@link AsyncTask} subclass that processes a request to get the
 * data stored in a {@link ca.ualberta.cmput301f14t16.easya.Model.User} object's
 * favourites field from the elastic search database.
 */
public class getFavouritesTask extends
		AsyncTask<Void, Void, List<QuestionList>> {
	private ProgressDialog pd;
	/**
	 * The main view of the application.
	 */
	private MainView<List<QuestionList>> v;

	/**
	 * Creates a new getFavouritesTask.
	 * 
	 * @param v
	 *            Setter for {@link getFavouritesTask#v}
	 */
	public getFavouritesTask(MainView<List<QuestionList>> v) {
		this.v = v;
	}

	/**
	 * Gets the requested favourites from the elastic search database.
	 * 
	 * @see android.os.AsyncTask#doInBackground(java.lang.Object[])
	 */
	@Override
	protected List<QuestionList> doInBackground(Void... voids) {
		try {
			return MainModel.getInstance().getAllUserFavourites();
		} catch (NoContentAvailableException ex) {
			return null;
		}
	}

	/**
	 * Closes the progress dialogue and displays a warning message if the task
	 * has failed.
	 * 
	 * @see android.os.AsyncTask#onPostExecute(java.lang.Object)
	 */
	@Override
	protected void onPostExecute(List<QuestionList> result) {
		if (pd != null) {
			pd.dismiss();
		}
		if (result == null) {
			// TODO: display the no content available screen
		} else {
			v.update(result);
		}
		v.stopAnimateSync();
	}
}