package ca.ualberta.cmput301f14t16.easya.Controller.ATasks;

import java.util.List;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import ca.ualberta.cmput301f14t16.easya.Exceptions.NoContentAvailableException;
import ca.ualberta.cmput301f14t16.easya.Model.MainModel;
import ca.ualberta.cmput301f14t16.easya.Model.Question;
import ca.ualberta.cmput301f14t16.easya.View.MainView;


public class getQuestionTask extends AsyncTask<Void, Void, Question> {
	private Context ctx;
	private ProgressDialog pd;
	private String qId;
	private MainView<Question> v;
	
	public getQuestionTask(Context ctx, MainView<Question> v, String qId){
		this.ctx = ctx;
		this.qId = qId;
		this.v = v;
	}
	
	@Override
	protected void onPreExecute() {
		pd = new ProgressDialog(ctx);
		pd.setTitle("Loading question...");
		pd.setMessage("Please wait.");
		pd.setCancelable(false);
		pd.setIndeterminate(true);
		pd.show();
	}
	
	@Override
    protected Question doInBackground(Void...voids) {
    	try{
    		if (this.qId == null || this.qId.equals(""))
    			return null;
    		return MainModel.getInstance().getQuestionById(this.qId);
    	}catch(NoContentAvailableException ex){
    		return null;
    	}
    }

	@Override
    protected void onPostExecute(Question result){
		if (pd!=null) {
			pd.dismiss();
		}
    	if (result == null){
    		//TODO: display the no content available screen
    	}else{  
    		v.update(result);    		
    	}
    }
}
