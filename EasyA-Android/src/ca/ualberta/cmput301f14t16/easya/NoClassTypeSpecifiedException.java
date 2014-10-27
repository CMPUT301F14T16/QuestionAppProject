package ca.ualberta.cmput301f14t16.easya;

public class NoClassTypeSpecifiedException extends Exception{
	public NoClassTypeSpecifiedException(){		
	}
	
	public NoClassTypeSpecifiedException(String message){
		super(message);
	}
}