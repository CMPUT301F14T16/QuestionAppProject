package ca.ualberta.cmput301f14t16.easya.Exceptions;

public class EmailAlreadyInUseException extends Exception{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1466599076768182124L;

	public EmailAlreadyInUseException(){		
	}
	
	public EmailAlreadyInUseException(String message){
		super(message);
	}
}
