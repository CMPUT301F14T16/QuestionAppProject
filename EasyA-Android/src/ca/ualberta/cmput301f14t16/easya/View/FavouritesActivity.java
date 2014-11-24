package ca.ualberta.cmput301f14t16.easya.View;

import java.util.List;

import ca.ualberta.cmput301f14t16.easya.R;
import ca.ualberta.cmput301f14t16.easya.Controller.ATasks.getFavouritesTask;
import ca.ualberta.cmput301f14t16.easya.Exceptions.NoContentAvailableException;
import ca.ualberta.cmput301f14t16.easya.Model.MainModel;
import ca.ualberta.cmput301f14t16.easya.Model.QuestionList;
import ca.ualberta.cmput301f14t16.easya.Model.Sort;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class FavouritesActivity extends MasterActivity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		position = 1;
		getActionBar().setTitle("Favourites");
    }
    
	@Override
	protected void startUpdate(){
		((TextView)findViewById(R.id.drawer_username)).setText(MainModel.getInstance().getCurrentUser().getUsername());
		update(MainModel.getInstance().getAllCachedFavourites());
	}
	
	@Override
	public void update(List<QuestionList> lst) {
		displayedQuestions = Sort.dateSort(true, lst);	
		bindAdapter();
		stopAnimateSync();
	}
}
