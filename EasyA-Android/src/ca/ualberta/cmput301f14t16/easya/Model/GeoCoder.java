package ca.ualberta.cmput301f14t16.easya.Model;

import java.util.List;
import java.util.Locale;
import ca.ualberta.cmput301f14t16.easya.Model.Data.ContextProvider;
import android.location.Geocoder;
import android.location.Address;


public class GeoCoder {
	public static String toAdress(double latitude, double longitude){
		String myAddress;
		Geocoder geocoder = new Geocoder(ContextProvider.get(), Locale.getDefault());
		List<Address> addresses;
		//StringBuilder strReturnedAddress = new StringBuilder("");
		try{
			addresses = geocoder.getFromLocation(latitude, longitude, 1);
			if (addresses != null && addresses.size()>0){
				Address returnedAddress =addresses.get(0);
				/*
				for (int i=0;i<returnedAddress.getMaxAddressLineIndex();i++){
					strReturnedAddress.append(returnedAddress.getAddressLine(i)).append("\n");
				}
				myAddress=strReturnedAddress.toString();*/
				myAddress = formatAddress(returnedAddress);
			}
			else{
				myAddress="";
			}
		}
		catch (Exception e){
			myAddress="";
		}
		return myAddress;
	}
	
	private static String formatAddress(Address a){
		StringBuilder sb = new StringBuilder();
		String aux = "";
		aux = a.getLocality();
		sb.append(aux != null ? (aux + ", ") : (a.getSubAdminArea() != null) ? (a.getSubAdminArea() + ", ") : "");
		aux = a.getAdminArea();
		sb.append(aux != null ? (aux + "/") : "");
		sb.append(aux != null ? a.getCountryCode() : a.getCountryName());
		return sb.toString();
	}
	
	public static double[] toLatLong(String strAddress){
		Geocoder geocoder = new Geocoder(ContextProvider.get(), Locale.getDefault());
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
			return new double[]{0.0,0.0};
		}
		return returnLatLong;
	}


	public static double toFindDistance(double[] coordinate1, double[] coordinate2){
        double R = 6371000; // m
        double dLat = Math.toRadians(coordinate1[0] - coordinate2[0]);
        double dLon = Math.toRadians(coordinate1[1] - coordinate2[1]);
        double lat1 = Math.toRadians(coordinate1[0]);
        double lat2 = Math.toRadians(coordinate2[0]);

        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2) + Math.sin(dLon / 2)
                * Math.sin(dLon / 2) * Math.cos(lat1) * Math.cos(lat2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        double d = R * c;

        return d;
	}
	
	public static String coordinatesToString(double[] coord){
		try{
			return String.valueOf(coord[0]) + ";" + String.valueOf(coord[1]);
		}catch(Exception ex){
			return "";
		}
	}
	
	public static double[] coordinatesFromString(String coord){
		double[] dcoord = new double[2];
		dcoord[0] = Double.valueOf((coord.split(";")[0]));
		dcoord[1] = Double.valueOf((coord.split(";")[1]));
		return dcoord;
	}
}
