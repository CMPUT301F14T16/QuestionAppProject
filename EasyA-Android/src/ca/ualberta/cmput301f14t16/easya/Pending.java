package ca.ualberta.cmput301f14t16.easya;

//TODO: update the javadoc
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
 * @author Cauani
 * @version 1.1 Build 1000 Oct 27st, 2014. * 
 */

import java.util.Date;

public class Pending {
	private String QuestionId;
	private String AnswerId;
	private Content Content;
	private Date createdOn;
	
	/**
	 * No args constructor used by deserializers in recreation of content.
	 */
	public Pending() {
	}
	
	/**
	 * Constructor for brand new, unsubmitted content.
	 * Date and id are automatically generated.
	 */
	public Pending(String qID, String aID, Content c) {
		this.QuestionId = qID;
		this.AnswerId = aID;
		this.Content = c;
		this.createdOn = new Date();
	}
	
	public Pending(Content c, String i) {
		this(null, null, c);
	}
	
	public Pending(String qID, Content c) {
		this(qID, null, c);
	}
		 
	public Content getContent() {
		return this.Content;
	}	
	
	public String getQuestionId(){
		return this.QuestionId;
	}
	
	public String getAnswerId(){
		return this.AnswerId;
	}
}
