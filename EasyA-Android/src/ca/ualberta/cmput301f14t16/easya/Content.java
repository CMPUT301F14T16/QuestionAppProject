package ca.ualberta.cmput301f14t16.easya;

import java.util.Date;
import java.util.UUID;

/**                                                                                                            
 * The content is the base case for the topic and the reply.                                                                                                                        
 */ 
public abstract class Content {
	private String body, id, userId;
	private Date date;
	
	/**
	 * No args constructor used by deserializers in recreation of content.
	 */
	public Content() {
		
	}
	
	/**
	 * Constructor for brand new, unsubmitted content.
	 * Date and id are automatically generated.
	 * 
	 * @param body		Main text of content.
	 * @param authorId	Unique identifier for author of content.
	 */
	public Content(String body, String userId) {
		this.body = body;
		this.userId = userId;
		this.date = new Date();
		this.id = UUID.randomUUID().toString(); //TODO: unify the creation of ID's method
	}
	
	public String getBody(){
		return body;
	}
	
	public String getAuthorId() {
		return this.userId;
	}
	
	public String getAuthorName(){
		try{
			return (User.getUserById(this.userId)).getUsername();
		}catch(Exception ex){
			return "";
		}
	}
	
	public Date getDate() {
		return date;
	}
	
	public String getId(){
		return id;
	}
	
	/**
	 * Sets the content's id. 
	 * Used in recreating a content object from the server.
	 * 
	 * @param newId
	 */
	// TODO Have server use provided id when creating a submission.
	public void setId(String newId) {
		this.id = newId;
	}
}
