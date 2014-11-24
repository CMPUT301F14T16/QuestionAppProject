package ca.ualberta.cmput301f14t16.easya.test;

import ca.ualberta.cmput301f14t16.easya.Model.GPSTracker;
import ca.ualberta.cmput301f14t16.easya.View.SubmitAnswerActivity;
import ca.ualberta.cmput301f14t16.easya.View.SubmitQuestionActivity;
import junit.framework.TestCase;

public class GeoLocationTest extends TestCase {
	private GPSTracker gps;
	//tests if gps initializes at 0,0.
	public void testGPS() {
		gps = new GPSTracker(gps);
    	double [] coordinate = new double[2];
		double latitude = gps.getLatitude();
		double longitude = gps.getLongitude();
		coordinate[0] = latitude;
		coordinate[1] = longitude;
		assertTrue(Math.floor(latitude) == 0 && Math.floor(longitude) == 0);
	}

}
