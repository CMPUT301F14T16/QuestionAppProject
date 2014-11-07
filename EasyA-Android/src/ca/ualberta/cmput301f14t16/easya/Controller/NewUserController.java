package ca.ualberta.cmput301f14t16.easya.Controller;

import java.io.IOException;

import ca.ualberta.cmput301f14t16.easya.Model.MainModel;
import ca.ualberta.cmput301f14t16.easya.Model.User;
import ca.ualberta.cmput301f14t16.easya.Model.Data.ESClient;
import android.content.Context;

public class NewUserController {
	private Context ctx;
	private User u;
	
	protected NewUserController(Context ctx, User u){
		this.ctx = ctx;
		this.u = u;
	}
	
	public static NewUserController create(Context ctx, String email, String username){
		User u = new User(email, username);
		return new NewUserController(ctx, u);
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
