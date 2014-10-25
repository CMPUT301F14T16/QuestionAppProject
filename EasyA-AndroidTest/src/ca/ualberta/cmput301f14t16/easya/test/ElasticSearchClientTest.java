package ca.ualberta.cmput301f14t16.easya.test;

import android.test.ActivityInstrumentationTestCase2;
import android.util.Log;

import ca.ualberta.cmput301f14t16.easya.Answer;
import ca.ualberta.cmput301f14t16.easya.ESClient;
import ca.ualberta.cmput301f14t16.easya.MainActivity;
import ca.ualberta.cmput301f14t16.easya.Question;
import ca.ualberta.cmput301f14t16.easya.Reply;

/**
 * 
 * @author Brett Commandeur
 *
 */
public class ElasticSearchClientTest extends ActivityInstrumentationTestCase2<MainActivity> {
	
	private static final String LOG_TAG = "ElasticSearchClientTest";
	private ESClient esclient;
	
	public ElasticSearchClientTest() {
		super(MainActivity.class);
	}
	
	public void setUp() {
		esclient = new ESClient();
	}
	
	public void testSubmitAndGetQuestion() {
		
		// Create a question for testing
		Question q = new Question("Title Submission Test", 
				"Body of Question", "test@ualberta.ca");
		
		Answer a = new Answer("Body of answer", "someone@ualberta.ca");
		q.addAnswer(a);
		
		Reply r = new Reply("Body of reply", "reply@ualberta.ca");
		q.addReplyToAnswer(r, 0);
		q.addReply(r);
		
		
		// submit question to elastic search client
		esclient.submitQuestion(q);
		
		// Get question back from elastic search
		Question returnedQ = esclient.getQuestionById(q.getId());
		
		Log.d(LOG_TAG, "Question id is " + q.getId());
		
		// Test the returned question.
		assertNotNull(returnedQ);
		assertEquals(q.getTitle(), returnedQ.getTitle());
		assertEquals(q.getBody(), returnedQ.getBody());
		assertEquals(q.getAuthorId(), returnedQ.getAuthorId());
		assertEquals(q.getDate().toString(), returnedQ.getDate().toString());
		assertEquals(q.getId(), returnedQ.getId());
		assertEquals(q.getAnswerCount(), q.getAnswers().size());
		assertEquals(returnedQ.getAnswerCount(), returnedQ.getAnswers().size());
		assertEquals(q.getAnswers().size(), returnedQ.getAnswers().size());
	}
	
	public void testSubmitAndGetAnswer () {
		String qid = "15c7827d-636a-48fc-8887-94318b98f95c";
		
		Question q = esclient.getQuestionById(qid);
		Answer answer = new Answer("Body of answer in test answer submission", "commande@ualberta.ca");
		q.addAnswer(answer);
		
		esclient.submitAnswer(answer, qid);
		Question rq = esclient.getQuestionById(qid);
		
		Log.d(LOG_TAG, "Size of q: " + q.getAnswers().size());
		Log.d(LOG_TAG, "Size of rq: " + rq.getAnswers().size());
		assertEquals(q.getAnswers().size(), rq.getAnswers().size());
		
	}
}
