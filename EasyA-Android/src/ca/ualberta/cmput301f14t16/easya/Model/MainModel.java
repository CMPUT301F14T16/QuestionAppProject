package ca.ualberta.cmput301f14t16.easya.Model;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import ca.ualberta.cmput301f14t16.easya.Exceptions.NoContentAvailableException;
import ca.ualberta.cmput301f14t16.easya.Exceptions.NoInternetException;
import ca.ualberta.cmput301f14t16.easya.Model.Data.Cache;
import ca.ualberta.cmput301f14t16.easya.Model.Data.ESClient;
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
 * @author Cauani
 * @author Brett Commandeur (commande)
 */
public class MainModel {
	private static MainModel m;
	private static MainView<?> currentView;
	
	private List<MainView<?>> views;

	/**
	 * Design rationale: MVC format
	 * 
	 * @reference 
	 *            https://github.com/LingboTang/FillerCreepForAndroid/blob/master
	 *            /src/es/softwareprocess/fillercreep/FModel.java
	 * @author Abram Hindle
	 */
	protected MainModel() {
		this.views = new ArrayList<MainView<?>>();
	}
	
	public static MainModel getInstance(){
		if (m == null)
			m = new MainModel();
		return m;
	}
	
	public void addView(MainView<?> view) {
    	if (!views.contains(view)) {
        	views.add(view);
    	}
	}
	
	public void deleteView(MainView<?> view) {
    	views.remove(view);
	}
	
	public void notifyViews() {
    	for (MainView<?> view : views) {
        	view.update();
    	}
	}
	
	public List<MainView<?>> getAllViews(){
		return this.views;
	}
	
	public Question getQuestionById(String id) throws NoContentAvailableException{
		return Cache.getInstance().getQuestionById(id);
	}
	
	public User getUserById(String id) throws NoContentAvailableException{
		return Cache.getInstance().getUserById(id);
	}
	
	public User getUserByEmail(String email) throws NoContentAvailableException, NoInternetException{
		return Cache.getInstance().getUserByEmail(email);
	}
	
	public User getCurrentUser(){
		try{
			PMClient pmclient = new PMClient();
			return pmclient.getUser();
		}catch(NoContentAvailableException ex){
			return null;
		}
		
	}
	
	public void saveMainUser(User u){
		PMClient pm = new PMClient();
		pm.saveUser(u);
	}
	
	public boolean updateUsername(User u){
		ESClient es = new ESClient();
		try{
			return es.setUsernameById(u.getId(), u.getUsername());
		}catch(IOException ex){
			return false;
		}		
	}

	public List<QuestionList> getAllQuestions() throws NoContentAvailableException{
		return Cache.getInstance().getAllQuestions();
	}
	
	public void wipeCache(){
		Cache.getInstance().wipeCache();
	}
}
