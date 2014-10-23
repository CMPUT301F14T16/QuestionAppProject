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
	 * Constructor for Content that does not yet exist. 
	 * Date and id are generated automatically.
	 * @param body
	 * @param authorId
	 */
	public Content(String body, String authorId) {
		super();
		
		this.body = body;
		this.authorId = authorId;
		
		// Auto-generate Unique id.
		// http://www.javapractices.com/topic/TopicAction.do?Id=56 on Oct 21, 21
		this.id = UUID.randomUUID().toString();
		
		// Default date of creation is now.
		this.date = new Date();
	}
	
	/**
	 * Constructor for Content that already exists (e.g. recreating from object 
	 * stored on the webservice).
	 * @param body
	 * @param authorId
	 * @param date
	 * @param id
	 */
	public Content(String body, String authorId, Date date, String id) {
		this.body = body;
		this.authorId = authorId;
		this.date = date;
		this.id = id;
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
}
