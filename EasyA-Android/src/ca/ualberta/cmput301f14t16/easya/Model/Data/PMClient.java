package ca.ualberta.cmput301f14t16.easya.Model.Data;

import android.content.Context;

import com.google.gson.Gson;

import ca.ualberta.cmput301f14t16.easya.Exceptions.NoContentAvailableException;
import ca.ualberta.cmput301f14t16.easya.Model.User;

/**
 * Phone Memory Client (really silly name)
 * Takes care of accessing the phone memory for saving and retrieving.
 * Will be used by Queue and Cache
 * @author Cauani
 *
 */
public class PMClient {
	public PMClient(){}
	
	public void saveUser(Context ctx, User user){
		Gson gson = new Gson();
		PMDataParser.saveUserPreference(ctx, User.USERKEY, gson.toJson(user, User.class));
	}
	
	public User getUser(Context ctx) throws NoContentAvailableException{
		Gson gson = new Gson();
		String aux = PMDataParser.recoverUserPreference(ctx, User.USERKEY);
		if (aux.equals("")){
			throw new NoContentAvailableException();
		}
		return gson.fromJson(aux, User.class);
	}
}
