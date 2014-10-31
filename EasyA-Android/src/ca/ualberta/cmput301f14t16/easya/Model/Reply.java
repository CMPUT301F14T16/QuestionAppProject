package ca.ualberta.cmput301f14t16.easya.Model;

/**
 * The Reply class extends the {@link Content} class, providing no extra
 * functionality. This class exists primarily to create a clear Object Oriented
 * distinction between different uses of the {@link Content} class.
 * 
 * @author Brett Commandeur (commande)
 * @author Cauani
 *
 */
public class Reply extends Content {

	/**
	 * Creates an empty Reply object
	 */
	public Reply() {
		super();
	}

	/**
	 * Creates a Reply containing the body text provided, and associated with
	 * the given author ID.
	 * 
	 * @see Content#Content(String, String)
	 * 
	 * @param body
	 *            {@link String} stored by the new instance of Reply.
	 * @param authorId
	 *            Identifier used to keep track of the unique user that provided
	 *            the text of this Reply.
	 */
	public Reply(String body, String authorId) {
		super (body, authorId);
	}
}
