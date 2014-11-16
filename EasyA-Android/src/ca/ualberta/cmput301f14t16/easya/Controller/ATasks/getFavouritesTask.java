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

public class getFavouritesTask extends AsyncTask<Void, Void, List<QuestionList>> {
	private ProgressDialog pd;
	private MainView<List<QuestionList>> v;
	
	public getFavouritesTask(MainView<List<QuestionList>> v){
		this.v = v;
	}
	
	@Override
	protected List<QuestionList> doInBackground(Void...voids) {
        try{        	
        	return MainModel.getInstance().getAllUserFavourites();
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
    }
}