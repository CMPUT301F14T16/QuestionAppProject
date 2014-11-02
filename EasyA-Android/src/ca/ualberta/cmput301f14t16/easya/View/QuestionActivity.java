package ca.ualberta.cmput301f14t16.easya.View;

import ca.ualberta.cmput301f14t16.easya.R;
import ca.ualberta.cmput301f14t16.easya.Exceptions.NoContentAvailableException;
import ca.ualberta.cmput301f14t16.easya.Model.Answer;
import ca.ualberta.cmput301f14t16.easya.Model.MainModel;
import ca.ualberta.cmput301f14t16.easya.Model.Question;
import android.app.Activity;
import android.os.Bundle;
import android.widget.LinearLayout;

public class QuestionActivity extends Activity {
	private Question q;
	
	@Override
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		
		try{
			MainModel mm = new MainModel(getApplicationContext());
			//Get the question id from the intent, and retrieve a question object from ESClient
			
			this.q = mm.getQuestionById((getIntent()).getStringExtra(MainActivity.QUESTION_KEY));
			// */
		}catch (NoContentAvailableException ex){
			//TODO: display the NoContentAvailable layout instead of the question_view
			
			//setContentView(R.layout.no_content_view);
			getActionBar().setDisplayHomeAsUpEnabled(true);
	        getActionBar().setHomeButtonEnabled(true);
			return;
			//test only
			/*
			this.q = new Question("Title", "Body", "userI");
			this.q.addAnswer(new Answer("New Answer 1", "NoAuthor"));
			this.q.addAnswer(new Answer("New Answer 2", "NoAuthor"));
			this.q.addAnswer(new Answer("New Answer 3", "NoAuthor"));
			this.q.addAnswer(new Answer("New Answer 1", "NoAuthor"));
			this.q.addAnswer(new Answer("New Answer 2", "NoAuthor"));
			this.q.addAnswer(new Answer("New Answer 3", "NoAuthor"));
			this.q.addAnswer(new Answer("New Answer 1", "NoAuthor"));
			this.q.addAnswer(new Answer("New Answer 2", "NoAuthor"));
			this.q.addAnswer(new Answer("New Answer 3", "NoAuthor"));
			this.q.addAnswer(new Answer("New Answer 1", "NoAuthor"));
			this.q.addAnswer(new Answer("New Answer 2", "NoAuthor"));
			this.q.addAnswer(new Answer("New Answer 3", "NoAuthor"));
			this.q.addAnswer(new Answer("New Answer 1", "NoAuthor"));
			this.q.addAnswer(new Answer("New Answer 2", "NoAuthor"));
			this.q.addAnswer(new Answer("New Answer 3", "NoAuthor"));
			this.q.addAnswer(new Answer("New Answer 1", "NoAuthor"));
			this.q.addAnswer(new Answer("New Answer 2", "NoAuthor"));
			this.q.addAnswer(new Answer("New Answer 3", "NoAuthor"));
			this.q.addAnswer(new Answer("New Answer 1", "NoAuthor"));
			this.q.addAnswer(new Answer("New Answer 2", "NoAuthor"));
			this.q.addAnswer(new Answer("New Answer 3", "NoAuthor"));
			// */
		}catch(Exception ex){
			//TODO: deal with exceptions here
			ex.printStackTrace();
		}
		setContentView(R.layout.question_view);
		getActionBar().setDisplayHomeAsUpEnabled(true);
        getActionBar().setHomeButtonEnabled(true);
        
        QuestionViewAdapter adapter = new QuestionViewAdapter(this, q, (LinearLayout)this.findViewById(R.id.question_scrollview_container));
        adapter.build();
	}	
}
