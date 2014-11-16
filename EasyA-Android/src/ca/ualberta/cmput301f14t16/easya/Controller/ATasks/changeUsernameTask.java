package ca.ualberta.cmput301f14t16.easya.Controller.ATasks;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.widget.EditText;
import android.widget.TextView;
import ca.ualberta.cmput301f14t16.easya.R;
import ca.ualberta.cmput301f14t16.easya.Controller.ChangeUsernameController;
import ca.ualberta.cmput301f14t16.easya.Model.MainModel;

public class changeUsernameTask extends AsyncTask<Void, Void, Boolean> {
	private ChangeUsernameController controller;
	private Context ctx;
	private DialogInterface d;
	private ProgressDialog pd;
	
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
			MainModel.getInstance().notifyViews();
		}else{    			
			new AlertDialog.Builder(ctx)
		    .setTitle("No connection found")
		    .setMessage("We were unable to connect to our servers to change your username. Try checking your internet connection and change it again.")
		    .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {}})
		    .setIcon(android.R.drawable.ic_dialog_alert)
		    .show();  
		}      	
    }
}