package ca.ualberta.cmput301f14t16.easya.Controller.ATasks;

import android.os.AsyncTask;
import android.widget.Toast;
import ca.ualberta.cmput301f14t16.easya.Exceptions.NoContentAvailableException;
import ca.ualberta.cmput301f14t16.easya.Model.MainModel;
import ca.ualberta.cmput301f14t16.easya.Model.Question;
import ca.ualberta.cmput301f14t16.easya.Model.Data.ContextProvider;

/**
 * Provides an {@link AsyncTask} subclass that saves a {@link Question},
 * referenced by ID, to the devices local memory.
 */
public class saveToDeviceTask extends AsyncTask<Void, Void, Boolean> {
	/**
	 * The unique ID of the {@link Question} requested.
	 */
	private String qId;

	/**
	 * Creates a new getQuestionTask.
	 * 
	 * @param qid
	 *            Setter for {@link getQuestionTask#qId}.
	 */
	public saveToDeviceTask(String qid) {
		this.qId = qid;
	}

	/**
	 * Displays a status message.
	 * 
	 * @see android.os.AsyncTask#onPreExecute()
	 */
	@Override
	protected void onPreExecute() {
		Toast.makeText(ContextProvider.get(),
				"Your question is being saved to the device.",
				Toast.LENGTH_SHORT).show();
	}

	/**
	 * Saves the requested {@link Question} object to local memory.
	 * 
	 * @see android.os.AsyncTask#doInBackground(java.lang.Object[])
	 */
	@Override
	protected Boolean doInBackground(Void... voids) {
		try {
			return (MainModel.getInstance().getQuestionById(qId) != null);
		} catch (NoContentAvailableException ex) {
			return false;
		}
	}

	/**
	 * Displays a warning message if the task has failed.
	 * 
	 * @see android.os.AsyncTask#onPostExecute(java.lang.Object)
	 */
	@Override
	protected void onPostExecute(Boolean result) {
		if (!result) {
			Toast.makeText(
					ContextProvider.get(),
					"We were unable to save this question to the device. Check your internet connection and try again.",
					Toast.LENGTH_LONG).show();
		}
	}
}