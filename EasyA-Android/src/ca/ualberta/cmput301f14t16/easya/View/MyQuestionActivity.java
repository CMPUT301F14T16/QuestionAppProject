package ca.ualberta.cmput301f14t16.easya.View;

import java.util.List;

import ca.ualberta.cmput301f14t16.easya.R;
import ca.ualberta.cmput301f14t16.easya.Exceptions.NoContentAvailableException;
import ca.ualberta.cmput301f14t16.easya.Model.MainModel;
import ca.ualberta.cmput301f14t16.easya.Model.QuestionList;
import ca.ualberta.cmput301f14t16.easya.Model.Sort;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class MyQuestionActivity extends MasterActivity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		position = 2;
		getActionBar().setTitle("My questions");
    }
    
    @Override
	protected void startUpdate(){
		((TextView)findViewById(R.id.drawer_username)).setText(MainModel.getInstance().getCurrentUser().getUsername());
		try {
			update(MainModel.getInstance().getAllUserQuestions());
		} catch (NoContentAvailableException e) {
			//TODO: display the nocontentavailable screen
		}	
	}
    
	@Override
	public void update(List<QuestionList> lst) {
		displayedQuestions = Sort.sortDate(true, lst);	
		bindAdapter();
		stopAnimateSync();
	}
}
