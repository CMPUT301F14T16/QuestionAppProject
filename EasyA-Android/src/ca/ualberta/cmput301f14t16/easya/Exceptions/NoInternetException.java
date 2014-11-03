package ca.ualberta.cmput301f14t16.easya.Exceptions;

public class NoInternetException extends Exception {
	private static final long serialVersionUID = 8077191679721930262L;

	public NoInternetException(){
		
	}
	
	public NoInternetException(String message){
		super(message);
	}
}
