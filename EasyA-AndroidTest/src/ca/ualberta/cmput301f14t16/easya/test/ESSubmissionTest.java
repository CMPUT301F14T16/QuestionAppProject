package ca.ualberta.cmput301f14t16.easya.test;

import java.lang.reflect.Type;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import ca.ualberta.cmput301f14t16.easya.Model.Answer;
import ca.ualberta.cmput301f14t16.easya.Model.Data.ESGetResponse;
import ca.ualberta.cmput301f14t16.easya.Model.Data.ESSubmission;
import ca.ualberta.cmput301f14t16.easya.View.MainActivity;
import ca.ualberta.cmput301f14t16.easya.Model.Question;
import android.test.ActivityInstrumentationTestCase2;

public class ESSubmissionTest extends ActivityInstrumentationTestCase2<MainActivity> {

	Gson gson;
	
	public ESSubmissionTest() {
		super(MainActivity.class);
	}
	
	public void setUp() {
		gson = new Gson();
	}
	
	public void testESSubmissionCreation() {
		Question question = new Question("Test Title", "Test Body", "test@email.com");
		ESSubmission submission = new ESSubmission(question.getClass().toString(), question, null, null);
		String json = gson.toJson(submission);
		
		Type esSubmissionType = new TypeToken<ESSubmission>(){}.getType();
		ESSubmission submissionFromJson = gson.fromJson(json, esSubmissionType);
		
		assertTrue(submission.getType().equals(Question.class.toString()));
		assertTrue(submission.getType().equals(submissionFromJson.getType()));
		
		Answer answer = new Answer("Test Body", "test@email.com");
		submission = new ESSubmission(answer.getClass().toString(), answer, question.getId(), null);
		assertTrue(submission.getType().equals(Answer.class.toString()));
	}
}
