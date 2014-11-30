package ca.ualberta.cmput301f14t16.easya.Controller.ATasks;

import org.apache.http.message.BasicNameValuePair;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;
import ca.ualberta.cmput301f14t16.easya.Controller.UpvoteController;
import ca.ualberta.cmput301f14t16.easya.Model.MainModel;

/**
 * Provides an {@link AsyncTask} subclass that processes a request to add an
 * upvote associated with a given user to a
 * {@link ca.ualberta.cmput301f14t16.easya.Model.Content} object.
 */
public class upvoteTask extends AsyncTask<Void, Void, Boolean> {
	private BasicNameValuePair vp;
	/**
	 * The current {@link Context} for the application.
	 */
	private Context ctx;

	/**
	 * Creates a new upvoteTask.
	 * 
	 * @param ctx
	 *            setter for {@link upvoteTask#ctx}
	 * @param vp
	 *            setter for {@link upvoteTask#vp}
	 */
	public upvoteTask(Context ctx, BasicNameValuePair vp) {
		this.ctx = ctx;
		this.vp = vp;
	}

	/**
	 * Makes the requested changes to the elastic search database in a
	 * background task.
	 * 
	 * @see android.os.AsyncTask#doInBackground(java.lang.Object[])
	 */
	@Override
	protected Boolean doInBackground(Void... voids) {
		try {
			try {
				UpvoteController controller = UpvoteController.create(vp,
						MainModel.getInstance().getCurrentUser().getId());
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
	 * Notifies current views of changes and displays a warning message if the
	 * task has failed.
	 * 
	 * @see android.os.AsyncTask#onPostExecute(java.lang.Object)
	 */
	@Override
	protected void onPostExecute(Boolean result) {
		if (result) {
			MainModel.getInstance().killNotify();
			MainModel.getInstance().notifyViews();
		} else {
			Toast.makeText(
					ctx,
					"We were unable to save your upvote, check your internet connection and try again!",
					Toast.LENGTH_LONG).show();
		}
	}
}