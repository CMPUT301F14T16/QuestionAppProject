package ca.ualberta.cmput301f14t16.easya.Exceptions;

public class MissingContentException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4923870598745550930L;

	public MissingContentException(){}
	
	/**
	 * @param message
	 */
	public MissingContentException(String message) {
		super(message);
	}

}
