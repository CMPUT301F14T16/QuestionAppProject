package ca.ualberta.cmput301f14t16.easya.test;

import java.util.ArrayList; 
import java.util.Calendar;
import java.util.List;

import ca.ualberta.cmput301f14t16.easya.R;
import ca.ualberta.cmput301f14t16.easya.Controller.ATasks.searchQuestionTask;
import ca.ualberta.cmput301f14t16.easya.Model.Answer;
import ca.ualberta.cmput301f14t16.easya.Model.MainModel;
import ca.ualberta.cmput301f14t16.easya.Model.Question;
import ca.ualberta.cmput301f14t16.easya.Model.QuestionList;
import ca.ualberta.cmput301f14t16.easya.Model.User;
import ca.ualberta.cmput301f14t16.easya.View.MainView;
import ca.ualberta.cmput301f14t16.easya.View.SearchActivity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.test.ActivityInstrumentationTestCase2;
import android.widget.TextView;
import junit.framework.TestCase;

public class SearchActivityTest extends ActivityInstrumentationTestCase2<SearchActivity> {
	
	public SearchActivityTest() {
		super(SearchActivity.class);
	}



	private Context ctx;
	private ProgressDialog pd;
	private String query;
	private MainView<List<QuestionList>> v;
	private String QId;
	private String AId;
	private String email;
	private String username;
	private AsyncTask<Void, Void, List<QuestionList>> searchquestionTask;
	private List<QuestionList> lst;
	
	

	public void testSearchTest() {
		Question q1 = new Question("Title Submission Test", "Body of Question", "test@ualberta.ca");
		Answer a1 = new Answer("Body of answer", "someone@ualberta.ca");
		q1.addAnswer(a1);
		QId = q1.getId();
		AId = a1.getId();
		//Question q2 = new Question("Title Submission Test2","Body of Question2","test2@ualberta.ca");
		email = "lingbo19.tang@gmail.com";
		username = "lingbo747";
		query = "Title Submission Test";
		User user = new User(email, username);
		String userid = user.getId();
		Calendar date = Calendar.getInstance();
		double[] coordinates = new double[2];
		coordinates[0] = 52.45;
		coordinates[1] = 13.37;
		String location = "Berlin German";
		QuestionList qst = new QuestionList(QId, "Title Submission Test", username, userid,
				"Body of answer", "1", false, date, coordinates, location);
		List<QuestionList> lst = new ArrayList<QuestionList>();
		lst.add(qst);
		
		Intent search = new Intent();
		setActivityIntent(search);
		SearchActivity sa = getActivity();
		ctx = sa.getApplicationContext();
		((TextView)sa.findViewById(R.id.drawer_username)).setText(MainModel
				.getInstance().getCurrentUser().getUsername());		
		assertTrue((new searchQuestionTask(ctx, sa, query)).execute().equals(qst));
		
	}
}