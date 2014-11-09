package ca.ualberta.cmput301f14t16.easya.View;

import ca.ualberta.cmput301f14t16.easya.R;
import ca.ualberta.cmput301f14t16.easya.Controller.NewUserController;
import ca.ualberta.cmput301f14t16.easya.Controller.ATasks.signinTask;
import ca.ualberta.cmput301f14t16.easya.Exceptions.NoContentAvailableException;
import ca.ualberta.cmput301f14t16.easya.Exceptions.NoInternetException;
import ca.ualberta.cmput301f14t16.easya.Model.GeneralHelper;
import ca.ualberta.cmput301f14t16.easya.Model.MainModel;
import ca.ualberta.cmput301f14t16.easya.Model.User;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class WelcomeActivity extends Activity {
	private ProgressDialog pd;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.welcome_view);
		try{
			MainModel.getInstance().getCurrentUser();
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
