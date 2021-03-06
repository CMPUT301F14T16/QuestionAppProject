package ca.ualberta.cmput301f14t16.easya.Controller.ATasks;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.TextView;
import android.widget.Toast;
import ca.ualberta.cmput301f14t16.easya.Controller.NewReplyController;
import ca.ualberta.cmput301f14t16.easya.Model.Answer;
import ca.ualberta.cmput301f14t16.easya.Model.MainModel;
import ca.ualberta.cmput301f14t16.easya.Model.Question;

public class submitReplyTask extends AsyncTask<Void, Void, Boolean> {
	private ProgressDialog pd;
	/**
	 * @see ca.ualberta.cmput301f14t16.easya.Controller.NewReplyController
	 */
	private NewReplyController controller;
	/**
	 * The current {@link Context} for the application.
	 */
	private Context ctx;
	/**
	 * The unique ID of the parent
	 * {@link ca.ualberta.cmput301f14t16.easya.Model.Question} to the
	 * {@link ca.ualberta.cmput301f14t16.easya.Model.Reply} being submitted.
	 */
	private Question q;
	/**
	 * The unique ID of the parent
	 * {@link ca.ualberta.cmput301f14t16.easya.Model.Answer} to the
	 * {@link ca.ualberta.cmput301f14t16.easya.Model.Reply} being submitted. May
	 * be Null.
	 */
	private Answer a;
	/**
	 * The data stored by the
	 * {@link ca.ualberta.cmput301f14t16.easya.Model.Reply} to be created.
	 */
	private String body;
	private TextView tv;

	/**
	 * Creates a new submitReplyTask.
	 * 
	 * @param ctx
	 *            setter for {@link submitReplyTask#ctx}
	 * @param q
	 *            Setter for {@link submitReplyTask#q}.
	 * @param a
	 *            setter for {@link submitReplyTask#a}.
	 * @param body
	 *            setter for {@link submitReplyTask#body}
	 * @param tv
	 *            setter for {@link submitReplyTask#tv}
	 */
	public submitReplyTask(Context ctx, Question q, Answer a, String body,
			TextView tv) {
		this.ctx = ctx;
		this.q = q;
		this.a = a;
		this.body = body;
		this.tv = tv;
	}

	/**
	 * Displays a progress dialogue as this AsyncTask completes.
	 * 
	 * @see android.os.AsyncTask#onPreExecute()
	 */
	@Override
	protected void onPreExecute() {
		pd = new ProgressDialog(ctx);
		pd.setTitle("Submitting reply...");
		pd.setMessage("Please wait.");
		pd.setCancelable(false);
		pd.setIndeterminate(true);
		pd.show();
	}

	/**
	 * Submits a newly created
	 * {@link ca.ualberta.cmput301f14t16.easya.Model.Reply} to the elastic
	 * search database.
	 * 
	 * @see android.os.AsyncTask#doInBackground(java.lang.Object[])
	 */
	@Override
	protected Boolean doInBackground(Void... voids) {
		try {
			try {
				controller = NewReplyController.create(ctx, q, a, body,
						MainModel.getInstance().getCurrentUser().getId());

				return controller.submit();
			} catch (Exception ex) {
				return false;
				// Deal with things as: user didn't fill out everything
			}
		} catch (Exception ex) {
			// Deal with this
			return false;
		}
	}

	/**
	 * Displays a warning message if the task has failed, or a notification
	 * message if the object was submitted offline.
	 * 
	 * @see android.os.AsyncTask#onPostExecute(java.lang.Object)
	 */
	@Override
	protected void onPostExecute(Boolean result) {
		if (pd != null) {
			pd.dismiss();
		}

		if (result) {
			if (controller.submitedOffline) {
				Toast.makeText(
						ctx,
						"We couldn't connect to the internet, your content will be posted online automatically when you connect to the internet!",
						Toast.LENGTH_LONG).show();
			}
			MainModel.getInstance().killNotify();
			MainModel.getInstance().notifyViews();
		} else {
			Toast.makeText(ctx,
					"Something bad happened, try posting your question again!",
					Toast.LENGTH_LONG).show();
		}
	}
}