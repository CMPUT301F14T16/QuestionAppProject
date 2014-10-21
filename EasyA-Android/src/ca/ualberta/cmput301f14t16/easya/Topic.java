package ca.ualberta.cmput301f14t16.easya;

import java.util.Date;
import java.util.List;

/**
 * @author Stephane
 *
 */
public class Topic extends Content {

	private List<Reply> replies;
	private int voteNumber;
	private Boolean favourite;
	private Boolean readLater;
	private String picture;

	/**
	 * @param title
	 * @param body
	 * @param date
	 * @param author
	 */
	public Topic(String body, Date date, User author) {
		super(body, date, author);
		
		/* should be taken care of by super class:
		this.body = body;
		this.date = date;
		this.user = author;
		*/
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
