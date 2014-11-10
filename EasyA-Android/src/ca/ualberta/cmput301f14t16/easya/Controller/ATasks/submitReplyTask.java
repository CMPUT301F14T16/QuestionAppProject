package ca.ualberta.cmput301f14t16.easya.Controller.ATasks;

import org.apache.http.message.BasicNameValuePair;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.widget.TextView;
import android.widget.Toast;
import ca.ualberta.cmput301f14t16.easya.Controller.NewReplyController;
import ca.ualberta.cmput301f14t16.easya.Model.GeneralHelper;
import ca.ualberta.cmput301f14t16.easya.Model.MainModel;
import ca.ualberta.cmput301f14t16.easya.View.QuestionActivity;

public class submitReplyTask extends AsyncTask<Void, Void, Boolean> {
	private ProgressDialog pd;
	private NewReplyController controller;
	private Context ctx;
	private String qId;
	private String aId;
	private String body;
	private TextView tv;
	
	public submitReplyTask(Context ctx, BasicNameValuePair vp, String body, TextView tv){
		this.ctx = ctx;
		this.qId = vp.getName();
		this.aId = vp.getValue();
		this.body = body;
		this.tv = tv;
	}
	
	@Override
	protected void onPreExecute() {
		pd = new ProgressDialog(ctx);
		pd.setTitle("Submitting reply...");
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
        				NewReplyController.create(
        						ctx, 
        						qId, 
        						aId,
        						body,
        						MainModel.getInstance().getCurrentUser().getId());
        		
        		return controller.submit();	        		
        	}catch(Exception ex){
        		System.out.println(ex.getMessage());
        		return false;
        		//Deal with things as: user didn't fill out everything
        	}
        }catch(Exception ex){
        	//Deal with this
        	return false;
        }
    }

    @Override
    protected void onPostExecute(Boolean result) {
    	if (result){
			if (controller.submitedOffline){
				Toast.makeText(ctx, "Your reply will be posted online when you connect to the internet!", Toast.LENGTH_LONG).show();
				tv.setText("");
			}else{
    			String aux = controller.getQuestionId();
    			Intent i = new Intent(ctx,QuestionActivity.class);
    			i.putExtra(GeneralHelper.QUESTION_KEY, aux);
    			i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
    			ctx.startActivity(i);
			}
		}else{
			Toast.makeText(ctx, "Something bad happened, try posting your question again!", Toast.LENGTH_LONG).show();
		}
    	
    	if (pd!=null) {
			pd.dismiss();
		}        	
    }
}