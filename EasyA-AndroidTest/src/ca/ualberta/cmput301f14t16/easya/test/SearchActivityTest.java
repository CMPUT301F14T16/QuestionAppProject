package ca.ualberta.cmput301f14t16.easya.test;

import java.io.IOException;
import java.util.ArrayList;  
import ca.ualberta.cmput301f14t16.easya.Model.Data.*;
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
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.test.ActivityInstrumentationTestCase2;
import android.widget.TextView;
import junit.framework.TestCase;

/**
 * 
 *  
 * @author Lingbo Tang,Klinton W Shmeid 
 *
 *
 */

//This test covers the Use case of search Question
//Search is complicated,it involves all Model,View and Controller
//Therefore, we use activity test.

public class SearchActivityTest extends ActivityInstrumentationTestCase2<SearchActivity> {
	
	// Get all needed test object
	private Context ctx;
	private ProgressDialog pd;
	private MainView<List<QuestionList>> v;
	private String QId;
	private String AId;
	private String QId2;
	private String AId2;
	private String email;
	private String username;
	
	private List<QuestionList> lst;
	private List<QuestionList> rst;
	private ESClient esclient;
	
	public SearchActivityTest() {
		super(SearchActivity.class);
	}
	
	public void testSearchActivityTest() {
		
		//Initialize Test Object
		ESClient esclient = new ESClient();
		Question q1 = new Question("Title Submission Test", "Body of Question", "test@ualberta.ca");
		Answer a1 = new Answer("Body of answer", "someone@ualberta.ca");
		Question q2 = new Question("Different", "Different", "test2@ualberta.ca");
		Answer a2 = new Answer("Different", "someone2@ualberta.ca");
		q1.addAnswer(a1);
		q2.addAnswer(a2);
		QId = q1.getId();
		AId = a1.getId();
		QId2 = q2.getId();
		AId2 = a2.getId();
		
		//Initialize Result set
		email = "lingbo19.tang@gmail.com";
		username = "lingbo747";
		String query = "Title Submission Test";
		User user = new User(email, username);
		String userid = user.getId();
		Calendar date = Calendar.getInstance();
		double[] coordinates = new double[2];
		coordinates[0] = 52.45;
		coordinates[1] = 13.37;
		String location = "Berlin German";
		QuestionList qst = new QuestionList(QId, "Title Submission Test", username, userid,
				"Body of answer", 1, false, date, coordinates, location);
		QuestionList qst2 = new QuestionList(QId2, "Different", username, userid,
				"Different", 1, false, date, coordinates, location);

		List<QuestionList> rst = new ArrayList<QuestionList>();
		rst.add(qst);
		List<QuestionList> rst2 = new ArrayList<QuestionList>();
		rst2.add(qst2);
		
		//Submit the new test Question
		try{
			assertTrue(esclient.submitQuestion(q1));
		}catch(IOException ex) {
			ex.printStackTrace();
		}
		
		// Test submit by parsing the query from search bar
		Intent intent = new Intent();
		intent.putExtra((SearchManager.QUERY).trim(),"Title Submission Test");
		query = intent.getStringExtra((SearchManager.QUERY).trim());
		setActivityIntent(intent);
		SearchActivity sa = getActivity();
		try{
			lst = esclient.searchQuestionListsByQuery(query, 1);
		}catch (IOException ex) {
			assertFalse(false);
		}
		
		// Test if the result set is not null and correct
		assertNotNull(lst);
		assertFalse(lst.get(0).equals(qst2));
		assertEquals(lst.get(0).getTitle(),"Title Submission Test");
		
		
		
		
	}
}
