package ca.ualberta.cmput301f14t16.easya.Controller.ATasks;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.widget.EditText;
import ca.ualberta.cmput301f14t16.easya.R;
import ca.ualberta.cmput301f14t16.easya.Controller.ChangeUsernameController;
import ca.ualberta.cmput301f14t16.easya.Model.MainModel;

/**
 * Provides an {@link AsyncTask} subclass that processes a request to change a
 * {@link ca.ualberta.cmput301f14t16.easya.Model.User} object's username field
 * in the elastic search database.
 */
public class changeUsernameTask extends AsyncTask<Void, Void, Boolean> {
	/**
	 * @see ca.ualberta.cmput301f14t16.easya.Controller.ChangeUsernameController
	 */
	private ChangeUsernameController controller;
	/**
	 * The current {@link Context} for the application.
	 */
	private Context ctx;
	private DialogInterface d;
	private ProgressDialog pd;

	/**
	 * Creates a new changeUsernameTask.
	 * 
	 * @param ctx
	 *            Setter for {@link changeUsernameTask#ctx}.
	 * @param d
	 */
	public changeUsernameTask(Context ctx, DialogInterface d) {
		this.ctx = ctx;
		this.d = d;
	}

	/**
	 * Displays a progress dialogue as this AsyncTask completes.
	 * 
	 * @see android.os.AsyncTask#onPreExecute()
	 */
	@Override
	protected void onPreExecute() {
		pd = new ProgressDialog(ctx);
		pd.setTitle("Changing your username...");
		pd.setMessage("Please wait.");
		pd.setCancelable(false);
		pd.setIndeterminate(true);
		pd.show();
	}

	/**
	 * Submits the requested changes to the elastic search database in a
	 * background thread.
	 * 
	 * @see android.os.AsyncTask#doInBackground(java.lang.Object[])
	 */
	@Override
	protected Boolean doInBackground(Void... voids) {
		try {
			try {
				String username = ((EditText) ((Dialog) d)
						.findViewById(R.id.change_username_username)).getText()
						.toString();
				controller = ChangeUsernameController.create(username,
						MainModel.getInstance().getCurrentUser());
				return controller.submit();
			} catch (Exception ex) {
				return false;
			}
		} catch (Exception ex) {
			return false;
		}
	}

	/**
	 * Closes the progress dialogue and displays a warning message if the task
	 * has failed.
	 * 
	 * @see android.os.AsyncTask#onPostExecute(java.lang.Object)
	 */
	@Override
	protected void onPostExecute(Boolean result) {
		if (pd != null) {
			pd.dismiss();
		}
		if (result) {
			MainModel.getInstance().RefreshMainUser();
			MainModel.getInstance().notifyViews();
		} else {
			new AlertDialog.Builder(ctx)
					.setTitle("No connection found")
					.setMessage(
							"We were unable to connect to our servers to change your username. Try checking your internet connection and change it again.")
					.setPositiveButton("Ok",
							new DialogInterface.OnClickListener() {
								@Override
								public void onClick(DialogInterface dialog,
										int which) {
								}
							}).setIcon(android.R.drawable.ic_dialog_alert)
					.show();
		}
	}
}