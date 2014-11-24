package ca.ualberta.cmput301f14t16.easya.Controller.ATasks;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;
import ca.ualberta.cmput301f14t16.easya.Controller.FavouriteController;
import ca.ualberta.cmput301f14t16.easya.Model.MainModel;
import ca.ualberta.cmput301f14t16.easya.Model.Question;
import ca.ualberta.cmput301f14t16.easya.View.QuestionActivity;

/**
 * Provides an {@link AsyncTask} subclass that processes a request to add a
 * {@link ca.ualberta.cmput301f14t16.easya.Model.Content} object to a
 * {@link ca.ualberta.cmput301f14t16.easya.Model.User} object's favourites field
 * in the elastic search database.
 */
public class favouriteTask extends AsyncTask<Void, Void, Boolean> {
	private Question q;
	/**
	 * The current {@link Context} for the application.
	 */
	private Context ctx;

	/**
	 * Creates a new favouriteTask.
	 * 
	 * @param ctx
	 *            Setter for {@link favouriteTask#ctx}.
	 * @param q
	 */
	public favouriteTask(Context ctx, Question q) {
		this.ctx = ctx;
		this.q = q;
	}

	/**
	 * Submits the requested changes to the elastic search database in a
	 * background thread.
	 * 
	 * @see android.os.AsyncTask#doInBackground(java.lang.Object[])
	 */
	@Override
	protected Boolean doInBackground(Void... voids) {
		try {
			try {
				FavouriteController controller = FavouriteController
						.create(this.q);
				return controller.submit();
			} catch (Exception ex) {
				System.out.println(ex.getMessage());
				return false;
			}
		} catch (Exception ex) {
			return false;
		}
	}

	/**
	 * Opens a new {@link QuestionActivity} displaying the newly favourited
	 * symbol.
	 * 
	 * @see android.os.AsyncTask#onPostExecute(java.lang.Object)
	 */
	@Override
	protected void onPostExecute(Boolean result) {
		if (result) {
			Toast.makeText(
					ctx,
					this.q.checkFavourite(MainModel.getInstance()
							.getCurrentUser()) ? "Favourite succesfully set!"
							: "Favourite succesfully removed!",
					Toast.LENGTH_LONG).show();
			MainModel.getInstance().notifyViews();
		} else {
			Toast.makeText(
					ctx,
					"We were unable to save your favourite, check your internet connection and try again!",
					Toast.LENGTH_LONG).show();
		}
	}
}