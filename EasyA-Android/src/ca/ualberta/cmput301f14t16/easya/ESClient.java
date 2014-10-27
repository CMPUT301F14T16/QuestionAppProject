package ca.ualberta.cmput301f14t16.easya;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

/**
 * Design rationale: caller/user of ESClient should handle exceptions.
 * 
 * @reference https://github.com/rayzhangcl/ESDemo/blob/master/ESDemo/src/ca/ualberta/cs/CMPUT301/chenlei/ESClient.java on Oct 23, 2014
 * @author Brett Commandeur
 */
public class ESClient {
	
	//ElasticSeach Urls
	private static final String HOST_URL = "http://cmput301.softwareprocess.es:8080/testing/t16question/";
	
	// JSON Utilities
	private Gson gson = new Gson();

	/**
	 * Gets a particular question from elastic search.
	 * 
	 * @param id			The id of the question to be retrieved.
	 * @return				A question object matching the given id.
	 * @throws IOException
	 */
	public Question getQuestionById(String id) throws IOException {

		// Get a response after submitting the request for the question.
		String content = HttpHelper.getFromUrl(HOST_URL + id);
			
		// We have to tell GSON what type we expect
		Type esGetResponseType = new TypeToken<ESGetResponse<Question>>(){}.getType();
		
		// Now we expect to get a Index-Creation response
		ESGetResponse<Question> esGetResponse = gson.fromJson(content, esGetResponseType);
		
		// Extract question from elastic search response;
		Question question = esGetResponse.getSource();
		
		return question;
	}
	
	/**
	 * Submits a question to elastic search.
	 * 
	 * @param question		The question object to be submitted.	
	 * @throws IOException
	 */
	public void submitQuestion(Question question) throws IOException {
		String json = gson.toJson(question);
		
		// Post the object to the webservice
		HttpHelper.putToUrl(HOST_URL + question.getId(), json);
	}
	

	/**
	 * Submits an answer-to-a-question to elastic search.
	 * 
	 * @param answer		The answer object to be submitted.
	 * @param qid			The id of the question the answer should be added to.
	 * @throws IOException
	 */
	public void submitAnswer(Answer answer, String qid) throws IOException {
		Question q = this.getQuestionById(qid);
		q.addAnswer(answer);
		
		String json = gson.toJson(q.getAnswers());
		String updateStr = "{ \"doc\":{ \"answers\":" + json + "} }";
		
		HttpHelper.putToUrl(HOST_URL + qid +"/_update", updateStr);
	}

	/**
	 * Submits a reply-to-a-question to elastic search.
	 * 
	 * @param reply			The reply object to be submitted.
	 * @param qid			The id of the question the reply should be added to.
	 * @throws IOException
	 */
	public void submitQuestionReply(Reply reply, String qid) throws IOException {
		Question q = this.getQuestionById(qid);
		q.addReply(reply);
		
		String json = gson.toJson(q.getReplies());
		String updateStr = "{ \"doc\":{ \"replies\":" + json + "} }";
		
		HttpHelper.putToUrl(HOST_URL + qid + "/_update", updateStr);
		
	}

	/**
	 * Submits a reply-to-an-answer to elastic search.
	 * 
	 * @param reply			The reply object to be submitted.
	 * @param qid			The id of the question containing the answer.
	 * @param aid			The id of the answer the reply should be added to.
	 * @throws IOException
	 */
	public void submitAnswerReply(Reply reply, String qid, String aid) throws IOException {
		Question q = this.getQuestionById(qid);
		q.getAnswerById(aid).addReply(reply);
		
		String json = gson.toJson(q.getAnswers());
		String updateStr = "{ \"doc\":{ \"answers\":" + json + "} }";
		
		HttpHelper.putToUrl(HOST_URL + qid +"/_update", updateStr);
	}

	/**
	 * Submits a search to elastic search and returns a list of questions containing content that matches the search.
	 * 
	 * @param query
	 * @return A list of up to 100 resulting questions.
	 * @throws UnsupportedEncodingException
	 * @throws IOException
	 */
	public List<Question> searchQuestionsByQuery(String query) throws UnsupportedEncodingException, IOException {
		List<Question> qlist = new ArrayList<Question>();
		
		String response = HttpHelper.getFromUrl(HOST_URL + "_search/?size=100&q=" + URLEncoder.encode(query, "UTF-8"));
		
		Type esSearchResponseType = new TypeToken<ESSearchResponse<Question>>(){}.getType();
		ESSearchResponse<Question> esResponse = gson.fromJson(response, esSearchResponseType);
		for (ESGetResponse<Question> q : esResponse.getHits()) {
			Question question = q.getSource();
			qlist.add(question);
		}
		
		return qlist;
	}
	
}
