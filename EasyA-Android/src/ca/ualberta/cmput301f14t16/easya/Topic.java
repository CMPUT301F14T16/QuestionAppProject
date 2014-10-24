package ca.ualberta.cmput301f14t16.easya;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Stephane
 * @author Brett Commandeur
 *
 */
public abstract class Topic extends Content {

	protected List<Reply> replies;
	protected int voteCount;
	protected Boolean favourite; // Topic can be favourite of >1 user.
	protected Boolean readLater; // Topic can be readLater of >1 user.
	protected String picture;

	/**
	 * No args constructor used by deserializers when recreating a topic.
	 */
	public Topic() {
		super();
	}
	
	/**
	 * Constructor for brand new, unsubmitted topic.
	 * 
	 * @param body		Main content of the topic; handled by Superclass Content
	 * @param authorId	Unique identifier for author of the topic; handled by Superclass Content
	 */
	public Topic(String body, String authorId) {
		super(body, authorId);
		this.replies = new ArrayList<Reply>();
		this.voteCount = 0;
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
	public void addReply(Reply reply) {
		replies.add(reply);
	}

	/**
	 * @return
	 */
	// TODO change name in UML
	public List<Reply> getReplies() {
		return replies;
	}

	// TODO Topic is a data class, does not manage files!
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

	// TODO Change method name in UML
	/**
	 * @param ID
	 * @return
	 */
	public Reply getReplyById(int ID) {
		return new Reply();
	}

	/**
	 * @return
	 */
	// TODO change name in UML
	public int getUpVoteCount() {
		return voteCount;
	}

	/**
	 * @return
	 */
	public boolean hasPicture() {
		return false;
	}

	// TODO Remove this method, Change this in the UML
	/**
	 * @param isFavourite
	 */
	public void setFavourite(boolean isFavourite) {
		favourite = isFavourite;
	}

	// TODO Remove this method, change in UML
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
		voteCount += 1;
	}

}
