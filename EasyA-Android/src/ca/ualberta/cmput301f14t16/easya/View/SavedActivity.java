package ca.ualberta.cmput301f14t16.easya.View;

import ca.ualberta.cmput301f14t16.easya.R;
import ca.ualberta.cmput301f14t16.easya.Exceptions.NoContentAvailableException;
import ca.ualberta.cmput301f14t16.easya.Model.MainModel;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class SavedActivity extends MasterActivity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		RelativeLayout.LayoutParams lp = (RelativeLayout.LayoutParams)((LinearLayout)findViewById(R.id.main_question_holder)).getLayoutParams();
		lp.height = 0;
		lp.setMargins(0,0,0,0);
		((LinearLayout)findViewById(R.id.main_question_holder)).setLayoutParams(lp);
		position = 3;
    }
    
    @Override
	protected void startUpdate(){
		((TextView)findViewById(R.id.drawer_username)).setText(MainModel.getInstance().getCurrentUser().getUsername());
		try {
			update(MainModel.getInstance().getAllSavedQuestions());
		} catch (NoContentAvailableException e) {
			//TODO: display the nocontentavailable screen
		}
	}
}
