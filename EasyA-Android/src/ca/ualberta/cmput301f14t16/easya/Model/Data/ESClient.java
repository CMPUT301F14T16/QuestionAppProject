package ca.ualberta.cmput301f14t16.easya.Model.Data;

import java.io.IOException;
import java.lang.reflect.Type;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import ca.ualberta.cmput301f14t16.easya.Model.Answer;
import ca.ualberta.cmput301f14t16.easya.Model.Question;
import ca.ualberta.cmput301f14t16.easya.Model.QuestionList;
import ca.ualberta.cmput301f14t16.easya.Model.Reply;

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
	private static final String HOST_URL = "http://cmput301.softwareprocess.es:8080/testing/";
	private static final String QUESTION_PATH = "t16newquestion/";
	
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
		String content = HttpHelper.getFromUrl(HOST_URL + QUESTION_PATH + id);
			
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
	public boolean submitQuestion(Question question) throws IOException {
		String json = gson.toJson(question);
		
		// Post the object to the webservice
		HttpHelper.putToUrl(HOST_URL + QUESTION_PATH + question.getId(), json);
		
		//TODO: change that based on ESS response
		return true;
	}
	

	/**
	 * Submits an answer-to-a-question to elastic search.
	 * 
	 * @param answer		The answer object to be submitted.
	 * @param qid			The id of the question the answer should be added to.
	 * @throws IOException
	 */
	public boolean submitAnswer(Answer answer, String qid) throws IOException {
		Question q = this.getQuestionById(qid);
		q.addAnswer(answer);
		
		String json = gson.toJson(q.getAnswers());
		String updateStr = "{ \"doc\":{ \"answers\":" + json + "} }";
		
		HttpHelper.putToUrl(HOST_URL + QUESTION_PATH + qid +"/_update", updateStr);
		
		//TODO: change that based on ESS response
		return true;
	}

	/**
	 * Submits a reply-to-a-question to elastic search.
	 * 
	 * @param reply			The reply object to be submitted.
	 * @param qid			The id of the question the reply should be added to.
	 * @throws IOException
	 */
	public boolean submitQuestionReply(Reply reply, String qid) throws IOException {
		Question q = this.getQuestionById(qid);
		q.addReply(reply);
		
		String json = gson.toJson(q.getReplies());
		String updateStr = "{ \"doc\":{ \"replies\":" + json + "} }";
		
		HttpHelper.putToUrl(HOST_URL + QUESTION_PATH + qid + "/_update", updateStr);
		
		//TODO: change that based on ESS response
		return true;
	}

	/**
	 * Submits a reply-to-an-answer to elastic search.
	 * 
	 * @param reply			The reply object to be submitted.
	 * @param qid			The id of the question containing the answer.
	 * @param aid			The id of the answer the reply should be added to.
	 * @throws IOException
	 */
	public boolean submitAnswerReply(Reply reply, String qid, String aid) throws IOException {
		Question q = this.getQuestionById(qid);
		q.getAnswerById(aid).addReply(reply);
		
		String json = gson.toJson(q.getAnswers());
		String updateStr = "{ \"doc\":{ \"answers\":" + json + "} }";
		
		HttpHelper.putToUrl(HOST_URL + QUESTION_PATH + qid +"/_update", updateStr);
		
		//TODO: change that based on ESS response
		return true;
	}

	/**
	 * Submits a search to elastic search and returns a list of questions containing content that matches the search.
	 * 
	 * @param query			The query to search by.
	 * @param numResults	The number of results to return.
	 * @return 				A list of up to the requested number of resulting questions.
	 * @throws IOException	If reading from server fails in any way.
	 */
	public List<Question> searchQuestionsByQuery(String query, int numResults) throws IOException {
		List<Question> qlist = new ArrayList<Question>();
		
		String response = HttpHelper.getFromUrl(HOST_URL + QUESTION_PATH + "_search/?size="+ numResults + "&q=" + URLEncoder.encode(query, "UTF-8"));
		
		Type esSearchResponseType = new TypeToken<ESSearchResponse<Question>>(){}.getType();
		ESSearchResponse<Question> esResponse = gson.fromJson(response, esSearchResponseType);
		for (ESGetResponse<Question> q : esResponse.getHits()) {
			Question question = q.getSource();
			qlist.add(question);
		}
		
		return qlist;
	}
	
	public List<QuestionList> searchQuestionListsByQuery(String query, int numResults) throws IOException {
		List<QuestionList> qlist = new ArrayList<QuestionList>();
		
		String response = HttpHelper.getFromUrl(HOST_URL + QUESTION_PATH + "_search/?size="+ numResults + "&q=" + URLEncoder.encode(query, "UTF-8"));
		
		Type esSearchResponseType = new TypeToken<ESSearchResponse<Question>>(){}.getType();
		ESSearchResponse<Question> esResponse = gson.fromJson(response, esSearchResponseType);
		for (ESGetResponse<Question> q : esResponse.getHits()) {
			Question question = q.getSource();
			QuestionList questionList = new QuestionList(question.getId(), question.getTitle(), question.getAuthorId(), question.getAnswerCountString(), question.getUpVoteCountString(), question.hasPicture());
			qlist.add(questionList);
		}
		
		return qlist;
	}
}
