package ca.ualberta.cmput301f14t16.easya.View;

import ca.ualberta.cmput301f14t16.easya.Model.GeneralHelper;
import ca.ualberta.cmput301f14t16.easya.Model.MainModel;
import android.app.Activity;
import android.content.Intent;

/**
 * Class responsible for handling access to the application 
 * (checking if user have permission to view this kind of content or not)
 * Since the only requirement is to (Have a user) that's the only behaviour here
 * @author Cauani
 *
 */
public abstract class SecureActivity extends Activity {
	@Override
	public void onResume(){
		super.onResume();
		if (MainModel.getInstance().getCurrentUser() == null 
				|| MainModel.getInstance().getCurrentUser().getId().equals("") 
				|| MainModel.getInstance().getCurrentUser().getUsername().equals("")){
			this.finish();
			Intent i = new Intent(this,WelcomeActivity.class);
			i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			startActivity(i);
		}
	}
}
