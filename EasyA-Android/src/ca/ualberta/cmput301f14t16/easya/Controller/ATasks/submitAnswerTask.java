package ca.ualberta.cmput301f14t16.easya.Controller.ATasks;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.widget.EditText;
import android.widget.Toast;
import ca.ualberta.cmput301f14t16.easya.R;
import ca.ualberta.cmput301f14t16.easya.Controller.NewAnswerController;
import ca.ualberta.cmput301f14t16.easya.Model.Answer;
import ca.ualberta.cmput301f14t16.easya.Model.GeneralHelper;
import ca.ualberta.cmput301f14t16.easya.Model.MainModel;
import ca.ualberta.cmput301f14t16.easya.Model.Question;
import ca.ualberta.cmput301f14t16.easya.Model.Data.PMClient;
import ca.ualberta.cmput301f14t16.easya.View.QuestionActivity;

/**
 * Provides an {@link AsyncTask} subclass that processes a request to store an
 * {@link Answer} object in the elastic search database.
 */
public class submitAnswerTask extends AsyncTask<Void, Void, Boolean> {
	private ProgressDialog pd;
	/**
	 * @see ca.ualberta.cmput301f14t16.easya.Controller.NewAnswerController
	 */
	private NewAnswerController controller;
	/**
	 * The current {@link Context} for the application.
	 */
	private Context ctx;
	private Activity act;
	/**
	 * The unique ID of the parent {@link Question} to the {@link Answer} being
	 * submitted.
	 */
	private String qId;
	/**
	 * The data stored by the {@link Answer} to be created.
	 */
	private String body;
	/**
	 * A picture attached to the {@link Answer} to be created.
	 */
	private byte[] pb;

	/**
	 * Creates a new submitAnswerTask.
	 * 
	 * @param act
	 *            setter for {@link submitAnswerTask#act}
	 * @param question
	 *            setter for {@link submitAnswerTask#qId}
	 * @param body
	 *            setter for {@link submitAnswerTask#body}
	 * @param pb
	 *            setter for {@link submitAnswerTask#pb}
	 */
	public submitAnswerTask(Activity act, String question, String body,
			byte[] pb) {
		this.act = act;
		this.ctx = act;
		this.qId = question;
		this.body = body;
		this.pb = pb;
	}

	/**
	 * Displays a progress dialogue as this AsyncTask completes.
	 * 
	 * @see android.os.AsyncTask#onPreExecute()
	 */
	@Override
	protected void onPreExecute() {
		// TODO experiemntal if null block to fix bug of crash on submit answer
		if (pd == null) {
			pd = new ProgressDialog(ctx);
			pd.setTitle("Submitting answer...");
			pd.setMessage("Please wait.");
			pd.setCancelable(false);
			pd.setIndeterminate(true);
		}
		pd.show();
	}

	/**
	 * Submits a newly created {@link Answer} to the elastic search database.
	 * 
	 * @see android.os.AsyncTask#doInBackground(java.lang.Object[])
	 */
	@Override
	protected Boolean doInBackground(Void... voids) {
		try {
			try {
				controller = NewAnswerController.create(ctx, qId, body, pb,
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
			PMClient pm = new PMClient();
			pm.clearA(ctx);
			((EditText) act.findViewById(R.id.submit_answer_body)).setText("");
			if (controller.submitedOffline) {
				act.finish();
				Toast.makeText(
						ctx,
						"Your answer will be posted online when you connect to the internet!",
						Toast.LENGTH_LONG).show();
			}
			MainModel.getInstance().notifyViews();
		} else {
			act.finish();
			Toast.makeText(ctx,
					"Something bad happened, try posting your answer again!",
					Toast.LENGTH_LONG).show();
		}
		act.finish();
	}
}