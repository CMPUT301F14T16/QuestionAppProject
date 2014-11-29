package ca.ualberta.cmput301f14t16.easya.Model;

import java.util.Date;

import ca.ualberta.cmput301f14t16.easya.Exceptions.LocationNotReachableException;
import ca.ualberta.cmput301f14t16.easya.Model.Data.ContextProvider;
import ca.ualberta.cmput301f14t16.easya.Model.Data.PMClient;

/**
 * Provides simplified access to the device's current coordinate location using
 * built-in GPS hardware.
 */
public class Location {
	/**
	 * The time interval required between location checks.
	 */
	private final static int CHECK_PARAMETER = 300000; // in milliseconds
	/**
	 * The time at which the location was last checked.
	 */
	private static Date lastCheck;

	/**
	 * @return The name of the device's current location.
	 */
	public static String getLocationName() {
		PMClient pm = new PMClient();
		getLocationCoordinates();
		return pm.getUserLocation();
	}

	/**
	 * @return True if location services are enabled.
	 */
	public static boolean isLocationEnabled() {
		PMClient pm = new PMClient();
		return (pm.getUserLocationPreference() != LocationPreferencesEnum.OFF);
	}

	/**
	 * Resets {@link Location#lastCheck}, allowing a location check regardless
	 * of the time elapsed since the last check.
	 */
	public static void forceCheckGPS() {
		lastCheck = null;
	}

	/**
	 * If the required amount of time has passed since the last location check,
	 * and GPS location preference is selected, this method will attempt to
	 * acquire and return the lon/lat coordinates of the device.
	 * 
	 * @return The device's lon/lat coordinates as an array of doubles.
	 */
	public static double[] getLocationCoordinates() {
		PMClient pm = new PMClient();
		switch (pm.getUserLocationPreference()) {
		case OFF:
			return null;
		case SELECT:
			break;
		case GPS:
			if (InternetCheck.haveInternet()
					&& (lastCheck == null || (new Date().getTime()
							- lastCheck.getTime() >= CHECK_PARAMETER))) {
				try {
					double[] aux = findLocation();
					pm.saveUserCoordinates(aux);
					pm.saveUserLocation(GeoCoder.toAdress(aux[0], aux[1]));
					lastCheck = new Date();
				} catch (LocationNotReachableException ex) {
					return new double[] { 0.0, 0.0 };
				}
			}
			break;
		}
		return pm.getUserCoordinates();
	}

	/**
	 * Uses {@link GPSTracker to acquire the lat/lon coordinates of the device.
	 * @return The device's lon/lat coordinates as an array of doubles.
	 * @throws LocationNotReachableException If the GPSTracker cannot get the devices location.
	 */
	private static double[] findLocation() throws LocationNotReachableException {
		GPSTracker gps = new GPSTracker(ContextProvider.get());
		double[] coordinate = new double[2];
		if (gps.canGetLocation()) {
			coordinate[0] = gps.getLatitude(); // latitude
			coordinate[1] = gps.getLongitude(); // longitude
			return coordinate;
		} else {
			throw new LocationNotReachableException();
		}
	}
}
