package ca.ualberta.cmput301f14t16.easya.Controller.ATasks;

import org.apache.http.message.BasicNameValuePair;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.EditText;
import android.widget.Toast;
import ca.ualberta.cmput301f14t16.easya.R;
import ca.ualberta.cmput301f14t16.easya.Controller.NewAnswerController;
import ca.ualberta.cmput301f14t16.easya.Model.Answer;
import ca.ualberta.cmput301f14t16.easya.Model.Location;
import ca.ualberta.cmput301f14t16.easya.Model.MainModel;
import ca.ualberta.cmput301f14t16.easya.Model.Question;
import ca.ualberta.cmput301f14t16.easya.Model.Data.PMClient;

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
	private Question q;
	/**
	 * The data stored by the {@link Answer} to be created.
	 */
	private String body;
	/**
	 * A picture attached to the {@link Answer} to be created.
	 */
	private byte[] pb;
	private boolean useLocation;
	/**
	 * Creates a new submitAnswerTask.
	 * 
	 * @param act
	 *            setter for {@link submitAnswerTask#act}
	 * @param question
	 *            setter for {@link submitAnswerTask#q}
	 * @param body
	 *            setter for {@link submitAnswerTask#body}
	 * @param pb
	 *            setter for {@link submitAnswerTask#pb}
	 */
	public submitAnswerTask(Activity act, Question question, String body,
			byte[] pb, boolean useLocation) {
		this.act = act;
		this.ctx = act;
		this.q = question;
		this.body = body;
		this.pb = pb;
		this.useLocation = useLocation;
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
				BasicNameValuePair vp;
				controller = NewAnswerController.create(ctx, q, body, pb,
						MainModel.getInstance().getCurrentUser().getId(),
						useLocation ? Location.getLocationCoordinates() : new double[]{0.0,0.0},
						useLocation ? Location.getLocationName().equals("No location found") ? "" : Location.getLocationName() : "");
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
			if (controller.submitedOffline){
				Toast.makeText(ctx,
						"We couldn't connect to the internet, your content will be posted online automatically when you connect to the internet!",
						Toast.LENGTH_LONG).show();
			}
			PMClient pm = new PMClient();
			pm.clearA(ctx);
			((EditText) act.findViewById(R.id.submit_answer_body)).setText("");
			MainModel.getInstance().killNotify();
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