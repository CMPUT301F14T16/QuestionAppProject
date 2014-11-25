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
	private View searchParams;
	
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
	
	private void searchSetup(int size){
		if (searchParams != null)
			((ListView)findViewById(R.id.question_list)).removeHeaderView(searchParams);
		searchParams = getLayoutInflater().inflate(R.layout.search_view_fragment, (ViewGroup)findViewById(R.id.question_list), false);
		TextView tx = (TextView)searchParams.findViewById(R.id.search_fragment_hits);
		tx.setText(String.valueOf(size));
		
		// TODO Below works on newer versions.
		//((ListView)findViewById(R.id.question_list)).addHeaderView(searchParams);		
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
		searchSetup(lst.size());
		//TODO: if no results, display the no-results-found page
	}

}
