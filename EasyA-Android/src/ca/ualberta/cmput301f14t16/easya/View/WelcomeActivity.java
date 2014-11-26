package ca.ualberta.cmput301f14t16.easya.View;

import ca.ualberta.cmput301f14t16.easya.R;
import ca.ualberta.cmput301f14t16.easya.Controller.ATasks.signinTask;
import ca.ualberta.cmput301f14t16.easya.Exceptions.NoContentAvailableException;
import ca.ualberta.cmput301f14t16.easya.Model.GeneralHelper;
import ca.ualberta.cmput301f14t16.easya.Model.MainModel;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnKeyListener;
import android.widget.Button;
import android.widget.EditText;

/**
 * A subclass of {@link Activity} that provides a UI that allows the user to
 * create a new {@link ca.ualberta.cmput301f14t16.easya.Model.User} entry in the
 * elastic search database, effectively registering them within the app. The UI
 * allows the user to set a username and associated email identifier.
 */
public class WelcomeActivity extends Activity {
	/**
	 * @see android.app.Activity#onCreate(android.os.Bundle)
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.welcome_view);
		try {
			if (MainModel.getInstance().getCurrentUser() == null)
				throw new NoContentAvailableException();
			Intent i = new Intent(this, MainActivity.class);
			i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			startActivity(i);
		} catch (NoContentAvailableException ex) {
			((EditText) findViewById(R.id.welcome_email)).setText(GeneralHelper
					.retrieveEmail());
			((EditText) findViewById(R.id.welcome_username))
					.setText(GeneralHelper.GenerateUserName());
		}
		((EditText) findViewById(R.id.welcome_email))
				.addTextChangedListener(watcher);
		((EditText) findViewById(R.id.welcome_username))
				.addTextChangedListener(watcher);
	}

	/**
	 * Checks if there is currently a string entered in the editable fields, and
	 * only activates the signin button once there is.
	 */
	public void checkInputs() {
		if (TextUtils.isEmpty(((EditText) findViewById(R.id.welcome_email))
				.getText().toString().trim())
				|| TextUtils
						.isEmpty(((EditText) findViewById(R.id.welcome_username))
								.getText().toString().trim())
				|| !android.util.Patterns.EMAIL_ADDRESS.matcher(
						((EditText) findViewById(R.id.welcome_email)).getText()
								.toString().trim()).matches()) {
			if (((Button) findViewById(R.id.welcome_sigin)).isEnabled())
				((Button) findViewById(R.id.welcome_sigin)).setEnabled(false);
		} else {
			if (!((Button) findViewById(R.id.welcome_sigin)).isEnabled())
				((Button) findViewById(R.id.welcome_sigin)).setEnabled(true);
		}
	}

	/**
	 * Creates a new {@link signinTask}
	 * 
	 * @param v
	 */
	public void onSignInClick(View v) {
		(new signinTask(this)).execute();
	}
	
	public void onRandomGenerateUsernameClick(View v){
		((EditText) findViewById(R.id.welcome_username))
				.setText(GeneralHelper.GenerateUserName());
	}

	/**
	 * Calls {@link WelcomeActivity#checkInputs()} when the text input fields
	 * are changed.
	 */
	private final TextWatcher watcher = new TextWatcher() {
		public void beforeTextChanged(CharSequence s, int start, int count,
				int after) {
			checkInputs();
		}

		public void onTextChanged(CharSequence s, int start, int before,
				int count) {
		}

		public void afterTextChanged(Editable s) {
			checkInputs();
		}
	};
}
