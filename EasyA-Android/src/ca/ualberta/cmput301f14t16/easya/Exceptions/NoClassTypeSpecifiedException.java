package ca.ualberta.cmput301f14t16.easya.Exceptions;

public class NoClassTypeSpecifiedException extends Exception{
	private static final long serialVersionUID = -4883639233461006411L;

	public NoClassTypeSpecifiedException(){		
	}
	
	public NoClassTypeSpecifiedException(String message){
		super(message);
	}
}