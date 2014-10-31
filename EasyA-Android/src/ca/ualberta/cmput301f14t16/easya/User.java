package ca.ualberta.cmput301f14t16.easya;

import java.util.List;

public class User {
	private String username;
	private String id;
	
	/*
	 * Creates a random user
	 */
	public User(){		
		this.username = generateRandomUsername();
		this.id = generateId();
	}
	
	/*
	 * Load a certain user
	 */
	public User(String id, String username){
		this.username = username;
		this.id = id;
	}
	
	public void setUsername(String newName) {
		this.username = newName;
	}
	
	public String getUsername(){
		return this.username;
	}
	
	public String getId(){
		return this.id;
	}
	
	/*
	 * Generate a random username for the first time user
	 */
	private String generateRandomUsername(){
		//TODO: create a new random username
		return "Guest";
	}
	
	/*
	 * Generate a unique id for the user
	 */
	private String generateId(){
		//TODO: create a unique hash to be used as a userId
		return "userId";
	}
	
	public static User getUserById(String id){
		//TODO: Connect to ESCLient and retrieve an user by the id
		return new User();
	}
}
