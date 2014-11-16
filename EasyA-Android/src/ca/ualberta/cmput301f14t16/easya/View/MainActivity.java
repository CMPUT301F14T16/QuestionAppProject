package ca.ualberta.cmput301f14t16.easya.View;

import ca.ualberta.cmput301f14t16.easya.R;
import ca.ualberta.cmput301f14t16.easya.Controller.ATasks.getQuestionListTask;
import ca.ualberta.cmput301f14t16.easya.Model.MainModel;
import ca.ualberta.cmput301f14t16.easya.Model.Queue;
import ca.ualberta.cmput301f14t16.easya.Model.Data.PMClient;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * 
 * @author Cauani
 *
 */
public class MainActivity extends MasterActivity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		((LinearLayout)findViewById(R.id.main_question_holder)).setVisibility(View.VISIBLE);
		position = 0;
    }
	
    @Override
    public void onDestroy() {
        //Free up the thread
        Queue.getInstance().Stop();
        PMClient pm = new PMClient();
        pm.clearA(this);
        pm.clearQ(this);
        super.onDestroy();
    }
    
    @Override
	protected void startUpdate(){
		((TextView)findViewById(R.id.drawer_username)).setText(MainModel.getInstance().getCurrentUser().getUsername());
		update(MainModel.getInstance().getAllCachedQuestions());
		(new getQuestionListTask(this, this)).execute();
	}
    
    @Override
    protected boolean closeViewOnSwitch(){
    	return false;
    }
}