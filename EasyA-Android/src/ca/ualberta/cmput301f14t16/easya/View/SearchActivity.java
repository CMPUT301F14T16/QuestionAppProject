package ca.ualberta.cmput301f14t16.easya.View;

import java.util.List;

import ca.ualberta.cmput301f14t16.easya.R;
import ca.ualberta.cmput301f14t16.easya.Controller.ATasks.getQuestionListTask;
import ca.ualberta.cmput301f14t16.easya.Controller.ATasks.searchQuestionTask;
import ca.ualberta.cmput301f14t16.easya.Model.MainModel;
import ca.ualberta.cmput301f14t16.easya.Model.QuestionList;
import ca.ualberta.cmput301f14t16.easya.Model.Sort;
import android.app.SearchManager;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class SearchActivity extends MasterActivity {
	private String query;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		position = 4;		
		getActionBar().setDisplayHomeAsUpEnabled(true);
		mDrawerToggle.setDrawerIndicatorEnabled(false);
		
		Intent intent = getIntent();
		try{
			this.query = intent.getStringExtra(SearchManager.QUERY);
			getActionBar().setTitle("\""+this.query+"\"");
	    }catch(Exception ex){
	    	finish();
	    }
    }
	
	private void searchSetup(){
		//TODO: add search layout parameters
		//TODO: create layout parameters as a xml file
		//TODO: inflate it here and move the list downwards
	}
	
	@Override
	protected void startUpdate() {
		((TextView) findViewById(R.id.drawer_username)).setText(MainModel
				.getInstance().getCurrentUser().getUsername());
		searchSetup();
		(new searchQuestionTask(this, this, this.query)).execute();
	}
	

	@Override
	public void update(List<QuestionList> lst) {
		super.update(lst);
		stopAnimateSync();
	}

}
