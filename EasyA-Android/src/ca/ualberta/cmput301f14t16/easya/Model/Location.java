package ca.ualberta.cmput301f14t16.easya.Model;

import java.util.Date;

import ca.ualberta.cmput301f14t16.easya.Exceptions.LocationNotReachableException;
import ca.ualberta.cmput301f14t16.easya.Model.Data.ContextProvider;
import ca.ualberta.cmput301f14t16.easya.Model.Data.PMClient;

public class Location {
	private final static int CHECK_PARAMETER = 300000; //in milliseconds
	private static Date lastCheck;
		
	public static String getLocationName(){
		double[] dcoords = getLocationCoordinates();
		if (dcoords == null){
			dcoords = new double[]{0.0,0.0};
		}
		return GeoCoder.toAdress(dcoords[0], dcoords[1]);
	}
	
	public static void forceCheckGPS(){
		lastCheck = null;
	}
	
	public static double[] getLocationCoordinates(){
		PMClient pm = new PMClient();
		switch(pm.getUserLocationPreference()){
		case OFF:
			return null;
		case SELECT:
			break;
		case GPS:
			if(lastCheck == null || (new Date().getTime() - lastCheck.getTime() >= CHECK_PARAMETER)){
				try{
					pm.saveUserLocation(findLocation());
					lastCheck = new Date();
				}catch(LocationNotReachableException ex){
					return new double[]{0.0,0.0};
				}
			}
			break;
		}	
		return pm.getUserLocation();
	}
	
	private static double[] findLocation() throws LocationNotReachableException{
    	GPSTracker gps = new GPSTracker(ContextProvider.get());
    	double[] coordinate = new double[2];
    	if (gps.canGetLocation()){
    		coordinate[0] = gps.getLatitude(); //latitude
    		coordinate[1] = gps.getLongitude(); //longitude
    		return coordinate;
		}
    	else{
    		throw new LocationNotReachableException();
		}
    }
}
