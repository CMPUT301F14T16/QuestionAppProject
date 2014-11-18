package ca.ualberta.cmput301f14t16.easya.Controller.ATasks;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.widget.EditText;
import android.widget.Toast;
import ca.ualberta.cmput301f14t16.easya.R;
import ca.ualberta.cmput301f14t16.easya.Controller.NewQuestionController;
import ca.ualberta.cmput301f14t16.easya.Model.GeneralHelper;
import ca.ualberta.cmput301f14t16.easya.Model.MainModel;
import ca.ualberta.cmput301f14t16.easya.Model.Data.PMClient;
import ca.ualberta.cmput301f14t16.easya.View.QuestionActivity;

public class submitQuestionTask extends AsyncTask<Void, Void, Boolean> {
	private ProgressDialog pd;
	private NewQuestionController controller;
	private Context ctx;
	private String title;
	private String body;
	private byte[] pb;
	private Activity act;
	
	public submitQuestionTask(Activity act, String title, String body, byte[] pb){
		this.act = act;
		this.ctx = act;
		this.title = title;
		this.body = body;
		this.pb = pb;
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
        						this.title, 
        						this.body, 
        						this.pb,
        						MainModel.getInstance().getCurrentUser().getId());
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
    		((EditText)act.findViewById(R.id.submit_question_title)).setText("");
        	((EditText)act.findViewById(R.id.submit_question_body)).setText("");
			if (controller.submitedOffline){
				act.finish();
				Toast.makeText(ctx, "Your Question will be posted online when you connect to the internet!", Toast.LENGTH_LONG).show();
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
			Toast.makeText(ctx, "Something bad happened, try posting your question again!", Toast.LENGTH_LONG).show();
		}
    	
    	if (pd!=null) {
			pd.dismiss();
		}
    	MainModel.getInstance().notifyViews();
    }
}