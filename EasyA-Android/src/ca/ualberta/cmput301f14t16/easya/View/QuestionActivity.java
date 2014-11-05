package ca.ualberta.cmput301f14t16.easya.View;

import ca.ualberta.cmput301f14t16.easya.R;
import ca.ualberta.cmput301f14t16.easya.Exceptions.NoContentAvailableException;
import ca.ualberta.cmput301f14t16.easya.Model.Question;
import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.LinearLayout;

public class QuestionActivity extends Activity {
	private Question q;
	
	@Override
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.question_view);
		getActionBar().setDisplayHomeAsUpEnabled(true);
        getActionBar().setHomeButtonEnabled(true);        
        (new GetQuestionListTask()).execute();
	}	
	
	private void ShowNoContentView(){
		//TODO: display the NoContentView
		//setContentView(R.layout.no_content_view);
		getActionBar().setDisplayHomeAsUpEnabled(true);
        getActionBar().setHomeButtonEnabled(true);        
		return;
	}
	
	private void SetAdapter(Question q){
		this.q = q;
    	QuestionViewAdapter adapter = new QuestionViewAdapter(this, this.q, (LinearLayout)findViewById(R.id.question_scrollview_container));
        adapter.build();
	}
	
	private class GetQuestionListTask extends AsyncTask<Void, Void, Question> {
        protected Question doInBackground(Void...voids) {
        	try{
        		String aux = (getIntent()).getStringExtra(MainActivity.QUESTION_KEY);
        		if (aux == null || aux.equals(""))
        			return null;
        		return MainActivity.mm.getQuestionById(aux);
        	}catch(NoContentAvailableException ex){
        		return null;
        	}
        }

        protected void onPostExecute(Question result){
        	if (result == null){
        		ShowNoContentView();
        	}else{
        		SetAdapter(result);
        	}
        }
    }
}
