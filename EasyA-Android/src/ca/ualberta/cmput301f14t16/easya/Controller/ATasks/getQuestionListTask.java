package ca.ualberta.cmput301f14t16.easya.Controller.ATasks;

import java.util.List;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import ca.ualberta.cmput301f14t16.easya.Exceptions.NoContentAvailableException;
import ca.ualberta.cmput301f14t16.easya.Model.MainModel;
import ca.ualberta.cmput301f14t16.easya.Model.QuestionList;
import ca.ualberta.cmput301f14t16.easya.Model.Data.PMClient;
import ca.ualberta.cmput301f14t16.easya.View.MainView;

public class getQuestionListTask extends AsyncTask<Void, Void, List<QuestionList>> {
	private Context ctx;
	private ProgressDialog pd;
	private MainView<List<QuestionList>> v;
	private boolean fullLoaded;
	
	public getQuestionListTask(Context ctx, MainView<List<QuestionList>> v){
		this.ctx = ctx;
		this.v = v;
		PMClient pm = new PMClient();
		this.fullLoaded = pm.getAppStatus();
	}
	
	@Override
	protected void onPreExecute() {
		if (!fullLoaded){
			this.pd = new ProgressDialog(ctx);
			pd.setTitle("Loading content...");
			pd.setMessage("Please wait.");
			pd.setCancelable(false);
			pd.setIndeterminate(true);
			pd.show();
		}
	}

	@Override
	protected List<QuestionList> doInBackground(Void...voids) {
        try{
        	try{
	        	if (!fullLoaded){
	        		MainModel.getInstance().getAllUserFavourites();
        	}}catch(NoContentAvailableException ex){} //Do Nothing
        	return MainModel.getInstance().getAllQuestions();
        }catch(NoContentAvailableException ex){
        	return null;
        }
    }

    @Override
    protected void onPostExecute(List<QuestionList> result) {
    	if (pd!=null) {
			pd.dismiss();
		}
    	if (result == null){
    		//TODO: display the no content available screen
    	}else{  
    		v.update(result);    		
    	}
    	if(!fullLoaded){
	    	PMClient pm = new PMClient();
	    	pm.saveAppStatus();
    	}
    	v.stopAnimateSync();
    }
}