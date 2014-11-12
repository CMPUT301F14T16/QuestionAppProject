package ca.ualberta.cmput301f14t16.easya.View;

import ca.ualberta.cmput301f14t16.easya.R;
import ca.ualberta.cmput301f14t16.easya.Controller.ATasks.signinTask;
import ca.ualberta.cmput301f14t16.easya.Exceptions.NoContentAvailableException;
import ca.ualberta.cmput301f14t16.easya.Model.GeneralHelper;
import ca.ualberta.cmput301f14t16.easya.Model.MainModel;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class WelcomeActivity extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.welcome_view);
		try{
			if (MainModel.getInstance().getCurrentUser() == null)
				throw new NoContentAvailableException();			
			Intent i = new Intent(this, MainActivity.class);
			i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			startActivity(i);
		}catch(NoContentAvailableException ex){
			((EditText)findViewById(R.id.welcome_email)).setText(GeneralHelper.retrieveEmail());
			((EditText)findViewById(R.id.welcome_username)).setText(GeneralHelper.GenerateUserName());
		}
    }
	
	public void SignIn(View v){
		(new signinTask(this)).execute();
	}	
}
