package ca.ualberta.cmput301f14t16.easya.Controller.ATasks;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.widget.EditText;
import ca.ualberta.cmput301f14t16.easya.R;
import ca.ualberta.cmput301f14t16.easya.Controller.NewUserController;
import ca.ualberta.cmput301f14t16.easya.Exceptions.NoContentAvailableException;
import ca.ualberta.cmput301f14t16.easya.Exceptions.NoInternetException;
import ca.ualberta.cmput301f14t16.easya.Model.InternetCheck;
import ca.ualberta.cmput301f14t16.easya.Model.Location;
import ca.ualberta.cmput301f14t16.easya.Model.LocationPreferencesEnum;
import ca.ualberta.cmput301f14t16.easya.Model.MainModel;
import ca.ualberta.cmput301f14t16.easya.Model.User;
import ca.ualberta.cmput301f14t16.easya.Model.Data.PMClient;
import ca.ualberta.cmput301f14t16.easya.View.MainActivity;

/**
 * Provides an {@link AsyncTask} subclass that processes a request "sign in" a
 * user. Effectively, this means to check the elastic search database for an
 * existing {@link User} with an email field matching the one being used to sign
 * in. If one is found, that user's data is downloaded and used, and if not, a
 * new {@link User} is created.
 */
public class signinTask extends AsyncTask<Void, Void, Boolean> {
	/**
	 * @see ca.ualberta.cmput301f14t16.easya.Controller.NewUserController
	 */
	private NewUserController controller;
	/**
	 * The current {@link Context} for the application.
	 */
	private Context ctx;
	private ProgressDialog pd;
	/**
	 * True if a {@link User} with an email field matching the one being used to
	 * sign in is found in the elastic search database.
	 */
	private boolean userFound;
	/**
	 * The {@link Activity} instantiating this class.
	 */
	private Activity a;

	/**
	 * Creates a new signinTask.
	 * 
	 * @param a
	 *            The {@link Activity} calling this constructor.
	 */
	public signinTask(Activity a) {
		this.a = a;
		this.ctx = (Context) a;
	}

	/**
	 * Displays a progress dialogue as this AsyncTask completes.
	 * 
	 * @see android.os.AsyncTask#onPreExecute()
	 */
	@Override
	protected void onPreExecute() {
		pd = new ProgressDialog(ctx);
		pd.setTitle("Processing your user...");
		pd.setMessage("Please wait.");
		pd.setCancelable(false);
		pd.setIndeterminate(true);
		pd.show();
		InternetCheck.forceCheckForInternet();
	}

	/**
	 * Acquires the strings being used to sign in and check the elastic search
	 * database for an existing {@link User} with an email field matching the
	 * one being submitted. If one is found, that user's data is downloaded and
	 * used, and if not, a new {@link User} is created.
	 * 
	 * @see android.os.AsyncTask#doInBackground(java.lang.Object[])
	 */
	@Override
	protected Boolean doInBackground(Void... voids) {
		try {
			try {
				String email = ((EditText) a.findViewById(R.id.welcome_email))
						.getText().toString();
				String username = ((EditText) a
						.findViewById(R.id.welcome_username)).getText()
						.toString();

				try {
					User auxUser = MainModel.getInstance()
							.getUserByEmail(email);
					MainModel.getInstance().saveMainUser(auxUser);
					this.userFound = true;
					MainModel.getInstance().updateUsername(auxUser);
					try{
						PMClient pm = new PMClient();
						pm.saveUserLocationPreference(LocationPreferencesEnum.GPS); //by default
						Location.getLocationName(); // Save user current location
					}catch(Exception ex){}
					return true;
				} catch (NoContentAvailableException ex) {
					controller = NewUserController.create(email, username);
					return controller.submit();
				} catch (NoInternetException ex) {
					return false;
				}
			} catch (Exception ex) {
				System.out.println(ex.getMessage());
				return false;
			}
		} catch (Exception ex) {
			return false;
		}
	}

	/**
	 * Closes the current progress dialogue and displays an AlertDialog telling
	 * the user what was done with his login credentials.
	 * 
	 * @see android.os.AsyncTask#onPostExecute(java.lang.Object)
	 */
	@Override
	protected void onPostExecute(Boolean result) {
		if (pd != null) {
			pd.dismiss();
		}	
		if (result) {
			if (userFound) {
				new AlertDialog.Builder(ctx)
						.setTitle("User found!")
						.setMessage(
								"Yay! We found your user in our server, we'll now load all your saved preferences and created content!")
						.setPositiveButton("Ok",
								new DialogInterface.OnClickListener() {
									public void onClick(DialogInterface dialog,
											int which) {
										Intent i = new Intent(ctx,
												MainActivity.class);
										i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
										ctx.startActivity(i);
									}
								}).setIcon(android.R.drawable.ic_dialog_info)
						.show();
			} else {
				Intent i = new Intent(ctx, MainActivity.class);
				i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				ctx.startActivity(i);
			}
		} else {
			new AlertDialog.Builder(ctx)
					.setTitle("No connection found")
					.setMessage(
							"We were unable to connect to our servers to create your user. Try checking your internet connection and sign in again.")
					.setPositiveButton("Ok",
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog,
										int which) {
								}
							}).setIcon(android.R.drawable.ic_dialog_alert)
					.show();
		}
	}
}