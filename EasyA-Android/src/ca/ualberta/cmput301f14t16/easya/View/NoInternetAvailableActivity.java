package ca.ualberta.cmput301f14t16.easya.View;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;
import ca.ualberta.cmput301f14t16.easya.R;
import ca.ualberta.cmput301f14t16.easya.Controller.ATasks.getQuestionTask;
import ca.ualberta.cmput301f14t16.easya.Exceptions.NoContentAvailableException;
import ca.ualberta.cmput301f14t16.easya.Model.GeneralHelper;
import ca.ualberta.cmput301f14t16.easya.Model.MainModel;
import ca.ualberta.cmput301f14t16.easya.Model.Question;

public class NoInternetAvailableActivity extends Activity {
	private Question question;
	private String qId;
	public Menu menu;
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.question_nointernet);
		getActionBar().setTitle("Question");
		this.qId = (getIntent()).getStringExtra(GeneralHelper.QUESTION_KEY);
		try {
			this.question=MainModel.getInstance().getQuestionById(this.qId);
		} catch (NoContentAvailableException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	
	public void AddNewAnswer(View v){
		Intent i = new Intent(v.getContext(), SubmitAnswerActivity.class);
		i.putExtra(GeneralHelper.AQUESTION_KEY, qId);
		this.startActivity(i);
	}

}