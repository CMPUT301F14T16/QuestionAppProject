package ca.ualberta.cmput301f14t16.easya.Model;

import java.util.ArrayList; // Used in list creation.

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
				return null;
			}
		}
	}	
		
	/**
	 * Adds submitted question to the webservice or device (pending).
	 * @param title
	 * @param body
	 */
	/*
	public void addQuestion(String title, String body) {
		//...
	}
	
	/**
	 * Add submitted answer to the webservice or device (pending).
	 * @param questionID
	 * @param title
	 * @param body
	 */
	/*
	public void addAnswer(int questionID, String title, String body) {
		//...
	}
	
	/**
	 * Returns a list of all questions submitted to the system by all users.
	 * @return
	 */
	/*
	public List<Question> getAllQuestions(){
		// @TODO Update pluralized name on UML
		//...
		return null;
	}
	
	/**
	 * Returns a list of all answers submitted to a particular question.
	 * @param questionID
	 * @return
	 */
	/*
	public List<Question> getAllAnswers(int questionID){
		// @TODO Update pluralized name on UML
		//...
		return null;
	}
	
	/**
	 * Returns a list of all questions submitted by the user.
	 * @return 
	 */
	/*
	public List<Question> getAllSubmittedQuestions() {
		// @TODO change method name to getQuestionsSubmittedByUser
		//...
		return null;
	}
	
	/**
	 * Returns a list of all questions the user has chosen to save to their device.
	 * @return
	 */
	/*
	public List<Answer> getAllSavedQuestions() {
		//...
		return null;
	}
	
	/**
	 * ...
	 * @return
	 */
	/*
	public List<Question> sortByDate() {
		//@TODO Extract the sort methods, should be part of a controller. 
		//@TODO Needs an argument, how does it know what to sort?
		//...
		return null;
	}
	
	/**
	 * ...
	 * @return
	 */
	/*
	public List<Question> sortByUpvote(){
		//@TODO Update name typo on UML.
		//...
		return null;
	}
	
	/**
	 * ...
	 * @return
	 */
	/*
	public List<Question> sortByHasQuestion(){
		//...
		return null;
	}
	
	/**
	 * ...
	 * @param newName
	 */
	/*
	public void changeUserName(String newName) {
		//@TODO How is this different from setUserName?
		//...
	}
	
	/** 
	 * Allows external sources to determine when content should be pushed to the webservice.
	 * The external source provides the list of content to be pushed.
	 * @param pendings
	 * @return
	 */
	/*
	public boolean pushPending(List<Pending> pendings) {
		//@TODO design of this method needs reconsideration. Why have pendingQuestion attribute?
		//...
		return false;
	}
	
	/**
	 * Sets the username of the active user both on the device and on the webservice.
	 * @param name
	 * @param emailID
	 */
	/*
	public void setUserName(String name, String emailID) {
		//@TODO Why do we have this and changeUserName() ?
		//...
	}
	
	/**
	 * Generates a content ID for content submission.
	 * @return
	 */
	/*
	public int generateID() {
		// TODO Main model should not be responsible for this.
		//...
		return 0;
	}
	
	/**
	 * Returns a particular question by its ID.
	 * @param ID
	 * @return
	 */
	/*
	public Question getQuestionByID(int ID) {
		//...
		return null;
	}
	
	/**
	 * Returns the username of the active user on the device.
	 * @return
	 */
	/*
	public String getUserName() {
		//...
		return null;
	}
	
	/**
	 * Returns whether there is content waiting to be pushed to the webservice.
	 * @return
	 */
	/*
	public boolean hasPending() {
		// TODO what is this for?
		//...
		return false;
	}
	
	/**
	 * Returns a particular answer by its ID.
	 * @param ID
	 * @return
	 */
	/*
	public Answer getAnswerByID(int ID) {
		// TODO feels like we're not taking advantage of abstractions like Content and Topic.
		//...
		return null;
	}
	
	//@TODO If mainmodel adds questions and answers, should it also add replies?
	 * */
}
