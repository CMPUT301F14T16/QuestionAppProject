package ca.ualberta.cmput301f14t16.easya.Model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List; 
import java.util.Random;
import java.util.UUID;
import java.util.regex.Pattern;

import ca.ualberta.cmput301f14t16.easya.R;
import ca.ualberta.cmput301f14t16.easya.Exceptions.NoContentAvailableException;
import ca.ualberta.cmput301f14t16.easya.Exceptions.UnableToGetUserEmailException;
import ca.ualberta.cmput301f14t16.easya.Model.Data.PMClient;
import ca.ualberta.cmput301f14t16.easya.View.MainActivity;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.content.Context;
import android.text.style.EasyEditSpan;
import android.util.Patterns;

public class User {
	private String username;
	private String email;
	private String id;
	private Date createdOn;
	private List<Integer> createdContent;
	private List<Integer> favorites;
	
	public final static String USERKEY = "ca.ualberta.cmput301f14t16.easya.USERKEY";
	
	public User(){
		
	}
	
	public User(String email, String username) {
		this.email = email;
		this.username = username.equals("") ? generateNewUserName() : username;
		this.id = UUID.randomUUID().toString();
		this.createdOn = new Date();
		this.favorites = getFavorites();
		this.createdContent = getCreatedContent();
	}
	
	private List<Integer> getFavorites(){
		try{
			//TODO: look for this user in ESClient and return the favorites
			return new ArrayList<Integer>(); 
		}catch (Exception ex){
			return new ArrayList<Integer>();
		}
	}
	
	private List<Integer> getCreatedContent(){
		try{
			//TODO: look for this user in ESClient and return the list of created contents for him
			return new ArrayList<Integer>();
		}catch (Exception ex){
			return new ArrayList<Integer>();
		}
	}
	
	public void setUserName(String newName) {
		this.username = newName;
	}
	
	
	public String getEmail() {
		return email;
	}
	
	public String getUsername() {
		return username;
	}
	
	public String getId()  {
		return id;
	}
			
	private String generateNewUserName(){
		Random ran = new Random();
		int name = ran.nextInt(5) + 1;
		int number = ran.nextInt(8999) + 1000;
		String aux = "";
		switch (name){
		case 1:
			aux = "Guest";
			break;
		case 2:
			aux = "GreenHorn";
			break;
		case 3:
			aux = "Inquirer";
			break;
		case 4:
			aux = "Newcomer";
			break;
		case 5:
			aux = "Newbie";
			break;
		default:
			aux = "Guest";
		}
		return aux + number;
	}

	public static User getUserById(String userId) throws NoContentAvailableException {
		//TODO: get user from ESClient
		return new User();
	}
	
	public static final User getCurrentUser(Context ctx){
		try{
			PMClient pmclient = new PMClient();
			return pmclient.getUser(ctx);
		}catch(NoContentAvailableException ex){
			try{
				return getUserById(GeneralHelper.retrieveEmail(ctx));
			}catch(UnableToGetUserEmailException ex2){
				return null;
			}catch(NoContentAvailableException ex2){
				//TODO: create a new user if have internet, if not, throw nointernetexception
				return null;
			}
		}
	}
}

