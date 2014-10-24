package ca.ualberta.cmput301f14t16.easya;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import android.util.Log;

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

	public String submitQuestion(Question q) {
		String json = gson.toJson(q);
		String newId = null;
		try {
			// Post the object to the webservice
			String content = HttpHelper.uploadUrl(HOST_URL, json);
			// We have to tell GSON what type we expect
			Type esPostResponseType = new TypeToken<ESPostResponse>(){}.getType();
			// Now we expect to get a Index-Creation response
			ESPostResponse esPostResponse = gson.fromJson(content, esPostResponseType);
			// Get the id, as it is on the server, 
			// of the created question from the response.
			newId = esPostResponse._id;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return newId;
	}

	public Question getQuestionById(String id) {
		Question q = null;
		String questionUrl = HOST_URL + id;
		try {
			String content = HttpHelper.downloadUrl(questionUrl);
			
			// We have to tell GSON what type we expect
			Type esGetResponseType = new TypeToken<ESGetResponse<Question>>(){}.getType();
			
			// Now we expect to get a Index-Creation response
			ESGetResponse<Question> esGetResponse = gson.fromJson(content, esGetResponseType);
			
			// Extract question from elastic search response;
			q = esGetResponse.getSource();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return q;
	}
	
}
