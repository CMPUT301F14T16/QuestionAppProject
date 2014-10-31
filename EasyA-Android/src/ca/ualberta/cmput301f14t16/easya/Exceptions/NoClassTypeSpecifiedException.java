package ca.ualberta.cmput301f14t16.easya.Exceptions;

public class NoClassTypeSpecifiedException extends Exception{
	public NoClassTypeSpecifiedException(){		
	}
	
	public NoClassTypeSpecifiedException(String message){
		super(message);
	}
}