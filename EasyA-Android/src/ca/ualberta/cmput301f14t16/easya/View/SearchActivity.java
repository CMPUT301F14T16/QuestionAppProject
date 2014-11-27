package ca.ualberta.cmput301f14t16.easya.View;

import java.util.List;
import ca.ualberta.cmput301f14t16.easya.R;
import ca.ualberta.cmput301f14t16.easya.Controller.ATasks.searchQuestionTask;
import ca.ualberta.cmput301f14t16.easya.Model.MainModel;
import ca.ualberta.cmput301f14t16.easya.Model.QuestionList;
import android.app.SearchManager;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
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
			this.query = intent.getStringExtra(SearchManager.QUERY).trim();
			getActionBar().setTitle("\""+this.query+"\"");
	    }catch(Exception ex){
	    	finish();
	    }
    }
	
	@Override
	protected void startUpdate() {
		((TextView) findViewById(R.id.drawer_username)).setText(MainModel
				.getInstance().getCurrentUser().getUsername());		
		(new searchQuestionTask(this, this, this.query)).execute();
	}

	@Override
	public void update(List<QuestionList> lst) {
		super.update(lst);
		stopAnimateSync();
	}

}
