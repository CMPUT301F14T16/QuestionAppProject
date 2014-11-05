package ca.ualberta.cmput301f14t16.easya.Exceptions;

public class EmailAlreadyInUseException extends Exception{
	public EmailAlreadyInUseException(){		
	}
	
	public EmailAlreadyInUseException(String message){
		super(message);
	}
}
