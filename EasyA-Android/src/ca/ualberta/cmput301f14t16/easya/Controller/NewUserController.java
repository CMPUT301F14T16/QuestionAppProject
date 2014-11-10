package ca.ualberta.cmput301f14t16.easya.Controller;

import java.io.IOException;

import ca.ualberta.cmput301f14t16.easya.Model.MainModel;
import ca.ualberta.cmput301f14t16.easya.Model.User;
import ca.ualberta.cmput301f14t16.easya.Model.Data.ESClient;

public class NewUserController {
	private User u;
	
	protected NewUserController(User u){
		this.u = u;
	}
	
	public static NewUserController create(String email, String username){
		User u = new User(email, username);
		return new NewUserController(u);
	}
	
	public boolean submit(){
		try{
			ESClient es = new ESClient();
			if (es.submitUser(this.u)){
				MainModel.getInstance().saveMainUser(this.u);
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
