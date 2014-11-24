package ca.ualberta.cmput301f14t16.easya.Controller.ATasks;

import java.io.IOException;
import java.util.List;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import ca.ualberta.cmput301f14t16.easya.Exceptions.NoContentAvailableException;
import ca.ualberta.cmput301f14t16.easya.Model.InternetCheck;
import ca.ualberta.cmput301f14t16.easya.Model.MainModel;
import ca.ualberta.cmput301f14t16.easya.Model.Question;
import ca.ualberta.cmput301f14t16.easya.Model.QuestionList;
import ca.ualberta.cmput301f14t16.easya.Model.Data.ESClient;
import ca.ualberta.cmput301f14t16.easya.View.MainView;

public class searchQuestionTask extends AsyncTask<Void, Void, List<QuestionList>> {
	/**
	 * The current {@link Context} for the application.
	 */
	private Context ctx;
	private ProgressDialog pd;
	/**
	 * The unique ID of the {@link Question} requested.
	 */
	private String query;
	/**
	 * The main view of the application.
	 */
	private MainView<List<QuestionList>> v;

	public searchQuestionTask(Context ctx, MainView<List<QuestionList>> v, String query) {
		this.ctx = ctx;
		this.query = query;
		this.v = v;
	}

	/**
	 * Displays a progress dialogue as this AsyncTask completes.
	 * 
	 * @see android.os.AsyncTask#onPreExecute()
	 */
	@Override
	protected void onPreExecute() {
		// TODO Experimental if null block to try and fix crash upon submit question.
		if (pd == null) {
			pd = new ProgressDialog(ctx);
			pd.setTitle("Performing search...");
			pd.setMessage("Please wait.");
			pd.setCancelable(false);
			pd.setIndeterminate(true);
		}
		pd.show();
	}


	@Override
	protected List<QuestionList> doInBackground(Void... voids) {
		try {
			if (this.query == null || this.query.equals(""))
				return null;
			if (InternetCheck.haveInternet()){
				ESClient es = new ESClient();
				return es.searchQuestionListsByQuery(this.query, 99);
			}else{
				return MainModel.getInstance().searchSavedQuestionsByQuery(query);
			}
		} catch (IOException ex) {
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
		if (pd != null && pd.isShowing()) {
			try {
				pd.dismiss();
			} catch (Exception e) {
				// TODO Solve this exception.
				e.printStackTrace();
			}
		}
		if (result == null) {
			// TODO: display the no content available screen
		} else {
			v.update(result);
		}
	}
}
