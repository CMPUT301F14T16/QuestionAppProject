package ca.ualberta.cmput301f14t16.easya.View;

import java.util.List;

import ca.ualberta.cmput301f14t16.easya.R;
import ca.ualberta.cmput301f14t16.easya.Controller.ChangeUsernameController;
import ca.ualberta.cmput301f14t16.easya.Exceptions.NoContentAvailableException;
import ca.ualberta.cmput301f14t16.easya.Model.MainModel;
import ca.ualberta.cmput301f14t16.easya.Model.QuestionList;
import ca.ualberta.cmput301f14t16.easya.Model.Queue;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import ca.ualberta.cmput301f14t16.easya.Model.Sort;
import ca.ualberta.cmput301f14t16.easya.Model.Data.PMClient;
import android.content.res.Configuration;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

/**
 * 
 * @author Cauani
 *
 */
public class MainActivity extends Activity {
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mDrawerToggle;
    private LinearLayout mDrawerList;
    private ProgressDialog pd;
    
    public final static String QUESTION_KEY = "ca.ualberta.cmput301f14t16.easya.QUESTIONKEY"; 
    public static List<QuestionList> displayedQuestions;

    @Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main_master);

		try{
			((TextView)findViewById(R.id.drawer_username)).setText(MainModel.getInstance().getCurrentUser().getUsername());
		}catch(Exception ex){
			finish();
			Intent i = new Intent(this, WelcomeActivity.class);
			startActivity(i);
		}
		
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
        (new GetQuestionListTask(this)).execute();
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
        menu.findItem(R.id.menu_sortByOldest).setVisible(!drawerOpen);
        menu.findItem(R.id.menu_sortByNewest).setVisible(!drawerOpen);
        menu.findItem(R.id.menu_sortByMostVotes).setVisible(!drawerOpen);
        menu.findItem(R.id.menu_sortByLeastVotes).setVisible(!drawerOpen);
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
        case R.id.menu_sortByNewest: 
        	displayedQuestions = Sort.dateSort(true, displayedQuestions);
        	SetAdapter(displayedQuestions);
        	return true;
        case R.id.menu_sync:
        	MainModel.getInstance().wipeCache();
        case R.id.menu_sortByOldest: 
        	displayedQuestions = Sort.dateSort(false, displayedQuestions);
        	SetAdapter(displayedQuestions);
        	return true;
        case R.id.menu_sortByMostVotes: 
        	displayedQuestions = Sort.sortUpVote(true, displayedQuestions);
        	SetAdapter(displayedQuestions);
        	return true;
        case R.id.menu_sortByLeastVotes: 
        	displayedQuestions = Sort.sortUpVote(false, displayedQuestions);
        	SetAdapter(displayedQuestions);
        	return true;
        }
        
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onDestroy() {
        //Free up the thread
        Queue.getInstance().Stop();
        PMClient pm = new PMClient();
        pm.clearA(this);
        pm.clearQ(this);
    	if (pd!=null) {
			pd.dismiss();
		}
        super.onDestroy();
    }

    @Override
    public void onPause(){
    	Queue.getInstance().Stop();
        super.onPause();
    }

    @Override
    public void onResume(){
        Queue.getInstance();
        super.onResume();
    }
    
    private void SetAdapter(List<QuestionList> lst){
    	ArrayAdapter<QuestionList> adapter = new MainViewAdapter(this, lst);
        ((ListView)findViewById(R.id.question_list)).setAdapter(adapter);
    }
    
    private class GetQuestionListTask extends AsyncTask<Void, Void, List<QuestionList>> {
    	private Context ctx;
    	
    	public GetQuestionListTask(Context ctx){
    		this.ctx = ctx;
    	}
    	
    	@Override
		protected void onPreExecute() {
    		pd = new ProgressDialog(ctx);
			pd.setTitle("Loading questions...");
			pd.setMessage("Please wait.");
			pd.setCancelable(false);
			pd.setIndeterminate(true);
			pd.show();
		}
    	
    	@Override
    	protected List<QuestionList> doInBackground(Void...voids) {
            try{
            	return MainModel.getInstance().getAllQuestions();
            }catch(NoContentAvailableException ex){
            	return null;
            }
        }

        @Override
        protected void onPostExecute(List<QuestionList> result) {
        	if (result == null){
        		//TODO: display the no content available screen
        	}else{        		
	        	SetAdapter(result);
	        	displayedQuestions = result;
	        	
	        	if (pd!=null) {
					pd.dismiss();
				}
        	}
        }
    }
    
    public void AddNewQuestion(View v){
    	Intent i = new Intent(this, SubmitQuestionActivity.class);
    	i.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
        this.startActivity(i);
    }
    
    public void changeUsername(View v){
    	changeUsernameDialog(v.getContext());
    }
    
    public void changeUsernameDialog(Context ctx) {
        AlertDialog.Builder builder = new AlertDialog.Builder(ctx);
        LayoutInflater inflater = this.getLayoutInflater();
        View v = inflater.inflate(R.layout.change_username_fragment, null);
        try{
        	((EditText)v.findViewById(R.id.change_username_username)).setText(MainModel.getInstance().getCurrentUser().getUsername());
        }catch(NoContentAvailableException ex){
        	//TODO: deal with this exception
        }
        builder.setView(v)
               .setPositiveButton("Change", new DialogInterface.OnClickListener() {
                   @Override
                   public void onClick(DialogInterface dialog, int id) {
                	   (new changeUsernameTask(((Dialog)dialog).getContext(), dialog)).execute();
                   }
               })
               .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                   public void onClick(DialogInterface dialog, int id) {
                       dialog.cancel();
                   }
               });      
        builder.create().show();
    }
    
    private class changeUsernameTask extends AsyncTask<Void, Void, Boolean> {
    	private ChangeUsernameController controller;
    	private Context ctx;
    	private DialogInterface d;
    	
    	public changeUsernameTask(Context ctx, DialogInterface d){
    		this.ctx = ctx;
    		this.d = d;
    	}
    	
    	@Override
		protected void onPreExecute() {
    		pd = new ProgressDialog(ctx);
			pd.setTitle("Changing your username...");
			pd.setMessage("Please wait.");
			pd.setCancelable(false);
			pd.setIndeterminate(true);
			pd.show();
		}
    	
    	@Override
    	protected Boolean doInBackground(Void...voids) {
            try{
            	try{
            		String username = ((EditText)((Dialog)d).findViewById(R.id.change_username_username)).getText().toString();
            		controller = 
	        				ChangeUsernameController.create(
	        						username,
	        						MainModel.getInstance().getCurrentUser());    	        		
	        		return controller.submit();	
	        	}catch(Exception ex){
	        		System.out.println(ex.getMessage());
	        		return false;
	        	}
            }catch(Exception ex){
            	return false;
            }
        }

        @Override
        protected void onPostExecute(Boolean result) {
        	if (pd!=null) {
				pd.dismiss();
			} 
        	if (result){
        		try{
        			((EditText)findViewById(R.id.drawer_username)).setText(MainModel.getInstance().getCurrentUser().getUsername());
        		}catch(NoContentAvailableException ex){
        			//TODO: deal with this exception
        		}
        		d.cancel();
    		}else{    			
    			new AlertDialog.Builder(ctx)
    		    .setTitle("No connection found")
    		    .setMessage("We were unable to connect to our servers to change your username. Try checking your internet connection and change it again.")
    		    .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
    		        public void onClick(DialogInterface dialog, int which) {
    		        	
    		        }
    		     })
    		    .setIcon(android.R.drawable.ic_dialog_alert)
    		    .show();  
			}      	
        }
    }
}
