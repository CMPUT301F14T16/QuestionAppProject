package ca.ualberta.cmput301f14t16.easya;

import java.util.Date;
import java.util.UUID;
/**                                                                                                            
 * The content is the base case for the topic and the reply.                                                                                                                        
 */ 
public abstract class Content {
	protected Date date;
	protected String body;
	protected String authorEmail;
	protected UUID cid;
	protected String type;
	
	/**
	 * @param date
	 * @param text
	 * @param cid
	 * @param user
	 */
	public Content(String body, Date date, String authorEmail) {
		super();
		this.date = date;
		this.body = body;
		this.authorEmail = authorEmail;
		
		//http://www.javapractices.com/topic/TopicAction.do?Id=56 on Oct21
		this.cid = UUID.randomUUID();
	}
	
	/**
	 * Return the type of the content
	 * @return 
	 */
	public UUID getCID(){
		return cid;
	}
	
	/**
	 * Return the type of the content
	 * @return 
	 */
	public String getBody(){
		return body;
	}
}
