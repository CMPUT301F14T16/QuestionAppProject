package ca.ualberta.cmput301f14t16.easya;

import java.util.ArrayList;
import java.util.List;

/**
 * The main model used to interact with the data of the application.
 * Manages saving and retrieving data to/from the webservice and device. 
 * Design rationale: user id does not change, but their displayed name can.
 * 
 * @author Brett Commandeur (commande)
 */
public class MainModel {

	/**
	 * Adds submitted question to the webservice or device.
	 * @param title
	 * @param body
	 */
	public void addQuestion(String title, String body) {
		//...
	}
	
	public void addAnswer(int questionID, String title, String body) {
		//...
	}
	
	public List<Question> getAllQuestions(){
		// @TODO Update pluralized name on UML
		//...
		return null;
	}
	
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
	
	public List<Question> sortByDate() {
		//@TODO Extract the sort methods, should be part of a controller. 
		//@TODO Needs an argument, how does it know what to sort?
		//...
		return null;
	}
	
	public List<Question> sortByUpvote(){
		//@TODO Update name typo on UML.
		//...
		return null;
	}
	
	public List<Question> sortByHasQuestion(){
		//...
		return null;
	}
	
	public void changeUserName(String newName) {
		//...
	}
	
	public boolean pushPending(List<Pending> pendings) {
		//@TODO design of this method needs reconsideration. Why have pendingQuestion attribute?
		//...
		return false;
	}
	
	public void setUserName(String name, String emailID) {
		//@TODO Why do we have this and changeUserName() ?
	}
}
