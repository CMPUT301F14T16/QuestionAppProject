package ca.ualberta.cmput301f14t16.easya.test;

import java.io.IOException;

import android.test.ActivityInstrumentationTestCase2;
import android.util.Log;

import ca.ualberta.cmput301f14t16.easya.ESClient;
import ca.ualberta.cmput301f14t16.easya.MainActivity;

public class ESClientTest extends ActivityInstrumentationTestCase2<MainActivity> {
	
	private static final String LOG_TAG = "LOG";
	
	public ESClientTest() {
		super(MainActivity.class);
	}

	public void testDownloadUrl() {
		String myUrl = "http://cmput301.softwareprocess.es:8080/testing/t16question/m7Byz_5lSO2bJGuejOf8_A?pretty=true";
		try {
			String content = ESClient.downloadUrl(myUrl);
			Log.d(LOG_TAG, content);
			assertNotNull(content);
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
