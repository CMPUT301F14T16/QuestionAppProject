package ca.ualberta.cmput301f14t16.easya.Model;

import java.util.ArrayList;
import java.util.List;

/**
 * The Question class is an extension of the {@link Content} class, that
 * provides functions for associating and retrieving relevant {@link Answer} and
 * {@link Reply} objects.
 * 
 * @author Brett Commandeur (commande), Cauani
 *
 */
public class Question extends Topic {

	/**
	 * A list of {@link Answer} objects associated with this Question.
	 */
	private List<Answer> Answers;
	/**
	 * A title String for this Question
	 */
	private String Title;

	/**
	 * Creates an empty Question object.
	 */
	public Question() {
		super();
	}

	/**
	 * Creates a Question containing the body text provided, and associated with
	 * the given author ID.
	 * 
	 * @param title
	 *            The title of the question. Setter for {@link Question#Title}.
	 * @param body
	 *            {@link String} stored by the new instance of Answer.
	 * @param authorId
	 *            Identifier used to keep track of the unique user that provided
	 *            the text of this Answer.
	 */
	public Question(String title, String body, String userId) {
		super(body, userId);
		this.Title = title;
		this.Answers = new ArrayList<Answer>();
	}

	/**
	 * @return {@link Question#Title}
	 */
	public String getTitle() {
		return this.Title;
	}

	/**
	 * Adds an {@link Answer} to the Question.
	 * 
	 * @param answer
	 *            The {@link Answer} to be added to the list of this Question's
	 *            {@link Answer} objects.
	 */
	public void addAnswer(Answer answer) {
		if (this.Answers == null)
			this.Answers = new ArrayList<Answer>();
		this.Answers.add(answer);
	}

	/**
	 * @return The number of {@link Answer} objects stored by the instance of
	 *         Question.
	 */
	public String getAnswerCount() {
		return this.Answers.size() <= 99 ? String.valueOf(this.Answers.size())
				: "99+";
	}

	/**
	 * @return The list of all {@link Answer} objects stored by this Question.
	 */
	// TODO change name in UML.
	public List<Answer> getAnswers() {
		return this.Answers;
	}

	/**
	 * Attempts to find an {@link Answer} with {@link Content#id} matching aid.
	 * 
	 * @param aid
	 *            the {@link Content#id} of the {@link Answer} requested.
	 * @return The answer specified by given index if successful, null if the
	 *         {@link Answer} is not found.
	 */
	public Answer getAnswerById(String aid) {
		for (Answer answer : this.Answers) {
			if (answer.getId().equals(aid))
				return answer;
		}
		return null;
	}
}
