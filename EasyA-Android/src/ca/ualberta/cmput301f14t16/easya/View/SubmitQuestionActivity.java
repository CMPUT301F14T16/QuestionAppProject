package ca.ualberta.cmput301f14t16.easya.View;

import java.util.List;

import ca.ualberta.cmput301f14t16.easya.R;
import ca.ualberta.cmput301f14t16.easya.Controller.NewQuestionController;
import ca.ualberta.cmput301f14t16.easya.Exceptions.NoContentAvailableException;
import ca.ualberta.cmput301f14t16.easya.Model.QuestionList;
import ca.ualberta.cmput301f14t16.easya.Model.Queue;
import ca.ualberta.cmput301f14t16.easya.Model.User;
import ca.ualberta.cmput301f14t16.easya.Model.Data.PMClient;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

/**
 * 
 * @author Cauani
 *
 */
public class SubmitQuestionActivity extends Activity {
	private ProgressDialog pd;
	
	@Override
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.submit_question);
		getActionBar().setDisplayHomeAsUpEnabled(true);
        getActionBar().setHomeButtonEnabled(true);
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
	        	(new submitQuestionTask(this)).execute();
	        	return true;
	        default:
	            return super.onOptionsItemSelected(item);
	    }
	}
	
	@Override
    public void onPause(){
        PMClient pm = new PMClient();
        pm.saveQTitle(this, ((EditText)findViewById(R.id.submit_question_title)).getText().toString());
        pm.saveQBody(this, ((EditText)findViewById(R.id.submit_question_body)).getText().toString());
        super.onPause();
    }

    @Override
    public void onResume(){
    	PMClient pm = new PMClient();
    	((EditText)findViewById(R.id.submit_question_title)).setText(pm.getQTitle(this));
    	((EditText)findViewById(R.id.submit_question_body)).setText(pm.getQBody(this));
        super.onResume();
    }
    
    private class submitQuestionTask extends AsyncTask<Void, Void, Boolean> {
    	private NewQuestionController controller;
    	private Context ctx;
    	
    	public submitQuestionTask(Context ctx){
    		this.ctx = ctx;
    	}
    	
    	@Override
		protected void onPreExecute() {
    		pd = new ProgressDialog(ctx);
			pd.setTitle("Submitting question...");
			pd.setMessage("Please wait.");
			pd.setCancelable(false);
			pd.setIndeterminate(true);
			pd.show();
		}
    	
    	@Override
    	protected Boolean doInBackground(Void...voids) {
            try{
            	try{
	        		controller = 
	        				NewQuestionController.create(
	        						ctx, 
	        						((EditText)findViewById(R.id.submit_question_title)).getText().toString(), 
	        						((EditText)findViewById(R.id.submit_question_body)).getText().toString(), 
	        						//MainActivity.mm.getCurrentUser().getId());
	        						(new User("creide", "juvenaldo")).getId());
	        						//TODO: remove this bogus user
	        		
	        		return controller.submit();	        		
	        	}catch(Exception ex){
	        		System.out.println(ex.getMessage());
	        		return false;
	        		//TODO: Deal with things as: user didn't fill out everything
	        	}
            }catch(Exception ex){
            	//TODO: Deal with this
            	return false;
            }
        }

        @Override
        protected void onPostExecute(Boolean result) {
        	if (result){
    			PMClient pm = new PMClient();	        			
    			pm.clearQ(ctx);
        		((EditText)findViewById(R.id.submit_question_title)).setText("");
            	((EditText)findViewById(R.id.submit_question_body)).setText("");
    			if (controller.submitedOffline){
    				finish();
    				Toast.makeText(getApplicationContext(), "Your Question will be posted online when you connect to the internet!", Toast.LENGTH_LONG).show();
    			}else{
        			String aux = controller.getQuestionId();
        			Intent i = new Intent(ctx,QuestionActivity.class);
        			i.putExtra(MainActivity.QUESTION_KEY, aux);
        			i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        			startActivity(i);
    			}
    		}else{    			
    			finish();
    			Toast.makeText(getApplicationContext(), "Something bad happened, try posting your question again!", Toast.LENGTH_LONG).show();
    		}
        	
        	if (pd!=null) {
				pd.dismiss();
			}        	
        }
    }
}
