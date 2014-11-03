package ca.ualberta.cmput301f14t16.easya.Exceptions;

public class NoContentAvailableException extends Exception {
	private static final long serialVersionUID = 7627199008349298221L;

	public NoContentAvailableException(){
		
	}
	
	public NoContentAvailableException(String message){
		super(message);
	}
}
