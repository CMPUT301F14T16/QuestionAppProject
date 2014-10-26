package ca.ualberta.cmput301f14t16.easya;

import java.io.IOException;
import java.lang.reflect.Type;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

/**
 * @reference https://github.com/rayzhangcl/ESDemo/blob/master/ESDemo/src/ca/ualberta/cs/CMPUT301/chenlei/ESClient.java on Oct 23, 2014
 * @author Brett Commandeur
 *
 */
public class ESClient {
	
	//ElasticSeach Urls
	private static final String HOST_URL = "http://cmput301.softwareprocess.es:8080/testing/t16question/";
	
	// JSON Utilities
	private Gson gson = new Gson();

	public Question getQuestionById(String id) throws IOException {
		Question q = null;
		String questionUrl = HOST_URL + id;
			
		String content = HttpHelper.getFromUrl(questionUrl);
			
		// We have to tell GSON what type we expect
		Type esGetResponseType = new TypeToken<ESGetResponse<Question>>(){}.getType();
		
		// Now we expect to get a Index-Creation response
		ESGetResponse<Question> esGetResponse = gson.fromJson(content, esGetResponseType);
		
		// Extract question from elastic search response;
		q = esGetResponse.getSource();
		
		return q;
	}
	
	public void submitQuestion(Question q) throws IOException {
		String json = gson.toJson(q);
		
		// Post the object to the webservice
		HttpHelper.putToUrl(HOST_URL + q.getId(), json);
	}

	public void submitAnswer(Answer answer, String qid) throws IOException {
		Question q = this.getQuestionById(qid);
		q.addAnswer(answer);
		
		String json = gson.toJson(q.getAnswers());
		String updateStr = "{ \"doc\":{ \"answers\":" + json + "} }";
		
		HttpHelper.putToUrl(HOST_URL + qid +"/_update", updateStr);
	}

	public void submitQuestionReply(Reply reply, String qid) throws IOException {
		Question q = this.getQuestionById(qid);
		q.addReply(reply);
		
		String json = gson.toJson(q.getReplies());
		String updateStr = "{ \"doc\":{ \"replies\":" + json + "} }";
		
		HttpHelper.putToUrl(HOST_URL + qid + "/_update", updateStr);
		
	}

	public void submitAnswerReply(Reply reply, String qid, String aid) throws IOException {
		Question q = this.getQuestionById(qid);
		q.getAnswerById(aid).addReply(reply);
		
		String json = gson.toJson(q.getAnswers());
		String updateStr = "{ \"doc\":{ \"answers\":" + json + "} }";
		

		HttpHelper.putToUrl(HOST_URL + qid +"/_update", updateStr);
	}	
	
}
