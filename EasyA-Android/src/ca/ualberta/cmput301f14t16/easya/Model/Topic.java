package ca.ualberta.cmput301f14t16.easya.Model;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Stephane
 * @author Brett Commandeur
 * @author Cauani
 *
 */
public abstract class Topic extends Content {		
	private String picture;
	private List<Reply> replies;
	private List<String> favourites; // Topic can be favourite of >1 user.
	private List<String> upVotes;
	
	/**
	 * No args constructor used by deserializers when recreating a topic.
	 */
	public Topic() {}
	
	/**
	 * Constructor for brand new, unsubmitted topic.
	 * 
	 * @param body		Main content of the topic; handled by Superclass Content
	 * @param authorId	Unique identifier for author of the topic; handled by Superclass Content
	 */
	public Topic(String body, String userId) {
		super(body, userId);
		this.replies = new ArrayList<Reply>();
		this.upVotes = new ArrayList<String>();
	}

	/**
	 * @param filename
	 */
	public void addImage(String filename) {
		//TODO: create the AddImage method
	}

	/**
	 * @param comment
	 */
	public void addReply(Reply reply) {
		try{
			this.replies.add(reply);
		}catch(Exception ex){
			//TODO: deal with exception
		}
	}

	/**
	 * @return
	 */
	// TODO change name in UML
	public List<Reply> getReplies() {
		if (this.replies == null){
			this.replies = new ArrayList<Reply>();
		}
		return this.replies;
	}

	/**
	 * @return
	 */
	public String getImage() {
		return this.picture;
	}

	/**
	 * @return
	 */
	// TODO change name in UML
	public int getUpVoteCount() {
		return this.upVotes.size();
	}
	
	public String getUpVoteCountString(){
		try{
		return this.upVotes.size() <= 99 ? String.valueOf(this.upVotes.size()) : "99+";
		}catch(Exception ex){
			return "0";
		}
	}

	/*
	 * A switch for setting upvote
	 * If the user have already upvoted, redo that
	 * if not, upvote it
	 */
	public void setUpvote(User user){
		try{
			if (this.upVotes.contains(user.getId())){
				this.upVotes.remove(user.getId());
			}else{
				this.upVotes.add(user.getId());
			}
		}catch(Exception ex){
			//TODO: deal with exception
		}
		//TODO: Try to call directly if have internet, if have no internet, throw exception
	}
	
	/**
	 * @return
	 */
	public boolean hasPicture() {
		return (this.picture != null && !this.picture.isEmpty());
	} 
	
	/**
	 * A switch for setting a content as favorite
	 * If the user have already favorited this, unfavorite
	 * If the user never favorited this, favorite
	 * @param isFavourite
	 */
	public void setFavourite(User user) {
		try{
			if (this.favourites.contains(user.getId())){
				this.favourites.remove(user.getId());
			}else{
				this.favourites.add(user.getId());
			}
		}catch(Exception ex){
			
		}
		//TODO: Try to call directly if have internet, if have no internet, throw exception
	}
 
	public boolean checkFavourite(User user) {
		return this.favourites.contains(user.getId());
	}
	
	public boolean checkUpVote(User user) {
		try{
			return this.favourites.contains(user.getId());
		}catch(Exception ex){
			return false;
		}
	}
}
