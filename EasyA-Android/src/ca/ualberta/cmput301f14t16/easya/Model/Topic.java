package ca.ualberta.cmput301f14t16.easya.Model;

import java.util.ArrayList;
import java.util.List;

/**
 * Topic extends {@link Content}, providing added functionality to suit more
 * specific purposes.
 * 
 * This class adds methods for storing and accessing a list of {@link Reply}
 * objects, as well as a single picture. It also provides methods to tally the
 * total number of upvotes it has been given by users of the application.
 * 
 * @author Brett Commandeur
 * @author Cauani
 *
 */
public abstract class Topic extends Content {
	/**
	 * A picture attached to the Topic object.
	 */
	private byte[] picture;
	/**
	 * A list of replies created by users in response to the Topic.
	 */
	private List<Reply> replies;
	/**
	 * A list of {@link User} IDs who have upvoted this topic. One {@link User}
	 * may add at most one upvote to any given Topic, so a single {@link User}
	 * will appear at most once in this list. Therefore, the length of this list
	 * is the number of unique users who have upvoted this Topic, and so, the
	 * total number of upvotes recieved by this Topic.
	 */
	private List<String> upVotes;

	/**
	 * Creates an empty Topic.
	 */
	// No args constructor used by deserializers when recreating a topic.
	public Topic() {
	}

	/**
	 * Creates a new instance of Topic containing the body text provided, and
	 * associated with the given author ID. {@link Content#createdOn} and
	 * {@link Content#id} will be automatically generated.
	 * 
	 * @param body
	 *            {@link String} stored by the new instance of Topic. Setter for
	 *            {@link Content#body}
	 * @param userId
	 *            Unique identifier for author of Topic. Setter for
	 *            {@link Content#userId}
	 */
	public Topic(String body, String userId) {
		super(body, userId);
		this.replies = new ArrayList<Reply>();
		this.upVotes = new ArrayList<String>();
	}
	public Topic(String body, byte[] bitmap, String userId) {
		super(body, userId);
		this.picture = bitmap;
		this.replies = new ArrayList<Reply>();
		this.upVotes = new ArrayList<String>();
	}
	public Topic(String body, byte[] bitmap, String userId, double[] coordinate, String location) {
		super(body, userId, coordinate, location);
		this.picture = bitmap;
		this.replies = new ArrayList<Reply>();
		this.upVotes = new ArrayList<String>();
	}
		
	/**
	 * Adds a {@link Reply} to {@link Topic#replies}.
	 * 
	 * @param reply
	 *            The {@link Reply} to be added.
	 */
	public void addReply(Reply reply) {
		try {
			this.replies.add(reply);
		} catch (Exception ex) {
			// TODO: deal with exception
		}
	}

	/**
	 * @return {@link Topic#replies}
	 */
	// TODO change name in UML
	public List<Reply> getReplies() {
		if (this.replies == null) {
			this.replies = new ArrayList<Reply>();
		}
		return this.replies;
	}

	/**
	 * @return {@link Topic#picture}
	 */
	public byte[] getImage() {
		return this.picture;
	}

	/**
	 * Returns the muber of upvotes given to the Topic.
	 * 
	 * @return The length of {@link Topic#upVotes}
	 */
	// TODO change name in UML
	public int getUpVoteCount() {
		return this.upVotes.size();
	}

	public String getUpVoteCountString() {
		try {
			return this.upVotes.size() <= 99 ? String.valueOf(this.upVotes
					.size()) : "99+";
		} catch (Exception ex) {
			return "0";
		}
	}

	/**
	 * Checks if the {@link User} submitted is already in {@link Topic#upVotes}.
	 * If they are, it removes them from the list. Otherwise, it adds them to
	 * it.
	 * 
	 * @param userId
	 *            The {@link User} to be added.
	 */
	public void setUpvote(String userId) {
		if (this.upVotes.contains(userId)) {
			this.upVotes.remove(userId);
		} else {
			this.upVotes.add(userId);
		}
	}
	
	public List<String> getUpvotes() {
		return this.upVotes;
	}

	/**
	 * @return True if {@link Topic#picture} is not null.
	 */
	public boolean hasPicture() {
		return (this.picture != null);
	}

	/**
	 * @param user
	 * @return True if the provided {@link User} has marked the Topic as a
	 *         favourite (the {@link User} is found in {@link User#favourites}).
	 */
	public boolean checkFavourite(User user) {
		return user.getFavourites().contains(this.getId());
	}

	/**
	 * @param user
	 * @return True if the provided {@link User} has upvoted the Topic (the
	 *         {@link User} is found in {@link Topic#upVotes}.
	 */
	public boolean checkUpVote(User user) {
		try {
			return this.upVotes.contains(user.getId());
		} catch (Exception ex) {
			return false;
		}
	}
}
