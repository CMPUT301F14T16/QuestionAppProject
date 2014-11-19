package ca.ualberta.cmput301f14t16.easya.Model;

import java.util.ArrayList;
import java.util.List;

/**
 * The Question class is an extension of the {@link Content} class, that
 * provides functions for associating and retrieving relevant {@link Answer} and
 * {@link Reply} objects.
 * 
 * @author Brett Commandeur (commande)
 * @author Cauani
 *
 */
public class Question extends Topic {

	/**
	 * A list of {@link Answer} objects associated with this Question.
	 */
	private List<Answer> answers;
	/**
	 * A title String for this Question
	 */
	private String title;

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
	 *            The title of the question. Setter for {@link Question#title}.
	 * @param body
	 *            {@link String} stored by the new instance of Answer.
	 * @param userId
	 *            Identifier used to keep track of the unique user that provided
	 *            the text of this Answer.
	 */
	public Question(String title, String body, String userId) {
		super(body, userId);
		this.title = title;
		this.answers = new ArrayList<Answer>();
	}
	
	public Question(String title, String body, byte[] bitmap, String userId) {
		super(body, bitmap, userId);
		this.title = title;
		this.answers = new ArrayList<Answer>();
	}

	/**
	 * @return {@link Question#title}
	 */
	public String getTitle() {
		return this.title;
	}

	/**
	 * Adds an {@link Answer} to the Question.
	 * 
	 * @param answer
	 *            The {@link Answer} to be added to the list of this Question's
	 *            {@link Answer} objects.
	 */
	public void addAnswer(Answer answer) {
		if (this.answers == null)
			this.answers = new ArrayList<Answer>();
		this.answers.add(answer);
	}

	/**
	 * @return The number of {@link Answer} objects stored by the instance of
	 *         Question (as an int).
	 */
	public int getAnswerCount() {
		return this.answers.size();
	}

	/**
	 * @return The number of {@link Answer} objects stored by the instance of
	 *         Question (as a {@link String}).
	 */
	public String getAnswerCountString() {
		try {
			return this.answers.size() <= 99 ? String.valueOf(this.answers
					.size()) : "99+";
		} catch (Exception ex) {
			return "0";
		}
	}

	/**
	 * @return The list of all {@link Answer} objects stored by this Question.
	 */
	// TODO change name in UML.
	public List<Answer> getAnswers() {
		if (this.answers == null)
			this.answers = new ArrayList<Answer>();
		return this.answers;
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
		try {
			for (Answer answer : this.answers) {
				if (answer.getId().equals(aid))
					return answer;
			}
			return null;
		} catch (Exception ex) {
			// TODO: deal with exception
			return null;
		}
	}

	/**
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (obj == this) {
			return true;
		}
		if (obj == null || obj.getClass() != this.getClass()) {
			return false;
		}

		Question q = (Question) obj;
		return (getId() != null && getId().equals(q.getId()))
				&& (title == q.title || (getTitle() != null && getTitle()
						.equals(q.getTitle())))
				&& (getBody() != null && getBody().equals(q.getBody()));
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
		result = prime * result
				+ ((getTitle() == null) ? 0 : getTitle().hashCode());
		return result;
	}
}
