package ca.ualberta.cmput301f14t16.easya.Exceptions;

public class UnableToGetUserEmailException extends Exception {
	private static final long serialVersionUID = -6973188597432827002L;

	public UnableToGetUserEmailException(){
		
	}
	
	public UnableToGetUserEmailException(String message){
		super(message);
	}
}
