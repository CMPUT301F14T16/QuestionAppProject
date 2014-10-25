package ca.ualberta.cmput301f14t16.easya;

import java.util.Date;
import java.util.UUID;

/**                                                                                                            
 * The content is the base case for the topic and the reply.                                                                                                                        
 */ 
public abstract class Content {
	protected String body, authorId, id;
	protected Date date;
	
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
	public Content(String body, String authorId) {
		this.body = body;
		this.authorId = authorId;
		this.date = new Date();
		this.id = UUID.randomUUID().toString();
	}
	
	public String getBody(){
		return body;
	}
	
	public String getAuthorId() {
		return authorId;
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
