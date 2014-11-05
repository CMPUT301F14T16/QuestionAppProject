package ca.ualberta.cmput301f14t16.easya.View;

import ca.ualberta.cmput301f14t16.easya.R;
import ca.ualberta.cmput301f14t16.easya.Controller.NewQuestionController;
import ca.ualberta.cmput301f14t16.easya.Exceptions.NoContentAvailableException;
import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

public class SubmitQuestionActivity extends Activity {
	@Override
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.submit_question);
		getActionBar().setDisplayHomeAsUpEnabled(true);
        getActionBar().setHomeButtonEnabled(true);
        try{
        	String aux = "Posting as " + MainActivity.mm.getCurrentUser().getUsername();
        	Toast.makeText(getApplicationContext(), aux, Toast.LENGTH_LONG).show();
        }catch(NoContentAvailableException ex){
        	//TODO: throw back to the main screen as no user was found
        }
	}	
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.submit, menu);
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
	    switch (item.getItemId()) {
	        case R.id.menu_submit:
	        	try{
	        		NewQuestionController controller = 
	        				new NewQuestionController(((EditText)findViewById(R.id.submit_question_title)).getText().toString(), 
	        										  ((EditText)findViewById(R.id.submit_question_body)).getText().toString(), 
						        					  MainActivity.mm.getCurrentUser().getId());
	        		controller.creatAndPushPending();
	        	}catch(Exception ex){
	        		//Deal with things as: user didn't fill out everything
	        	}
	        	return true;
	        default:
	            return super.onOptionsItemSelected(item);
	    }
	}
}
