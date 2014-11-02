package ca.ualberta.cmput301f14t16.easya.View;

import java.util.ArrayList;
import java.util.List;

import ca.ualberta.cmput301f14t16.easya.R;
import ca.ualberta.cmput301f14t16.easya.Model.Answer;
import ca.ualberta.cmput301f14t16.easya.Model.Question;
import ca.ualberta.cmput301f14t16.easya.Model.Queue;
import ca.ualberta.cmput301f14t16.easya.R.drawable;
import ca.ualberta.cmput301f14t16.easya.R.id;
import ca.ualberta.cmput301f14t16.easya.R.layout;
import ca.ualberta.cmput301f14t16.easya.R.menu;
import ca.ualberta.cmput301f14t16.easya.R.string;

import android.content.res.Configuration;
import android.os.Bundle;
import android.app.Activity;
import android.app.ListActivity;
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
    private List<Question> mQuestionList;
    
    public final static String QUESTION_KEY = "ca.ualberta.cmput301f14t16.easya.QUESTIONKEY"; 
    
    public static Queue mQueueThread; 

    @Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main_master);

		////
		//TODO: if no user is found, try to create/load one from internet
		// If still not able, block ui with the create user screen.
		
		
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
        ArrayAdapter<Question> adapter = new MainViewAdapter(this, getListQuestions());
        ((ListView)findViewById(R.id.question_list)).setAdapter(adapter);
        //TODO: Organize this mess
    }
    
    public List<Question> getListQuestions(){
    	if (mQuestionList == null)
    		mQuestionList = new ArrayList<Question>();
    	//Test only
    	/*
    	Question q = new Question("Title", "Body", "userI");
    	q.addAnswer(new Answer("New Answer 1", "NoAuthor"));
		q.addAnswer(new Answer("New Answer 2", "NoAuthor"));
		q.addAnswer(new Answer("New Answer 3", "NoAuthor"));

    	mQuestionList.add(q);
    	mQuestionList.add(new Question("Title", "Body", "userI"));
    	// */
    	return mQuestionList;
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
}
