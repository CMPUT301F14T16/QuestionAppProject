package ca.ualberta.cmput301f14t16.easya.View;

import ca.ualberta.cmput301f14t16.easya.R;
import ca.ualberta.cmput301f14t16.easya.Controller.ATasks.getQuestionTask;
import ca.ualberta.cmput301f14t16.easya.Model.GeneralHelper;
import ca.ualberta.cmput301f14t16.easya.Model.MainModel;
import ca.ualberta.cmput301f14t16.easya.Model.Question;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

/**
 * 
 * @author Cauani
 *
 */
public class QuestionActivity extends SecureActivity implements MainView<Question> {
	private static Question question;
	private String qId;
	
	@Override
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.question_view);
		getActionBar().setDisplayHomeAsUpEnabled(true);
        getActionBar().setHomeButtonEnabled(true);
        MainModel.getInstance().addView(this);
        this.qId = (getIntent()).getStringExtra(GeneralHelper.QUESTION_KEY);	
        update();
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.question, menu);
		return true;
	}
		
	private void SetAdapter(Question q){
		question = q;
    	QuestionViewAdapter adapter = new QuestionViewAdapter(this, question, (LinearLayout)findViewById(R.id.question_scrollview_container));
        adapter.build();
	}
		
	@Override
    public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
	    	case android.R.id.home:
		        finish();
		        break;
	    	case R.id.menu_question_favourite:
	    		Toast.makeText(getApplicationContext(), "To be implemented", Toast.LENGTH_SHORT).show();
    			break;
		 }
		 return true;        
    }
	
	public void AddNewAnswer(View v){
		if (question != null){
			Intent i = new Intent(v.getContext(), SubmitAnswerActivity.class);
	    	i.putExtra(GeneralHelper.AQUESTION_KEY, question.getId());
	        this.startActivity(i);
		}
	}
	
	@Override
	public void onDestroy(){
		MainModel.getInstance().deleteView(this);
		super.onDestroy();
	}
	
	@Override
	public void update() {
		runOnUiThread(new Runnable() {
			@Override
			public void run() {
				startUpdate();
			}
		});
	}

	private void startUpdate(){
		(new getQuestionTask(this, this, this.qId)).execute();
	}
	
	@Override
	public void update(Question q) {
    	SetAdapter(q);
	}
	
	@Override
	public void stopAnimateSync(){}
}
