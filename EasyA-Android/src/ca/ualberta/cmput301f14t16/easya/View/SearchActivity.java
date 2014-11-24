package ca.ualberta.cmput301f14t16.easya.View;

import android.os.Bundle;

public class SearchActivity extends MasterActivity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		position = 4;
		getActionBar().setDisplayShowTitleEnabled(false);
		getActionBar().setDisplayHomeAsUpEnabled(true);
    }
	
	@Override
	protected void startUpdate() {
		
	}

}
