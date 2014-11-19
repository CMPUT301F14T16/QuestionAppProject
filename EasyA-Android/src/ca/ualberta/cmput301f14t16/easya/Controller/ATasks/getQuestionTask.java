package ca.ualberta.cmput301f14t16.easya.Controller.ATasks;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import ca.ualberta.cmput301f14t16.easya.Exceptions.NoContentAvailableException;
import ca.ualberta.cmput301f14t16.easya.Model.MainModel;
import ca.ualberta.cmput301f14t16.easya.Model.Question;
import ca.ualberta.cmput301f14t16.easya.View.MainView;

/**
 * Provides an {@link AsyncTask} subclass that processes a request to get a
 * single {@link Question} object stored in the elastic search database.
 */
public class getQuestionTask extends AsyncTask<Void, Void, Question> {
	/**
	 * The current {@link Context} for the application.
	 */
	private Context ctx;
	private ProgressDialog pd;
	/**
	 * The unique ID of the {@link Question} requested.
	 */
	private String qId;
	/**
	 * The main view of the application.
	 */
	private MainView<Question> v;

	/**
	 * Creates a new getQuestionTask.
	 * 
	 * @param ctx
	 *            Setter for {@link getQuestionTask#ctx}.
	 * @param v
	 *            Setter for {@link getQuestionTask#v}.
	 * @param qId
	 *            Setter for {@link getQuestionTask#qId}.
	 */
	public getQuestionTask(Context ctx, MainView<Question> v, String qId) {
		this.ctx = ctx;
		this.qId = qId;
		this.v = v;
	}

	/**
	 * Displays a progress dialogue as this AsyncTask completes.
	 * 
	 * @see android.os.AsyncTask#onPreExecute()
	 */
	@Override
	protected void onPreExecute() {
		pd = new ProgressDialog(ctx);
		pd.setTitle("Loading question...");
		pd.setMessage("Please wait.");
		pd.setCancelable(false);
		pd.setIndeterminate(true);
		pd.show();
	}

	/**
	 * Gets the requested {@link Question} object from the elastic search
	 * database.
	 * 
	 * @see android.os.AsyncTask#doInBackground(java.lang.Object[])
	 */
	@Override
	protected Question doInBackground(Void... voids) {
		try {
			if (this.qId == null || this.qId.equals(""))
				return null;
			return MainModel.getInstance().getQuestionById(this.qId);
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
	protected void onPostExecute(Question result) {
		if (pd != null) {
			pd.dismiss();
		}
		if (result == null) {
			// TODO: display the no content available screen
		} else {
			v.update(result);
		}
	}
}
