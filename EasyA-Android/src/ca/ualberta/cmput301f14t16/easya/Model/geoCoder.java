package ca.ualberta.cmput301f14t16.easya.Model;

import java.util.List;
import java.util.Locale;

import android.content.Context;
import android.location.Geocoder;
import android.location.Address;

public class geoCoder {
	private Context context;
	private double latitude;
	private double longitude;
	private String myAddress;
	
	public geoCoder(Context context, double latitude, double longitude){
		this.context=context;
		this.latitude=latitude;
		this.longitude=longitude;
	}
	public void toAdress(){
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
	}
	public String getMyAddress(){
		return myAddress;
		
	}
	
}
