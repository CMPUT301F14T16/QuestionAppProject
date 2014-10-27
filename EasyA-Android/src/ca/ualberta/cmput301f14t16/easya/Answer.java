package ca.ualberta.cmput301f14t16.easya;

/**
 * 
 * @author Brett Commandeur (commande)
 *
 */
public class Answer extends Topic {
	
	/**
	 * No args constructor used by deserializers in recreation of answer.
	 */
	public Answer() {
		super();
	}
	
	/**
	 * Constructor for creating brand new, unsubmitted answer.
	 * 
	 * @param body
	 * @param authorId
	 */
	public Answer(String body, String authorId) {
		super(body, authorId);
	}	
}
