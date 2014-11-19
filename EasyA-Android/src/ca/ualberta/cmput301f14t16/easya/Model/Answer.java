package ca.ualberta.cmput301f14t16.easya.Model;

/**
 * The Answer class extends the {@link Topic} class, providing no extra
 * functionality. This class exists primarily to create a clear Object Oriented
 * distinction between different uses of the {@link Topic} class.
 * 
 * @author Brett Commandeur (commande)
 *
 */
public class Answer extends Topic {
	
	/**
	 * Creates an empty Answer object
	 */
	//No args constructor used by deserializers in recreation of answer.
	public Answer() {
		super();
	}
	
	/**
	 * Creates an Answer containing the body text provided, and associated with
	 * the given author ID.
	 * 
	 * @see Topic#Topic(String, String)
	 * 
	 * @param body
	 *            {@link String} stored by the new instance of Answer.
	 * @param authorId
	 *            Identifier used to keep track of the unique user that provided
	 *            the text of this Answer.
	 */
	public Answer(String body, String authorId) {
		super(body, authorId);
	}	
	public Answer(String body, Byte[] bitmap, String authorId) {
		super(body, bitmap, authorId);
	}	
}
