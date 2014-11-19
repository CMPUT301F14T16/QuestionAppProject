package ca.ualberta.cmput301f14t16.easya.Controller.ATasks;

import org.apache.http.message.BasicNameValuePair;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.widget.Toast;
import ca.ualberta.cmput301f14t16.easya.Controller.FavouriteController;
import ca.ualberta.cmput301f14t16.easya.Model.GeneralHelper;
import ca.ualberta.cmput301f14t16.easya.Model.MainModel;
import ca.ualberta.cmput301f14t16.easya.View.QuestionActivity;

/**
 * Provides an {@link AsyncTask} subclass that processes a request to add a
 * {@link Content} object to a {@link User} object's favourites field in the
 * elastic search database.
 */
public class favouriteTask extends AsyncTask<Void, Void, Boolean> {
	private BasicNameValuePair vp;
	/**
	 * The current {@link Context} for the application.
	 */
	private Context ctx;

	/**
	 * Creates a new favouriteTask.
	 * 
	 * @param ctx
	 *            Setter for {@link favouriteTask#ctx}.
	 * @param vp
	 */
	public favouriteTask(Context ctx, BasicNameValuePair vp) {
		this.ctx = ctx;
		this.vp = vp;
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
						.create(MainModel.getInstance().getCurrentUser()
								.getId());
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
			Intent i = new Intent(ctx, QuestionActivity.class);
			i.putExtra(GeneralHelper.QUESTION_KEY, vp.getName());
			i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			ctx.startActivity(i);
		} else {
			Toast.makeText(
					ctx,
					"We were unable to save your favourite, check your internet connection and try again!",
					Toast.LENGTH_LONG).show();
		}
	}
}