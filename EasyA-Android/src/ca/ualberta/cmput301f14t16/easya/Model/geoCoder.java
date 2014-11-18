package ca.ualberta.cmput301f14t16.easya.Model;

import java.util.List;
import java.util.Locale;

import android.content.Context;
import android.location.Geocoder;
import android.location.Address;


public class geoCoder {

	public static String toAdress(Context context, double latitude, double longitude){
		String myAddress;
		Geocoder geocoder = new Geocoder(context, Locale.getDefault());
		List<Address> addresses;
		StringBuilder strReturnedAddress = new StringBuilder("Address:\n");
		try{
			addresses = geocoder.getFromLocation(latitude, longitude, 1);
			if (addresses != null && addresses.size()>0){
				Address returnedAddress =addresses.get(0);
				for (int i=0;i<returnedAddress.getMaxAddressLineIndex();i++){
					strReturnedAddress.append(returnedAddress.getAddressLine(i)).append("\n");
				}
				myAddress=strReturnedAddress.toString();
			}
			else{
				myAddress="No Address returned!";
			}
		}
		catch (Exception e){
			myAddress="Exception found";
		}
		return myAddress;
	}
	public static double[] toLatLong(Context context, String strAddress){
		Geocoder geocoder = new Geocoder(context, Locale.getDefault());
		List<Address> addresses;
		double[] returnLatLong = new double[2];
		try{
			addresses = geocoder.getFromLocationName(strAddress, 1);
			if (addresses != null && addresses.size()>0){
				Address returnedAddress =addresses.get(0);
				returnLatLong[0] = returnedAddress.getLatitude();
				returnLatLong[1] = returnedAddress.getLongitude();
			}
		}
		catch (Exception e){
		}
		return returnLatLong;
	}

	
}
