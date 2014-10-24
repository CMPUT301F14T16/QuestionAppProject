package ca.ualberta.cmput301f14t16.easya;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

public class Question extends Topic {
	
	List<Answer> answers;
	int answerCount;
	String title;
	
	/**
	 * Creates a new Question
	 * @param title
	 * @param body
	 * @param authorId
	 * @param date
	 * @param id
	 */
	public Question(String title, String body, String authorId, Date date, String id) {
		super(body, authorId, date, id);
		this.title = title;
		this.answerCount = 0;
		this.answers = new ArrayList<Answer>(); 
	}
	
	/**
	 * Gets the list of answers
	 * @return answers
	 */
	public List<Answer> getAllAnswer() {
		return answers;
	}
	
	/**
	 * Gets specific answer
	 * @return answers
	 */
	public Answer getAnswerByID(int ID) {
		return answers.get(0);
	}

}
