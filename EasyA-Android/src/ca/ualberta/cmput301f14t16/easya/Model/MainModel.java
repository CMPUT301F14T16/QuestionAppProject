package ca.ualberta.cmput301f14t16.easya.Model;

import java.util.ArrayList; // Used in list creation.
import java.util.List;

import android.content.Context;

import ca.ualberta.cmput301f14t16.easya.Exceptions.NoContentAvailableException;
import ca.ualberta.cmput301f14t16.easya.Exceptions.UnableToGetUserEmailException;
import ca.ualberta.cmput301f14t16.easya.Model.Data.Cache;
import ca.ualberta.cmput301f14t16.easya.Model.Data.PMClient;
import ca.ualberta.cmput301f14t16.easya.View.MainView;

/**
 * Provides a method of interacting with the data created and stored by the
 * application. Includes several methods to save and retrieve data from a
 * webservice, or local memory..
 * 
 * Design rationale: a unique user is associated with a unique and immutable ID.
 * However, this user and ID may also be associated with a non-unique and
 * mutable screen name.
 * 
 * @author Brett Commandeur (commande)
 */
public class MainModel<V extends MainView> {
	private ArrayList<V> views;
	private Context ctx;

	/**
	 * Design rationale: MVC format
	 * 
	 * @reference 
	 *            https://github.com/LingboTang/FillerCreepForAndroid/blob/master
	 *            /src/es/softwareprocess/fillercreep/FModel.java
	 * @author Abram Hindle
	 */
	public MainModel(Context ctx) {
		this.ctx = ctx;
		this.views = new ArrayList<V>();
	}
	
	public void addView(V view) {
        	if (!views.contains(view)) {
            	views.add(view);
        	}
    	}
	
	public void deleteView(V view) {
        	views.remove(view);
    	}
	
	public void notifyViews() {
        	for (V view : views) {
            	view.update(this);
        	}
    	}
	
	public Question getQuestionById(String id) throws NoContentAvailableException{
		return Cache.getQuestionById(ctx, id);
	}
	
	public User getUserById(String id) throws NoContentAvailableException{
		return Cache.getUserById(ctx, id);
	}
	
	public User getCurrentUser() throws NoContentAvailableException{
		try{
			PMClient pmclient = new PMClient();
			return pmclient.getUser(ctx);
		}catch(NoContentAvailableException ex){
			try{
				return getUserById(GeneralHelper.retrieveEmail(ctx));
			}catch(UnableToGetUserEmailException ex2){
				throw new NoContentAvailableException();
			}catch(Exception ex2){
				throw new NoContentAvailableException();
			}
		}
	}

	public List<QuestionList> getAllQuestions() throws NoContentAvailableException{
		return Cache.getAllQuestions(ctx);
	}
}
