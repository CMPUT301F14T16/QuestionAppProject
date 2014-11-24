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
 * Provides an {@link AsyncTask} subclass that processes a request to get a list
 * of {@link ca.ualberta.cmput301f14t16.easya.Model.Question} objects stored in
 * the elastic search database.
 */
public class getQuestionListTask extends
		AsyncTask<Void, Void, List<QuestionList>> {
	/**
	 * The current {@link Context} for the application.
	 */
	private Context ctx;
	private ProgressDialog pd;
	/**
	 * The main view of the application.
	 */
	private MainView<List<QuestionList>> v;
	private boolean fullLoaded;

	/**
	 * Creates a new getQuestionListTask.
	 * 
	 * @param ctx
	 *            Setter for {@link getQuestionListTask#ctx}.
	 * @param v
	 *            Setter for {@link getQuestionListTask#v}.
	 */
	public getQuestionListTask(Context ctx, MainView<List<QuestionList>> v) {
		this.ctx = ctx;
		this.v = v;
		PMClient pm = new PMClient();
		this.fullLoaded = pm.getAppStatus();
	}

	/**
	 * Displays a progress dialogue as this AsyncTask completes.
	 * 
	 * @see android.os.AsyncTask#onPreExecute()
	 */
	@Override
	protected void onPreExecute() {
		if (!fullLoaded) {
			this.pd = new ProgressDialog(ctx);
			pd.setTitle("Loading content...");
			pd.setMessage("Please wait.");
			pd.setCancelable(false);
			pd.setIndeterminate(true);
			pd.show();
		}
	}

	/**
	 * Gets the requested
	 * {@link ca.ualberta.cmput301f14t16.easya.Model.Question} objects from the
	 * elastic search database.
	 * 
	 * @see android.os.AsyncTask#doInBackground(java.lang.Object[])
	 */
	@Override
	protected List<QuestionList> doInBackground(Void... voids) {
		try {
			try {
				if (!fullLoaded) {
					MainModel.getInstance().getAllUserFavourites();
				}
			} catch (NoContentAvailableException ex) {
			} // Do Nothing
			return MainModel.getInstance().getAllQuestions();
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
		if (!fullLoaded) {
			PMClient pm = new PMClient();
			pm.saveAppStatus();
		}
		v.stopAnimateSync();
	}
}