package ca.ualberta.cmput301f14t16.easya.test;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.Date;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import android.test.ActivityInstrumentationTestCase2;
import android.util.Log;

import ca.ualberta.cmput301f14t16.easya.ESClient;
import ca.ualberta.cmput301f14t16.easya.ESGetResponse;
import ca.ualberta.cmput301f14t16.easya.ESPostResponse;
import ca.ualberta.cmput301f14t16.easya.HttpHelper;
import ca.ualberta.cmput301f14t16.easya.MainActivity;
import ca.ualberta.cmput301f14t16.easya.Question;

/**
 * 
 * @author Brett Commandeur
 *
 */
public class ElasticSearchClientTest extends ActivityInstrumentationTestCase2<MainActivity> {
	
	private static final String LOG_TAG = "ESCT";
	
//	private Gson gson = new Gson();
//	
//	private static final String HOST_URL = "http://cmput301.softwareprocess.es:8080/testing/";
	
	public ElasticSearchClientTest() {
		super(MainActivity.class);
	}

//	public void testDownloadUrl() {
//		String myUrl = HOST_URL + "t16question/m7Byz_5lSO2bJGuejOf8_A?pretty=true";
//		try {
//			String content = HttpHelper.downloadUrl(myUrl);
//			Log.d(LOG_TAG, content);
//			assertNotNull(content);
//			
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//	}
//	
//	public void testUploadUrl() {
//		String myUrl = HOST_URL + "t16question/";
//		String myData = "{\"authorEmail\":\"commande@ualberta.ca\",\"title\":\"Title\",\"body\":\"Body\",\"replies\":[{\"authorEmail\":\"commande@ualberta.ca\",\"body\":\"Body\"},{\"authorEmail\":\"commande@ualberta.ca\",\"body\":\"Body\"}],\"answers\":[{\"authorEmail\":\"commande@ualberta.ca\",\"body\":\"Body\",\"replies\":[{\"authorEmail\":\"commande@ualberta.ca\",\"body\":\"Body\"}]},{\"authorEmail\":\"commande@ualberta.ca\",\"body\":\"Body\",\"replies\":[{\"authorEmail\":\"commande@ualberta.ca\",\"body\":\"Body\"}]}]}";
//		try {
//			String content = HttpHelper.uploadUrl(myUrl, myData);
//			Log.d(LOG_TAG, content);
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//	}
	
//	public void testUploadFromObject() {
//		String myUrl = HOST_URL + "t16question/";
//		Question q = new Question("Title from Upload Test", "Body of Question", "test@ualberta.ca", new Date(), "123");
//		String json = gson.toJson(q);
//		Log.d(LOG_TAG, "json object: " + json);
//		
//		try {
//			String content = HttpHelper.uploadUrl(myUrl, json);
//			Log.d(LOG_TAG, "content: " + content);
//			
//			// We have to tell GSON what type we expect
//			Type esPostResponseType = new TypeToken<ESPostResponse>(){}.getType();
//			// Now we expect to get a Question response
//			ESPostResponse esPostResponse = gson.fromJson(content, esPostResponseType);
//			Log.d(LOG_TAG, "ID of Created Content:" + esPostResponse._id);
//			System.out.println(esPostResponse._id);
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//	}
	
	public void testSubmitAndGetQuestion() {
		
		// Create a question for testing
		Question q = new Question("Title Submission Test", 
				"Body of Question", "test@ualberta.ca", new Date(), null);
		
		// submit question to elastic search client
		ESClient esclient = new ESClient();
		String newId = esclient.submitQuestion(q);
		
		// Test the submission result
		Log.d(LOG_TAG, "Id of created content is: " + newId);
		assertNotNull(newId);
		
		// Get question back from elastic search
		Question r = esclient.getQuestionById(newId);
		assertNotNull(r);
		assertEquals(q.getTitle(), r.getTitle());
		assertEquals(q.getDate().toString(), r.getDate().toString());
		
	}
}
