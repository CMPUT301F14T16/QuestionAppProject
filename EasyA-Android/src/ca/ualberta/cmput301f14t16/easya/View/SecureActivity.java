package ca.ualberta.cmput301f14t16.easya.View;

import ca.ualberta.cmput301f14t16.easya.Model.GeneralHelper;
import ca.ualberta.cmput301f14t16.easya.Model.MainModel;
import android.app.Activity;
import android.content.Intent;

/**
 * Extends the {@link Activity} class in order to provide a specific handling of
 * it's onResume() method that will then be passed on to all of its children
 * classes. Specifically, onResume() will handle a given user's access to the
 * overall functionality of the app. The only requirement for full access to the
 * app is to have registered with a username and email, so onResume() will check
 * for these credentials, and open a new {@link WelcomeActivity} if they are not
 * found.
 * 
 * @author Cauani
 *
 */
public abstract class SecureActivity extends Activity {
	/**
	 * @see android.app.Activity#onResume()
	 */
	@Override
	public void onResume() {
		super.onResume();
		if (MainModel.getInstance().getCurrentUser() == null
				|| MainModel.getInstance().getCurrentUser().getId().equals("")
				|| MainModel.getInstance().getCurrentUser().getUsername()
						.equals("")) {
			this.finish();
			Intent i = new Intent(this, WelcomeActivity.class);
			i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			startActivity(i);
		}
	}
}
