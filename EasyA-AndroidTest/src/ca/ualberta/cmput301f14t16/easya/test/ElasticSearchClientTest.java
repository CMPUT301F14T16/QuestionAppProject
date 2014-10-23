package ca.ualberta.cmput301f14t16.easya.test;

import java.io.IOException;

import android.test.ActivityInstrumentationTestCase2;
import android.util.Log;

import ca.ualberta.cmput301f14t16.easya.HttpHelper;
import ca.ualberta.cmput301f14t16.easya.MainActivity;

public class ElasticSearchClientTest extends ActivityInstrumentationTestCase2<MainActivity> {
	
	private static final String LOG_TAG = "ESCT";
	
	private static final String HOST_URL = "http://cmput301.softwareprocess.es:8080/testing/";
	
	public ElasticSearchClientTest() {
		super(MainActivity.class);
	}

	public void testDownloadUrl() {
		String myUrl = HOST_URL + "t16question/m7Byz_5lSO2bJGuejOf8_A?pretty=true";
		try {
			String content = HttpHelper.downloadUrl(myUrl);
			Log.d(LOG_TAG, content);
			assertNotNull(content);
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void testUploadUrl() {
		String myUrl = HOST_URL + "t16question/";
		String myData = "{\"authorEmail\":\"commande@ualberta.ca\",\"title\":\"Title\",\"body\":\"Body\",\"replies\":[{\"authorEmail\":\"commande@ualberta.ca\",\"body\":\"Body\"},{\"authorEmail\":\"commande@ualberta.ca\",\"body\":\"Body\"}],\"answers\":[{\"authorEmail\":\"commande@ualberta.ca\",\"body\":\"Body\",\"replies\":[{\"authorEmail\":\"commande@ualberta.ca\",\"body\":\"Body\"}]},{\"authorEmail\":\"commande@ualberta.ca\",\"body\":\"Body\",\"replies\":[{\"authorEmail\":\"commande@ualberta.ca\",\"body\":\"Body\"}]}]}";
		try {
			String content = HttpHelper.uploadUrl(myUrl, myData);
			Log.d(LOG_TAG, content);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void testUploadFromObject() {

	}
}
