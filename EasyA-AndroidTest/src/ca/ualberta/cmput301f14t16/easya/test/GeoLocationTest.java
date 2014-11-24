package ca.ualberta.cmput301f14t16.easya.test;

import ca.ualberta.cmput301f14t16.easya.Model.GPSTracker;
import ca.ualberta.cmput301f14t16.easya.View.SubmitAnswerActivity;
import ca.ualberta.cmput301f14t16.easya.View.SubmitQuestionActivity;
import junit.framework.TestCase;

public class GeoLocationTest extends TestCase {
	private GPSTracker gps;
	public void testGPS() {
		gps = new GPSTracker(gps);
    	double [] coordinate = new double[2];
		int latitude = (int)gps.getLatitude();
		int longitude = (int)gps.getLongitude();
		coordinate[0] = latitude;
		coordinate[1] = longitude;
		assertTrue(latitude ==  -113 && longitude == 53);
	}

}
