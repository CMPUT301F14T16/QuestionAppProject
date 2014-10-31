package ca.ualberta.cmput301f14t16.easya;

import java.util.ArrayList;
import java.util.List;


/**
 * 
 * @author Brett Commandeur (commande)
 * @author Cauani
 *
 */
public class Question extends Topic {
	
	private List<Answer> Answers;
	private String Title;
	
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
	public Question(String title, String body, String userId) {
		super(body, userId);
		this.Title = title;
		this.Answers = new ArrayList<Answer>();
	}
	
	/**
	 * Gets the title of the question.
	 * 
	 * @return	The title as a string.
	 */
	public String getTitle() {
		return this.Title;
	}
	
	/**
	 * Adds an answer to the question.
	 * 
	 * @param answer	The answer to be added to the list of this question's answers.
	 */
	public void addAnswer(Answer answer) {
		if (this.Answers == null)
			this.Answers = new ArrayList<Answer>();
		this.Answers.add(answer);
	}
	
	/**
	 * Gets the quantity of answers.
	 * 
	 * @return	Bounded quantity of answers. 
	 */
	public String getAnswerCount() {
		return this.Answers.size() <= 99 ? String.valueOf(this.Answers.size()) : "99+";
	}
	
	/**
	 * Gets the list of answers.
	 * 
	 * @return	The list of this question's answers.
	 */
	// TODO change name in UML.
	public List<Answer> getAnswers() {
		return this.Answers;
	}
	
	/**
	 * Gets a specific answer from within the question by id.
	 * 
	 * @return	On success, the answer specified by given index.
	 * 			On failure, null if answer not found.
	 */
	public Answer getAnswerById(String aid) {
		for (Answer answer : this.Answers) {
			if (answer.getId().equals(aid))
				return answer;
		}
		return null;
	}
}
