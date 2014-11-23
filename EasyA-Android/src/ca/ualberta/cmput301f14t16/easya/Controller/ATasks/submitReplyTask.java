package ca.ualberta.cmput301f14t16.easya.Controller.ATasks;

import org.apache.http.message.BasicNameValuePair;

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
	 * The unique ID of the parent {@link Question} to the {@link Reply} being
	 * submitted.
	 */
	private String qId;
	/**
	 * The unique ID of the parent {@link Answer} to the {@link Reply} being
	 * submitted. May be Null.
	 */
	private String aId;
	/**
	 * The data stored by the {@link Reply} to be created.
	 */
	private String body;
	private TextView tv;

	/**
	 * Creates a new submitReplyTask.
	 * 
	 * @param ctx
	 *            setter for {@link submitReplyTask#ctx}
	 * @param vp
	 *            setter for {@link submitReplyTask#qId} and
	 *            {@link submitReplyTask#aId}. The values with be extracted from
	 *            the value pair.
	 * @param body
	 *            setter for {@link submitReplyTask#body}
	 * @param tv
	 *            setter for {@link submitReplyTask#tv}
	 */
	public submitReplyTask(Context ctx, BasicNameValuePair vp, String body,
			TextView tv) {
		this.ctx = ctx;
		this.qId = vp.getName();
		this.aId = vp.getValue();
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
	 * Submits a newly created {@link Reply} to the elastic search database.
	 * 
	 * @see android.os.AsyncTask#doInBackground(java.lang.Object[])
	 */
	@Override
	protected Boolean doInBackground(Void... voids) {
		try {
			try {
				controller = NewReplyController.create(ctx, qId, aId, body,
						MainModel.getInstance().getCurrentUser().getId());

				return controller.submit();
			} catch (Exception ex) {
				System.out.println(ex.getMessage());
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
						"Your reply will be posted online when you connect to the internet!",
						Toast.LENGTH_LONG).show();
				tv.setText("");
			}
			MainModel.getInstance().notifyViews();
		} else {
			Toast.makeText(ctx,
					"Something bad happened, try posting your question again!",
					Toast.LENGTH_LONG).show();
		}
	}
}