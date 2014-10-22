package ca.ualberta.cmput301f14t16.easya;

import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * Topic is an extension of the Content class, used to provide various methods
 * required by the Question and Answer classes. Its primary functions primarily
 * include various getters and setters for various elements stored by itself,
 * or its subclasses.
 * @author Stephane
 *
 */
public abstract class Topic extends Content {

	protected List<Reply> replies;
	protected int voteNumber;
	protected Boolean favourite; // Topic can be favourite of >1 user.
	protected Boolean readLater; // Topic can be readLater of >1 user.
	protected String picture;

	/**
	 * @param title
	 * @param body
	 * @param date
	 * @param author
	 */
	public Topic(String body, Date date, String authorEmail) {
		super(body, date, authorEmail);
	}

	/**
	 * Attach an Image
	 */
	public void addImage() {

	}

	/**
	 * Add an image by file name
	 * @param filename
	 */
	public void addImage(String filename) {

	}

	/**
	 * Append a Reply object to this Topic
	 * @param comment The text contained by the Reply
	 */
	public void addReply(String comment) {

	}

	/**
	 * @return All replies contained by this Topic
	 */
	public List<Reply> getAllReplies() {
		return replies;
	}

	/**
	 * @return The body text of this Topic
	 */
	public String getBody() {
		return body;
	}

	/**
	 * 
	 */
	public void getFile() {

	}

	/**
	 * @return The Image attached to this Topic.
	 */
	public String getImage() {
		return picture;
	}

	// Change method name in UML
	/**
	 * @param ID The unique ID for the requested reply
	 * @return A single Reply by ID
	 */
	public Reply getReply(int ID) {
		return new Reply();
	}

	/**
	 * @return
	 */
	public int getUpVote() {
		return voteNumber;
	}

	/**
	 * @return
	 */
	public boolean hasPicture() {
		return false;
	}

	// Change this in the UML
	/**
	 * @param isFavourite
	 */
	public void setFavourite(boolean isFavourite) {
		favourite = isFavourite;
	}

	// change in UML
	/**
	 * @param isReadLater
	 */
	public void setReadLater(boolean isReadLater) {
		readLater = isReadLater;
	}

	/**
	 * 
	 */
	public void upVote() {
		voteNumber += 1;
	}

}
