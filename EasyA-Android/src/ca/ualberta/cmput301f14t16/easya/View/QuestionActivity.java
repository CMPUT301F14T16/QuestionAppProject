package ca.ualberta.cmput301f14t16.easya.View;

import ca.ualberta.cmput301f14t16.easya.R;
import ca.ualberta.cmput301f14t16.easya.Controller.ATasks.getQuestionTask;
import ca.ualberta.cmput301f14t16.easya.Model.GeneralHelper;
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
public class QuestionActivity extends Activity implements MainView<Question> {
	private static Question question;
	
	@Override
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.question_view);
		getActionBar().setDisplayHomeAsUpEnabled(true);
        getActionBar().setHomeButtonEnabled(true);  
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
        // Pass the event to ActionBarDrawerToggle, if it returns
        // true, then it has handled the app icon touch event
        if (item.getItemId() == R.id.menu_question_favourite){
        	Toast.makeText(getApplicationContext(), "To be implemented", Toast.LENGTH_SHORT).show();
        	//TODO: favourite a question
        	return true;
        }
        return super.onOptionsItemSelected(item);
    }
	
	public void AddNewAnswer(View v){
		if (question != null){
			Intent i = new Intent(v.getContext(), SubmitAnswerActivity.class);
	    	//i.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
	    	i.putExtra(GeneralHelper.AQUESTION_KEY, question.getId());
	        this.startActivity(i);
		}
	}

	@Override
	public void update() {
		(new getQuestionTask(this, this,(getIntent()).getStringExtra(GeneralHelper.QUESTION_KEY))).execute();	
	}

	@Override
	public void update(Question q) {
    	SetAdapter(q);
	}
}
