package ca.ualberta.cmput301f14t16.easya;

import java.sql.Date;
import java.util.List;

/**
 * @author Stephane
 *
 */
public class Topic extends Content {

	private List<Reply> replies;
	private int voteNumber;
	private String body;
	private Boolean favourite;
	private Boolean readLater;
	private String picture;

	/**
	 * @param title
	 * @param body
	 * @param date
	 * @param author
	 */
	public Topic(String title, String body, Date date, User author) {
		this.text = title;
		this.body = body;
		this.date = date;
		this.user = author;
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
	public String getTitle() {
		return text;
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
