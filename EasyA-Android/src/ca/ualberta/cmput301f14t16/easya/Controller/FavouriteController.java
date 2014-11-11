package ca.ualberta.cmput301f14t16.easya.Controller;

import java.io.IOException;
import ca.ualberta.cmput301f14t16.easya.Model.MainModel;
import ca.ualberta.cmput301f14t16.easya.Model.User;
import ca.ualberta.cmput301f14t16.easya.Model.Data.ESClient;

public class FavouriteController {
	private String qId;
	
	protected FavouriteController(String qId){
		this.qId = qId;
	}
	
	public static FavouriteController create(String qid){		
		return new FavouriteController(qid);
	}
	
	public boolean submit(){
		try{
			User u = MainModel.getInstance().getCurrentUser();
			boolean response = u.setFavourite(qId);
			ESClient es = new ESClient();
			//TODO: send the User to ESClient for it to update the favourites
			//es.setUserFavourite();
			return response;
		//}catch(IOException ex){
			//return false;			
		}catch(Exception ex){
			return false;
		}
	}
}
