package ca.ualberta.cmput301f14t16.easya;

import java.util.Date;
import java.util.UUID;

public class Content {
	protected Date date;
	protected String body;
	protected UUID cid;
	protected User user;
	protected String type;
	
	/**
	 * @param date
	 * @param text
	 * @param cid
	 * @param user
	 */
	public Content(String body, Date date, User user) {
		super();
		this.date = date;
		this.body = body;
		this.user = user;
		
		//http://www.javapractices.com/topic/TopicAction.do?Id=56 on Oct21
		this.cid = UUID.randomUUID();
	}
	public String getType(){
		return null;
	}
	
	public UUID getCID(){
		return cid;
	}
	
	public String getBody(){
		return body;
	}
}
