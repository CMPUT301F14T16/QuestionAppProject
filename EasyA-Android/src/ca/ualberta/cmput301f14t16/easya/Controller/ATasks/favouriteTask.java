package ca.ualberta.cmput301f14t16.easya.Controller.ATasks;

import org.apache.http.message.BasicNameValuePair;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.widget.Toast;
import ca.ualberta.cmput301f14t16.easya.Controller.FavouriteController;
import ca.ualberta.cmput301f14t16.easya.Model.GeneralHelper;
import ca.ualberta.cmput301f14t16.easya.Model.MainModel;
import ca.ualberta.cmput301f14t16.easya.View.QuestionActivity;

public class favouriteTask extends AsyncTask<Void, Void, Boolean> {
	private BasicNameValuePair vp;
	private Context ctx;
	
	public favouriteTask(Context ctx, BasicNameValuePair vp){
		this.ctx = ctx;
		this.vp = vp;
	}
	    	
	@Override
	protected Boolean doInBackground(Void...voids) {
        try{
        	try{
        		FavouriteController controller = 
        				FavouriteController.create(
        						MainModel.getInstance().getCurrentUser().getId());	        		
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
    	if (result){ 
			Intent i = new Intent(ctx,QuestionActivity.class);
			i.putExtra(GeneralHelper.QUESTION_KEY, vp.getName());
			i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			ctx.startActivity(i);
		}else{
			Toast.makeText(ctx, "We were unable to save your favourite, check your internet connection and try again!", Toast.LENGTH_LONG).show();
		}     	
    }
}