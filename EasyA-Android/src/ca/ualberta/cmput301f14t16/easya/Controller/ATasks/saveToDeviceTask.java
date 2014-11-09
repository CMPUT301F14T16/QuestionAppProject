package ca.ualberta.cmput301f14t16.easya.Controller.ATasks;

import android.os.AsyncTask;
import android.widget.Toast;
import ca.ualberta.cmput301f14t16.easya.Exceptions.NoContentAvailableException;
import ca.ualberta.cmput301f14t16.easya.Model.MainModel;
import ca.ualberta.cmput301f14t16.easya.Model.Data.ContextProvider;

public class saveToDeviceTask extends AsyncTask<Void, Void, Boolean> {
	private String qId;
	
	public saveToDeviceTask(String qid){
		this.qId = qid;
	}
	
	@Override
	protected void onPreExecute() {
		Toast.makeText(ContextProvider.get(), "Your question is being saved to the device.", Toast.LENGTH_SHORT).show();
	}
	
	@Override
	protected Boolean doInBackground(Void...voids) {
        try{
        	return(MainModel.getInstance().getQuestionById(qId) != null);
        }catch(NoContentAvailableException ex){
        	return false;
        }
    }

    @Override
    protected void onPostExecute(Boolean result) {    	
    	if (!result){  
			Toast.makeText(ContextProvider.get(), "We were unable to save this question to the device. Check your internet connection and try again.", Toast.LENGTH_LONG).show();
		}      	
    }
}