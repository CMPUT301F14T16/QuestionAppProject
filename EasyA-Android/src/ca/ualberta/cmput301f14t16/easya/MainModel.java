package ca.ualberta.cmput301f14t16.easya;

import java.util.ArrayList; // Used in list creation.
import java.util.List;

/**
 * The main model used to interact with the data of the application.
 * Manages saving and retrieving data to/from the webservice and device. 
 * Design rationale: user id does not change, but their displayed name can.
 * 
 * @author Brett Commandeur (commande)
 */
public class MainModel {
	
	// TODO MVC will keep these up to date.
	private List<Question> submittedQuestions;
	private List<Question> myQuestions;
	private List<Question> savedQuestions;
	private List<Pending> pendingQuestion;
	private User user;

	/**
	 * Adds submitted question to the webservice or device (pending).
	 * @param title
	 * @param body
	 */
	public void addQuestion(String title, String body) {
		//...
	}
	
	/**
	 * Add submitted answer to the webservice or device (pending).
	 * @param questionID
	 * @param title
	 * @param body
	 */
	public void addAnswer(int questionID, String title, String body) {
		//...
	}
	
	/**
	 * Returns a list of all questions submitted to the system by all users.
	 * @return
	 */
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
	public List<Question> getAllAnswers(int questionID){
		// @TODO Update pluralized name on UML
		//...
		return null;
	}
	
	/**
	 * Returns a list of all questions submitted by the user.
	 * @return 
	 */
	public List<Question> getAllSubmittedQuestions() {
		// @TODO change method name to getQuestionsSubmittedByUser
		//...
		return null;
	}
	
	/**
	 * Returns a list of all questions the user has chosen to save to their device.
	 * @return
	 */
	public List<Answer> getAllSavedQuestions() {
		//...
		return null;
	}
	
	/**
	 * ...
	 * @return
	 */
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
	public List<Question> sortByUpvote(){
		//@TODO Update name typo on UML.
		//...
		return null;
	}
	
	/**
	 * ...
	 * @return
	 */
	public List<Question> sortByHasQuestion(){
		//...
		return null;
	}
	
	/**
	 * ...
	 * @param newName
	 */
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
	public void setUserName(String name, String emailID) {
		//@TODO Why do we have this and changeUserName() ?
		//...
	}
	
	/**
	 * Generates a content ID for content submission.
	 * @return
	 */
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
	public Question getQuestionByID(int ID) {
		//...
		return null;
	}
	
	/**
	 * Returns the username of the active user on the device.
	 * @return
	 */
	public String getUserName() {
		//...
		return null;
	}
	
	/**
	 * Returns whether there is content waiting to be pushed to the webservice.
	 * @return
	 */
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
	public Answer getAnswerByID(int ID) {
		// TODO feels like we're not taking advantage of abstractions like Content and Topic.
		//...
		return null;
	}
	
	//@TODO If mainmodel adds questions and answers, should it also add replies?
}
