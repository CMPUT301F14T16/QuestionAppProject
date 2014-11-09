package ca.ualberta.cmput301f14t16.easya.Controller.ATasks;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.widget.EditText;
import ca.ualberta.cmput301f14t16.easya.R;
import ca.ualberta.cmput301f14t16.easya.Controller.NewUserController;
import ca.ualberta.cmput301f14t16.easya.Exceptions.NoContentAvailableException;
import ca.ualberta.cmput301f14t16.easya.Exceptions.NoInternetException;
import ca.ualberta.cmput301f14t16.easya.Model.MainModel;
import ca.ualberta.cmput301f14t16.easya.Model.User;
import ca.ualberta.cmput301f14t16.easya.View.MainActivity;


public class signinTask extends AsyncTask<Void, Void, Boolean> {
	private NewUserController controller;
	private Context ctx;
	private ProgressDialog pd;
	private boolean userFound;	
	private Activity a;
	
	public signinTask(Activity a){
		this.a = a;
		this.ctx = (Context)a;
	}
	
	@Override
	protected void onPreExecute() {
		pd = new ProgressDialog(ctx);
		pd.setTitle("Processing your user...");
		pd.setMessage("Please wait.");
		pd.setCancelable(false);
		pd.setIndeterminate(true);
		pd.show();
	}
	
	@Override
	protected Boolean doInBackground(Void...voids) {
        try{
        	try{
        		String email = ((EditText)a.findViewById(R.id.welcome_email)).getText().toString();
        		String username = ((EditText)a.findViewById(R.id.welcome_username)).getText().toString();
        		
        		try{
        			User auxUser = MainModel.getInstance().getUserByEmail(email);
        			MainModel.getInstance().saveMainUser(auxUser);
        			this.userFound = true;
	        		MainModel.getInstance().updateUsername(auxUser);
        			return true;
        		}catch(NoContentAvailableException ex){
        			controller = 
	        				NewUserController.create(
	        						ctx, 
	        						email, 
	        						username);    	        		
	        		return controller.submit();	    
        		}catch(NoInternetException ex){
        			return false;
        		}            		    		
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
    		if (userFound){
    			new AlertDialog.Builder(ctx)
    		    .setTitle("User found!")
    		    .setMessage("Yay! We found your user in our server, we'll now load all your saved preferences and created content!")
    		    .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
    		        public void onClick(DialogInterface dialog, int which) {
    		        	Intent i = new Intent(ctx, MainActivity.class);
    	    			i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
    	    			ctx.startActivity(i);
    		        }
    		     })
    		    .setIcon(android.R.drawable.ic_dialog_info)
    		    .show();
    		}else{
    			Intent i = new Intent(ctx, MainActivity.class);
    			i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
    			ctx.startActivity(i);
    		}
		}else{    			
			new AlertDialog.Builder(ctx)
		    .setTitle("No connection found")
		    .setMessage("We were unable to connect to our servers to create your user. Try checking your internet connection and sign in again.")
		    .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
		        public void onClick(DialogInterface dialog, int which) {    		        	
		        }
		     })
		    .setIcon(android.R.drawable.ic_dialog_alert)
		    .show();    		
		}      	
    }
}