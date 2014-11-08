package ca.ualberta.cmput301f14t16.easya.Controller;

import java.io.IOException;

import ca.ualberta.cmput301f14t16.easya.Model.MainModel;
import ca.ualberta.cmput301f14t16.easya.Model.User;
import ca.ualberta.cmput301f14t16.easya.Model.Data.ESClient;
import android.content.Context;

public class ChangeUsernameController {
	private String username;
	private User user;
	
	protected ChangeUsernameController(String username, User user){
		this.username = username;
		this.user = user;
	}
	
	public static ChangeUsernameController create(String username, User user){
		return new ChangeUsernameController(username, user);
	}
	
	public boolean submit(){
		try{
			ESClient es = new ESClient();
			if (es.setUsernameById(this.user.getId(), username)){
				this.user.setUserName(username);
				MainModel.getInstance().saveMainUser(this.user);
				return true;
			}else
				return false; 
		}catch(IOException ex){
			return false;			
		}catch(Exception ex){
			return false;
		}
	}
}
