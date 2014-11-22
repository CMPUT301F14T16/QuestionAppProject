package ca.ualberta.cmput301f14t16.easya.Model.Data;

import java.io.IOException;
import java.lang.reflect.Type;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import ca.ualberta.cmput301f14t16.easya.Model.Answer;
import ca.ualberta.cmput301f14t16.easya.Model.Question;
import ca.ualberta.cmput301f14t16.easya.Model.QuestionList;
import ca.ualberta.cmput301f14t16.easya.Model.Reply;
import ca.ualberta.cmput301f14t16.easya.Model.User;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

/**
 * Design rationale: caller/user of ESClient should handle exceptions.
 * 
 * see https://github.com/rayzhangcl/ESDemo/blob/master/ESDemo/src/ca/ualberta/cs/CMPUT301/chenlei/ESClient.java on Oct 23, 2014
 * @author Brett Commandeur
 */
public class ESClient {
	
	//Debug
	//private static final String LOG_TAG = "ESClient";
	
	//ElasticSeach Urls
	private static final String HOST_URL = "http://cmput301.softwareprocess.es:8080/testing/";
	private static final String QUESTION_TYPE = "team16question201411221"; // Edit Me to Change Question DB.
	private static final String USER_TYPE = "team16user";		   // Edit Me to Change User DB.
	private static final String QUESTION_PATH = QUESTION_TYPE + "/";
	private static final String USER_PATH = USER_TYPE + "/";
	
	
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
		String content = HttpHelper.getFromUrl(HOST_URL + QUESTION_PATH + id + "?fields=_source,_timestamp");
			
		// We have to tell GSON what type we expect
		Type esGetResponseType = new TypeToken<ESGetResponse<Question>>(){}.getType();
		
		// Now we expect to get a Index-Creation response
		ESGetResponse<Question> esGetResponse = gson.fromJson(content, esGetResponseType);
		
		// Extract question from elastic search response;
		Question question = esGetResponse.getSource();
		ESFields fields = esGetResponse.getFields();
		if (fields != null) {
			//long timestamp = fields.getTimestamp();
			//question.setTimestamp(timestamp);
		}
		
		
		
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
		
		String response = HttpHelper.getFromUrl(HOST_URL + QUESTION_PATH + "_search/?size="+ numResults + "&q=" + URLEncoder.encode(query, "UTF-8") + "&fields=_source,_timestamp");
		
		Type esSearchResponseType = new TypeToken<ESSearchResponse<Question>>(){}.getType();
		ESSearchResponse<Question> esResponse = gson.fromJson(response, esSearchResponseType);
		for (ESGetResponse<Question> q : esResponse.getHits()) {
			if (q == null)
				continue;
			
			Question question = q.getSource();
			ESFields fields = q.getFields();
			if (fields != null) {
				//question.setTimestamp(fields.getTimestamp());
			}
			qlist.add(question);
		}
		
		return qlist;
	}
	
	// Same as method above, but returns a preview of a question instead of the full question object.
	// Above method will most likely not be used...
	// I'm using it :D
	public List<QuestionList> searchQuestionListsByQuery(String query, int numResults) throws IOException {
		List<QuestionList> qlist = new ArrayList<QuestionList>();
		
		String response = HttpHelper.getFromUrl(HOST_URL + QUESTION_PATH + "_search/?size="+ numResults + "&q=" + URLEncoder.encode(query, "UTF-8") + "&fields=_source,_timestamp");
		
		Type esSearchResponseType = new TypeToken<ESSearchResponse<Question>>(){}.getType();
		ESSearchResponse<Question> esResponse = gson.fromJson(response, esSearchResponseType);
		for (ESGetResponse<Question> q : esResponse.getHits()) {
			if (q == null)
				continue;
			
			Question question = q.getSource();
			
			ESFields fields = q.getFields();
			if (q.getFields() != null) {
				//question.setTimestamp(fields.getTimestamp());
			}
			
			QuestionList questionList = new QuestionList(question.getId(), question.getTitle(), 
					question.getAuthorName(), question.getAuthorId(), question.getAnswerCountString(), 
					question.getUpVoteCountString(), question.hasPicture(), 
					question.getDate());
			qlist.add(questionList);
		}
		
		return qlist;
	}
	
	public boolean submitUser(User user) throws IOException {
		String json = gson.toJson(user);
		
		// Post the object to the webservice
		HttpHelper.putToUrl(HOST_URL + USER_PATH + user.getId(), json);
		
		//TODO: change that based on ESS response
		return true;
	}
	
	public User getUserById(String id) throws IOException {
		String content = HttpHelper.getFromUrl(HOST_URL + USER_PATH + id);
		Type esGetResponseType = new TypeToken<ESGetResponse<User>>(){}.getType();
		ESGetResponse<User> esGetResponse = gson.fromJson(content, esGetResponseType);
		User user = esGetResponse.getSource();
		
		return user;
	}
	
	public boolean setUsernameById(String uid, String newUsername) throws IOException {
		String updateStr = "{ \"doc\":{ \"username\":\"" + newUsername + "\"} }";
		HttpHelper.putToUrl(HOST_URL + USER_PATH + uid + "/_update", updateStr);
		return true;
	}
	
	public String getUserIdByEmail(String email) throws IOException {
		
		List<User> returnedUsers = searchUsersByQuery("email:"+email, 100);
		
		if (returnedUsers.size() > 0) {
			for (User user : returnedUsers) {
				String email1 = user.getEmail().toLowerCase(Locale.CANADA);
				String email2 = email.toLowerCase(Locale.CANADA);
				if (email1.equals(email2)) {
					return user.getId();
				}
			}
		}
		
		return null;
	}
	
	public List<User> searchUsersByQuery(String query, int numResults) throws IOException {
		List<User> ulist = new ArrayList<User>();
		
		String response = HttpHelper.getFromUrl(HOST_URL + USER_PATH + "_search/?size="+ numResults + "&q=" + URLEncoder.encode(query, "UTF-8"));
		
		Type esSearchResponseType = new TypeToken<ESSearchResponse<User>>(){}.getType();
		ESSearchResponse<User> esResponse = gson.fromJson(response, esSearchResponseType);
		for (ESGetResponse<User> u : esResponse.getHits()) {
			if (u == null)
				continue;
			
			User user = u.getSource();			
			ulist.add(user);
		}
		
		return ulist;
	}
	
	public boolean setQuestionUpvote(String userId, String qid) throws IOException {
		Question q = this.getQuestionById(qid);
		q.setUpvote(userId);
		
		String json = gson.toJson(q.getUpvotes());
		String updateStr = "{ \"doc\":{ \"upVotes\":" + json + "} }";
		
		HttpHelper.putToUrl(HOST_URL + QUESTION_PATH + qid + "/_update", updateStr);
		
		//TODO: change that based on ESS response
		return true;
	} 
	
	public boolean setAnswerUpvote(String userId, String qid, String aid) throws IOException {
		Question q = this.getQuestionById(qid);
		q.getAnswerById(aid).setUpvote(userId);
		
		String json = gson.toJson(q.getAnswers());
		String updateStr = "{ \"doc\":{ \"answers\":" + json + "} }";
		
		HttpHelper.putToUrl(HOST_URL + QUESTION_PATH + qid +"/_update", updateStr);
		
		//TODO: change that based on ESS response
		return true;
	}

	public List<Question> getFavouriteQuestionsByUser(User u) throws IOException {
		List<String> favsList = u.getFavourites();
		if (favsList == null || favsList.size() <= 0)
			return null;
		StringBuilder sb = new StringBuilder().append("id:");
		for (String s : favsList){
			sb.append(" " + s);
		}
		return searchQuestionsByQuery(sb.toString(), 999);
	}
	
	public List<QuestionList> getQuestionListsByIds(List<String> ids) throws IOException {
		String json = gson.toJson(ids);
		String queryString = "{\"query\": {\"ids\": {\"type\": \"" +  QUESTION_TYPE + "\", \"values\": " + json +"}}}";
		String response = HttpHelper.getFromUrlWithData(HOST_URL + QUESTION_PATH + "_search", queryString);
		
		return getQuestionListsFromResponse(response);
		
	}
	
	private List<QuestionList> getQuestionListsFromResponse(String response) {
		List<QuestionList> qlist = new ArrayList<QuestionList>();
		Type esSearchResponseType = new TypeToken<ESSearchResponse<Question>>(){}.getType();
		ESSearchResponse<Question> esResponse = gson.fromJson(response, esSearchResponseType);
		for (ESGetResponse<Question> q : esResponse.getHits()) {
			if (q == null)
				continue;
			
			Question question = q.getSource();			
			QuestionList questionList = new QuestionList(question.getId(), question.getTitle(), 
					question.getAuthorName(), question.getAuthorId(), question.getAnswerCountString(), 
					question.getUpVoteCountString(), question.hasPicture(), 
					question.getDate());
			qlist.add(questionList);
		}
		return qlist;
	}
	
	public boolean updateUserFavourites(User user) throws IOException {
		
		String json = gson.toJson(user.getFavourites());
		String updateStr = "{ \"doc\":{ \"favourites\":" + json + "} }";
		
		HttpHelper.putToUrl(HOST_URL + USER_PATH + user.getId() +"/_update", updateStr);
		
		return true;
	}
}
