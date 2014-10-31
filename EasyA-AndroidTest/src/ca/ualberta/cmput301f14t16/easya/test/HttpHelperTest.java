package ca.ualberta.cmput301f14t16.easya.test;

import java.io.IOException;
import java.lang.reflect.Type;

import junit.framework.TestCase;
import android.util.Log;
import ca.ualberta.cmput301f14t16.easya.Model.Data.ESPostResponse;
import ca.ualberta.cmput301f14t16.easya.Model.Data.HttpHelper;
import ca.ualberta.cmput301f14t16.easya.Model.Question;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

/**
 * 
 * @author Brett Commandeur
 *
 */
public class HttpHelperTest extends TestCase {
	
	private static final String HOST_URL = "http://cmput301.softwareprocess.es:8080/testing/t16question/";
	private static final String LOG_TAG = "HttpHelperTest";
	
	private Gson gson = new Gson();

	public void testDownloadUrl() {
		String myUrl = HOST_URL + "t16question/m7Byz_5lSO2bJGuejOf8_A?pretty=true";
		try {
			String content = HttpHelper.getFromUrl(myUrl);
			Log.d(LOG_TAG, content);
			assertNotNull(content);

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void testUploadUrl() {
		String myUrl = HOST_URL;
		String myData = "{\"authorEmail\":\"commande@ualberta.ca\",\"title\":\"Title\",\"body\":\"Body\",\"replies\":[{\"authorEmail\":\"commande@ualberta.ca\",\"body\":\"Body\"},{\"authorEmail\":\"commande@ualberta.ca\",\"body\":\"Body\"}],\"answers\":[{\"authorEmail\":\"commande@ualberta.ca\",\"body\":\"Body\",\"replies\":[{\"authorEmail\":\"commande@ualberta.ca\",\"body\":\"Body\"}]},{\"authorEmail\":\"commande@ualberta.ca\",\"body\":\"Body\",\"replies\":[{\"authorEmail\":\"commande@ualberta.ca\",\"body\":\"Body\"}]}]}";
		try {
			String content = HttpHelper.putToUrl(myUrl, myData);
			Log.d(LOG_TAG, content);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void testUploadFromObject() {
		String myUrl = HOST_URL;
		Question q = new Question("Title from Upload Test", "Body of Question", "test@ualberta.ca");
		String json = gson.toJson(q);
		Log.d(LOG_TAG, "json object: " + json);

		try {
			String content = HttpHelper.putToUrl(myUrl, json);
			Log.d(LOG_TAG, "content: " + content);

			// We have to tell GSON what type we expect
			Type esPostResponseType = new TypeToken<ESPostResponse>(){}.getType();
			// Now we expect to get a Question response
			ESPostResponse esPostResponse = gson.fromJson(content, esPostResponseType);
			Log.d(LOG_TAG, "ID of Created Content:" + esPostResponse._id);
			System.out.println(esPostResponse._id);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}

