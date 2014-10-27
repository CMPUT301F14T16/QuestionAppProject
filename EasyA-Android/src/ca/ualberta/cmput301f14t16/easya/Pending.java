package ca.ualberta.cmput301f14t16.easya;

/** Description of Pending   
 *
 * <p>
 * Pending is cache like buffer used to store
 * the unsubmitted Question, Answer and Reply.
 * Once the device get connections, we submit
 * them right away from pending,and clear space
 * for next time.
 * <p>
 * @return qid The question_id for elastic search in database
 * @return aid The answer_id for elastic search in database
 * @return rid The Reply_id for elastic search in database	update the UML later
 * @return content The content that is retrived in the pending
 * @author Lingbo Tang
 * @version 1.0 Build 1000 Oct 21st, 2014.
 */


import java.util.Date;
import java.util.List;

import android.util.Log;
import java.lang.reflect.Type;

public class Pending {

	protected String body, authorId;
	protected Date date;

	private Content content;
	
	/**
	 * No args constructor used by deserializers in recreation of content.
	 */
	public Pending() {
	}
	
	/**
	 * Constructor for brand new, unsubmitted content.
	 * Date and id are automatically generated.
	 * 
	 * @param body			Main text of content
	 * @param authorId		Unique identifier for author of content.
	 */
	public Pending(String body, String authorId) {
		this.body = body;
		this.authorId = authorId;
		this.date = new Date();
	}
	
	/**
	 * Check content view
	 * 
	 * @return content
	 */
	public Content getContent() {
		return content;
	}
	
	/**
	 * Check content body
	 * 
	 * @return body
	 */
	public String getBody() {
		return body;
	}
	
	/**
	 * Check content authorId
	 * 
	 * @return authorId
	 */
	public String getAuthorId() {
		return authorId;
	}
	
	/**
	 * Check content date
	 * 
	 * @return date
	 */
	public Date getDate() {
		return date;
	}
	
}
