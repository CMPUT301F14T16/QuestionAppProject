package ca.ualberta.cmput301f14t16.easya.Exceptions;

public class OversizePictureException extends Exception {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -47834823445546646L;

	public OversizePictureException(){}
	
	/**
	 * @param message
	 */
	public OversizePictureException(String message) {
		super(message);
	}
	
}
