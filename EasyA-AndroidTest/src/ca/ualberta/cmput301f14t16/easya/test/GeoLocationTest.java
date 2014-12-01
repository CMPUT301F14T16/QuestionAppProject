package ca.ualberta.cmput301f14t16.easya.test;

import android.util.Log;
import ca.ualberta.cmput301f14t16.easya.Model.GPSTracker;
import ca.ualberta.cmput301f14t16.easya.Model.GeoCoder;
import ca.ualberta.cmput301f14t16.easya.View.SubmitAnswerActivity;
import ca.ualberta.cmput301f14t16.easya.View.SubmitQuestionActivity;
import junit.framework.TestCase;

/**
 * 
 *  
 * @author Lingbo Tang,Klinton W Shmeid 
 *
 *
 */

// This test covers the use case of
// SetGeoLocation
public class GeoLocationTest extends TestCase {
	private GPSTracker gps;
	private final static String LOG_TAG = "GeoLocationTest";
	//tests if gps initializes and sets location.
	//In order for this test to pass the emulator needs
	//or device must have a working gps to properly test
	//the coordinates.
	public void testGPS() {
		gps = new GPSTracker(gps);
    	double [] coordinate = new double[2];
		double latitude = gps.getLatitude();
		double longitude = gps.getLongitude();
		coordinate[0] = latitude;
		coordinate[1] = longitude;
		assertTrue(Math.floor(latitude) == 0 && Math.floor(longitude) == 0);
		
		String address = GeoCoder.toAdress(53.5267620,-113.5271460);
		Log.d(LOG_TAG, address);
		String testAddress =  "Edmonton, Alberta/CA";
		Log.d(LOG_TAG, testAddress);
		assertEquals(address, testAddress);
		
	}

}
