package ca.ualberta.cmput301f14t16.easya.Exceptions;

public class LocationNotReachableException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3532903410424407358L;

	public LocationNotReachableException(){		
	}
	
	public LocationNotReachableException(String message){
		super(message);
	}
}
