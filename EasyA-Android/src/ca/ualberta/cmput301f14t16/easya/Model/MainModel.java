package ca.ualberta.cmput301f14t16.easya.Model;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList; // Used in list creation.
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import android.content.Context;

import ca.ualberta.cmput301f14t16.easya.Exceptions.NoContentAvailableException;
import ca.ualberta.cmput301f14t16.easya.Exceptions.NoInternetException;
import ca.ualberta.cmput301f14t16.easya.Exceptions.UnableToGetUserEmailException;
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
public class MainModel<V extends MainView> {
	private static MainModel m;
	private ArrayList<V> views;

	/**
	 * Design rationale: MVC format
	 * 
	 * @reference 
	 *            https://github.com/LingboTang/FillerCreepForAndroid/blob/master
	 *            /src/es/softwareprocess/fillercreep/FModel.java
	 * @author Abram Hindle
	 */
	protected MainModel() {
		this.views = new ArrayList<V>();
	}
	
	public static MainModel getInstance(){
		if (m == null)
			m = new MainModel();
		return m;
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
		return Cache.getInstance().getQuestionById(id);
	}
	
	public User getUserById(String id) throws NoContentAvailableException{
		return Cache.getInstance().getUserById(id);
	}
	
	public User getUserByEmail(String email) throws NoContentAvailableException, NoInternetException{
		return Cache.getInstance().getUserByEmail(email);
	}
	
	public User getCurrentUser() throws NoContentAvailableException{
		PMClient pmclient = new PMClient();
		return pmclient.getUser();
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
	
	public void Test(){
		Gson gson = new Gson();
		Question q = new Question("title", "body", "userid");
		String aux = gson.toJson(q);
		Question q2 = gson.fromJson(aux, Question.class);
		//
		List<Question> ql = new ArrayList<Question>();
		ql.add(q);
		String aux2 = gson.toJson(ql);
		Type listType = new TypeToken<List<Question>>(){}.getType();
		List<Question> ql2 = gson.fromJson(aux2, listType);
		q.addAnswer(new Answer("bodyanswer", "userid2"));
		ql2.add(q);
		ql2.add(q);
		ql2.add(q);
		ql2.add(q);
		ql2.add(q);
		ql2.add(q);
		ql2.add(q);
		ql2.add(q);
		ql2.add(q);
		ql2.add(q);
		ql2.add(q);
		ql2.add(q);
		ql2.add(q);
		ql2.add(q);
		ql2.add(q);
		ql2.add(q);
		String aux3 = gson.toJson(ql2);		
		List<Question> ql3 = gson.fromJson(aux3, listType);
		String i = "1";
		//
	}
}
