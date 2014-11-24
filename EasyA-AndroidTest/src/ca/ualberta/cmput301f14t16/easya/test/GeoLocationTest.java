package ca.ualberta.cmput301f14t16.easya.test;

import android.util.Log;
import ca.ualberta.cmput301f14t16.easya.Model.GPSTracker;
import ca.ualberta.cmput301f14t16.easya.Model.geoCoder;
import ca.ualberta.cmput301f14t16.easya.View.SubmitAnswerActivity;
import ca.ualberta.cmput301f14t16.easya.View.SubmitQuestionActivity;
import junit.framework.TestCase;

public class GeoLocationTest extends TestCase {
	private GPSTracker gps;
	private final static String LOG_TAG = "GeoLocationTest";
	//tests if gps initializes and sets location.
	public void testGPS() {
		gps = new GPSTracker(gps);
    	double [] coordinate = new double[2];
		double latitude = gps.getLatitude();
		double longitude = gps.getLongitude();
		coordinate[0] = latitude;
		coordinate[1] = longitude;
		assertTrue(Math.floor(latitude) == 0 && Math.floor(longitude) == 0);
		
		String address = geoCoder.toAdress(gps, 53.5267620,-113.5271460);
		Log.d(LOG_TAG, address);
		String testAddress =  "University of Alberta\n8900 114 St NW\nEdmonton, AB T6G 2S4\n";
		Log.d(LOG_TAG, testAddress);
		assertEquals(address.length(), testAddress.length());
		
	}

}
