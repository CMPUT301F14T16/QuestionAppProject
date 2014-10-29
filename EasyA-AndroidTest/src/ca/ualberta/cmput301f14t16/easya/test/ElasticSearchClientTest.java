package ca.ualberta.cmput301f14t16.easya.test;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.SocketTimeoutException;
import java.util.List;

import android.test.ActivityInstrumentationTestCase2;
import android.util.Log;

import ca.ualberta.cmput301f14t16.easya.Answer;
import ca.ualberta.cmput301f14t16.easya.ESClient;
import ca.ualberta.cmput301f14t16.easya.MainActivity;
import ca.ualberta.cmput301f14t16.easya.NoInternetException;
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
		a.addReply(r);
		q.addReply(r);
		
		// submit question to elastic search client
		try {
			esclient.submitQuestion(q);
		} catch (SocketTimeoutException e) {
			Log.d(LOG_TAG, "Timeout caught!");
			return;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		// Get question back from elastic search
		Question returnedQ = null;
		try {
			returnedQ = esclient.getQuestionById(q.getId());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		assertNotNull(returnedQ);
		
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
		
		// Get question before answer submission.
		Question q = null;
		try {
			q = esclient.getQuestionById(qid);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Log.d(LOG_TAG, "No internet caught!");
			return;
		}
		assertNotNull(q);
		
		// Create answer and add to received question.
		Answer answer = new Answer("Body of answer in test answer submission", "commande@ualberta.ca");
		q.addAnswer(answer);
		
		// Submit answer through ES and receive the associated question.
		try {
			esclient.submitAnswer(answer, qid);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Log.d(LOG_TAG, "No internet caught!");
			return;
		}
		Question rq = null;
		try {
			rq = esclient.getQuestionById(qid);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Log.d(LOG_TAG, "No internet caught!");
			return;
		}
		assertNotNull(rq);
		
		// Test both questions for equality.
		Log.d(LOG_TAG, "Size of q's answers: " + q.getAnswers().size());
		Log.d(LOG_TAG, "Size of rq's answers: " + rq.getAnswers().size());
		assertEquals(q.getAnswers().size(), rq.getAnswers().size());
		
	}
	
	public void testSubmitAndGetQuestionReply() {
		String qid = "15c7827d-636a-48fc-8887-94318b98f95c";
		
		// Get question before answer submission.
		Question q = null;
		try {
			q = esclient.getQuestionById(qid);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			Log.d(LOG_TAG, "No internet caught!");
			return;
		}
		assertNotNull(q);
		int rsize = q.getReplies().size();
		
		// Create reply and manually add to received question.
		Reply reply = new Reply("Body of reply in question reply submission", "commande@ualberta.ca");
		q.addReply(reply);
		
		// Submit reply through ES and receive the associated question.
		try {
			esclient.submitQuestionReply(reply, qid);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Log.d(LOG_TAG, "No internet caught!");
			return;
		}
		
		Question rq = null;
		try {
			rq = esclient.getQuestionById(qid);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Log.d(LOG_TAG, "No internet caught!");
			return;
		}
		assertNotNull(rq);
		int rrsize = rq.getReplies().size();
		
		// Test both questions for equality.
		assertEquals(q.getReplies().size(), rq.getReplies().size());
		assertEquals((rsize + 1), (rrsize));
	}
	
	public void testSubmitAndGetAnswerReply() {
		String qid = "15c7827d-636a-48fc-8887-94318b98f95c";
		String aid = "d8b62533-4ad6-4ca9-9e48-4651b8a4ad8f";
		
		// Get question before answer submission.
		Question q = null;
		try {
			q = esclient.getQuestionById(qid);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			Log.d(LOG_TAG, "No internet caught!");
			return;
		}
		assertNotNull(q);
		Answer a = q.getAnswerById(aid);
		int arsize = a.getReplies().size();
		
		// Create reply and manually add to the answer before submission to ES.
		Reply reply = new Reply("Body of reply in test answer reply submission", "commande@ualberta.ca");
		a.addReply(reply);
		
		// Test add by reference, note that reply was only added to the "extracted" answer.
		assertEquals(a.getReplies().size(), q.getAnswerById(aid).getReplies().size());
		
		// Submit answer reply through ES.
		try {
			esclient.submitAnswerReply(reply, qid, aid);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Log.d(LOG_TAG, "No internet caught!");
			return;
		}
		
		// receive the associated question from ES
		Question rq = null;
		try {
			rq = esclient.getQuestionById(qid);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Log.d(LOG_TAG, "No internet caught!");
			return;
		}
		assertNotNull(rq);
		int arrsize = q.getAnswerById(aid).getReplies().size();
		
		// Test both questions for equality.
		assertEquals(q.getAnswerById(aid).getReplies().size(), rq.getAnswerById(aid).getReplies().size());
		assertEquals((arsize + 1), (arrsize));
	}
	
	public void testSearchByKeyword(){
		List<Question> rqs = null;
		try {
			rqs = esclient.searchQuestionsByQuery("Test", 10);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Log.d(LOG_TAG, "No internet caught!");
			return;
		}
		assertNotNull(rqs);
		assertTrue(rqs.size() > 0);
	}
}
