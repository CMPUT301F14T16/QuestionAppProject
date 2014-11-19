package ca.ualberta.cmput301f14t16.easya.Controller.ATasks;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.widget.EditText;
import android.widget.Toast;
import ca.ualberta.cmput301f14t16.easya.R;
import ca.ualberta.cmput301f14t16.easya.Controller.NewAnswerController;
import ca.ualberta.cmput301f14t16.easya.Model.GeneralHelper;
import ca.ualberta.cmput301f14t16.easya.Model.MainModel;
import ca.ualberta.cmput301f14t16.easya.Model.Data.PMClient;
import ca.ualberta.cmput301f14t16.easya.View.QuestionActivity;

public class submitAnswerTask extends AsyncTask<Void, Void, Boolean> {
	private ProgressDialog pd;
	private NewAnswerController controller;
	private Context ctx;
	private Activity act;
	private String qId;
	private String body;
	private byte[] pb;
	
	public submitAnswerTask(Activity act, String question, String body, byte[] pb){
		this.act = act;
		this.ctx = act;
		this.qId = question;
		this.body = body;
		this.pb = pb;
	}
	
	@Override
	protected void onPreExecute() {
		pd = new ProgressDialog(ctx);
		pd.setTitle("Submitting answer...");
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
        				NewAnswerController.create(
        						ctx, 
        						qId, 
        						body, 
        						pb,
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
    		PMClient pm = new PMClient();	        			
			pm.clearA(ctx);
			((EditText)act.findViewById(R.id.submit_answer_body)).setText("");
			if (controller.submitedOffline){
				act.finish();
				Toast.makeText(ctx, "Your answer will be posted online when you connect to the internet!", Toast.LENGTH_LONG).show();
			}else{        			
    			String aux = controller.getQuestionId();
    			Intent i = new Intent(ctx,QuestionActivity.class);
    			i.putExtra(GeneralHelper.QUESTION_KEY, aux);
    			i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
    			act.startActivity(i);
    			act.finish();
			}
		}else{    			
			act.finish();
			Toast.makeText(ctx, "Something bad happened, try posting your answer again!", Toast.LENGTH_LONG).show();
		}
    	
    	if (pd!=null) {
			pd.dismiss();
		}        	
    }
}
