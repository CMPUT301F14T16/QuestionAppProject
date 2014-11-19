package ca.ualberta.cmput301f14t16.easya.Controller.ATasks;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.widget.EditText;
import android.widget.Toast;
import ca.ualberta.cmput301f14t16.easya.R;
import ca.ualberta.cmput301f14t16.easya.Controller.NewQuestionController;
import ca.ualberta.cmput301f14t16.easya.Model.Answer;
import ca.ualberta.cmput301f14t16.easya.Model.GeneralHelper;
import ca.ualberta.cmput301f14t16.easya.Model.MainModel;
import ca.ualberta.cmput301f14t16.easya.Model.Question;
import ca.ualberta.cmput301f14t16.easya.Model.Data.PMClient;
import ca.ualberta.cmput301f14t16.easya.View.QuestionActivity;

public class submitQuestionTask extends AsyncTask<Void, Void, Boolean> {
	private ProgressDialog pd;
	/**
	 * @see ca.ualberta.cmput301f14t16.easya.Controller.NewQuestionController
	 */
	private NewQuestionController controller;
	/**
	 * The current {@link Context} for the application.
	 */
	private Context ctx;
	/**
	 * A title String for the newly created {@link Question} to be pushed.
	 */
	private String title;
	/**
	 * The data stored by the {@link Question} to be created.
	 */
	private String body;
	/**
	 * A picture attached to the {@link Question} to be created.
	 */
	private byte[] pb;
	private Activity act;

	/**
	 * Creates a new submitQuestionTask.
	 * 
	 * @param act
	 *            setter for {@link submitQuestionTask#act}
	 * @param title
	 *            setter for {@link submitQuestionTask#title}
	 * @param body
	 *            setter for {@link submitQuestionTask#body}
	 * @param pb
	 *            setter for {@link submitQuestionTask#pb}
	 */
	public submitQuestionTask(Activity act, String title, String body, byte[] pb) {
		this.act = act;
		this.ctx = act;
		this.title = title;
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
		pd = new ProgressDialog(ctx);
		pd.setTitle("Submitting question...");
		pd.setMessage("Please wait.");
		pd.setCancelable(false);
		pd.setIndeterminate(true);
		pd.show();
	}

	/**
	 * Submits a newly created {@link Question} to the elastic search database.
	 * 
	 * @see android.os.AsyncTask#doInBackground(java.lang.Object[])
	 */
	@Override
	protected Boolean doInBackground(Void... voids) {
		try {
			try {
				controller = NewQuestionController.create(ctx, this.title,
						this.body, this.pb, MainModel.getInstance()
								.getCurrentUser().getId());
				return controller.submit();
			} catch (Exception ex) {
				System.out.println(ex.getMessage());
				return false;
				// TODO: Deal with things as: user didn't fill out everything
			}
		} catch (Exception ex) {
			// TODO: Deal with this
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
		if (result) {
			PMClient pm = new PMClient();
			pm.clearQ(ctx);
			((EditText) act.findViewById(R.id.submit_question_title))
					.setText("");
			((EditText) act.findViewById(R.id.submit_question_body))
					.setText("");
			if (controller.submitedOffline) {
				act.finish();
				Toast.makeText(
						ctx,
						"Your Question will be posted online when you connect to the internet!",
						Toast.LENGTH_LONG).show();
			} else {
				String aux = controller.getQuestionId();
				Intent i = new Intent(ctx, QuestionActivity.class);
				i.putExtra(GeneralHelper.QUESTION_KEY, aux);
				i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				act.startActivity(i);
				act.finish();
			}
		} else {
			act.finish();
			Toast.makeText(ctx,
					"Something bad happened, try posting your question again!",
					Toast.LENGTH_LONG).show();
		}

		if (pd != null) {
			pd.dismiss();
		}
		MainModel.getInstance().notifyViews();
	}
}