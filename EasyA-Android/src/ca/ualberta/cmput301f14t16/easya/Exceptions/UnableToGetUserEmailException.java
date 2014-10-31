package ca.ualberta.cmput301f14t16.easya.Exceptions;

public class UnableToGetUserEmailException extends Exception {
	public UnableToGetUserEmailException(){
		
	}
	
	public UnableToGetUserEmailException(String message){
		super(message);
	}
}
