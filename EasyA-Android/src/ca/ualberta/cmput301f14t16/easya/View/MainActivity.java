package ca.ualberta.cmput301f14t16.easya.View;

import java.util.List;

import ca.ualberta.cmput301f14t16.easya.R;
import ca.ualberta.cmput301f14t16.easya.Exceptions.NoContentAvailableException;
import ca.ualberta.cmput301f14t16.easya.Model.MainModel;
import ca.ualberta.cmput301f14t16.easya.Model.QuestionList;
import ca.ualberta.cmput301f14t16.easya.Model.Queue;
import android.content.Intent;
import ca.ualberta.cmput301f14t16.easya.Model.Sort;
import android.content.res.Configuration;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;

public class MainActivity extends Activity {
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mDrawerToggle;
    private LinearLayout mDrawerList;
    
    public final static String QUESTION_KEY = "ca.ualberta.cmput301f14t16.easya.QUESTIONKEY"; 
    
    public static Queue mQueueThread; 
    public static MainModel mm;
    public static List<QuestionList> displayedQuestions;

    @Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main_master);

		////
		//TODO: if no user is found, try to create/load one from internet
		// If still not able, block ui with the create user screen.
		
		mm = new MainModel(getApplicationContext());
		
        //Creation of the drawer menu
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerList = (LinearLayout) findViewById(R.id.drawer_menu);
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout,
               R.drawable.ic_drawer, R.string.drawer_open, R.string.drawer_close) {

            /** Called when a drawer has settled in a completely closed state. */
            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
                //getActionBar().setTitle(mTitle);
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }

            /** Called when a drawer has settled in a completely open state. */
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                //getActionBar().setTitle(mDrawerTitle);
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }
        };

        // Set the drawer toggle as the DrawerListener
        mDrawerLayout.setDrawerListener(mDrawerToggle);
        getActionBar().setDisplayHomeAsUpEnabled(true);
        getActionBar().setHomeButtonEnabled(true);

        /////
        mQueueThread = new Queue(getApplicationContext());
        mQueueThread.start();
        
        /////
        (new GetQuestionListTask()).execute();
        //TODO: Organize this mess
    }
    
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        // If the nav drawer is open, hide action items related to the content view
        boolean drawerOpen = mDrawerLayout.isDrawerOpen(mDrawerList);
        menu.findItem(R.id.menu_search).setVisible(!drawerOpen);
        menu.findItem(R.id.menu_sort).setVisible(!drawerOpen);
        menu.findItem(R.id.menu_sortByDate).setVisible(!drawerOpen);
        //menu.findItem(R.id.menu_sortByPicture).setVisible(!drawerOpen);
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Pass the event to ActionBarDrawerToggle, if it returns
        // true, then it has handled the app icon touch event
        if (mDrawerToggle.onOptionsItemSelected(item))
        	return true;

        switch (item.getItemId()) {
        case R.id.menu_sortByDate: 
        	displayedQuestions = Sort.dateSort(true, displayedQuestions);
        	SetAdapter(displayedQuestions);
        	return true;
        }
        
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onDestroy() {
        //Free up the thread
        mQueueThread.Stop();
        super.onDestroy();
    }

    @Override
    public void onPause(){
        mQueueThread.Stop();
        super.onPause();
    }

    @Override
    public void onResume(){
        if (mQueueThread != null && !mQueueThread.isAlive()) {
            mQueueThread = new Queue(getApplicationContext());
            mQueueThread.start();
        }
        super.onResume();
    }
    
    private void SetAdapter(List<QuestionList> lst){
    	ArrayAdapter<QuestionList> adapter = new MainViewAdapter(this, lst);
        ((ListView)findViewById(R.id.question_list)).setAdapter(adapter);
    }
    
    private class GetQuestionListTask extends AsyncTask<Void, Void, List<QuestionList>> {
        protected List<QuestionList> doInBackground(Void...voids) {
            try{
            	return mm.getAllQuestions();
            }catch(NoContentAvailableException ex){
            	return null;
            }
        }

        protected void onPostExecute(List<QuestionList> result) {
        	if (result == null){
        		//TODO: display the no content available screen
        	}else{
        		
        	SetAdapter(result);
        	displayedQuestions = result;
        	}
        }
    }
    
    public void AddNewQuestion(View v){
    	Intent i = new Intent(this, SubmitQuestionActivity.class);
        this.startActivity(i);
    }

}
