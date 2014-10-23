package ca.ualberta.cmput301f14t16.easya;

import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * @author Stephane
 * @author Brett Commandeur
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
	public Topic(String body, String authorId, Date date, String id) {
		super(body, authorId, date, id);
	}

	/**
	 * 
	 */
	public void addImage() {

	}

	/**
	 * @param filename
	 */
	public void addImage(String filename) {

	}

	/**
	 * @param comment
	 */
	public void addReply(String comment) {

	}

	/**
	 * @return
	 */
	public List<Reply> getAllReplies() {
		return replies;
	}

	/**
	 * @return
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
	 * @return
	 */
	public String getImage() {
		return picture;
	}

	// Change method name in UML
	/**
	 * @param ID
	 * @return
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

	// TODO Change this in the UML
	/**
	 * @param isFavourite
	 */
	public void setFavourite(boolean isFavourite) {
		favourite = isFavourite;
	}

	// TODO change in UML
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
