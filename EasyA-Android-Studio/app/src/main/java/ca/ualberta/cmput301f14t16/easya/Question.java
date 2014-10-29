package ca.ualberta.cmput301f14t16.easya;

import java.util.ArrayList;
import java.util.List;


/**
 * 
 * @author Brett Commandeur (commande)
 *
 */
public class Question extends Topic {
	
	List<Answer> answers;
	String title;
	
	/**
	 * No args constructor 
	 */
	public Question() {
		super();
	}
	
	/**
	 * Constructor for creating brand new, unsubmitted Question.
	 * 
	 * @param title		The title of the question.
	 * @param body		The main text content of the question; handled by Superclass Content.
	 * @param authorId	A unique identifier to the user who authored the question; handled by Superclass Content.
	 */
	public Question(String title, String body, String authorId) {
		super(body, authorId);
		this.title = title;
		this.answers = new ArrayList<Answer>(); 
	}
	
	/**
	 * Gets the title of the question.
	 * 
	 * @return	The title as a string.
	 */
	public String getTitle() {
		return title;
	}
	
	/**
	 * Adds an answer to the question.
	 * 
	 * @param answer	The answer to be added to the list of this question's answers.
	 */
	public void addAnswer(Answer answer) {
		answers.add(answer);
	}
	
	/**
	 * Gets the number of answers.
	 * 
	 * @return	Size of answer list. 
	 */
	public int getAnswerCount() {
		return answers.size();
	}
	
	/**
	 * Gets the list of answers.
	 * 
	 * @return	The list of this question's answers.
	 */
	// TODO change name in UML.
	public List<Answer> getAnswers() {
		return answers;
	}
	
	/**
	 * Gets a specific answer by index (where it is in the list of answers).
	 * 
	 * @return	The answer specified by given index.
	 */
	public Answer getAnswerByIndex(int index) {
		return answers.get(index);
	}

	public void addReplyToAnswer(Reply r, int index) {
		answers.get(index).addReply(r);	
	}
	
}
