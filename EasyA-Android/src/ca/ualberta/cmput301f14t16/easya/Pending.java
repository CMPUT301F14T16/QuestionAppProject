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
	private String questionId;
	private String answerId;
	private Content content;
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
	private Pending(String qID, String aID, Content c) {
		this.questionId = qID;
		this.answerId = aID;
		this.content = c;
		this.createdOn = new Date();
	}
	
	/*
	 * New Question
	 */
	public Pending(Question c) {
		this(null, null, c);
	}
	
	/*
	 * New Answer
	 */
	public Pending(String qID, Answer c) {
		this(qID, null, c);
	}
	/*
	 * New Reply
	 * (Had to change the order of the objects for the compiler not to interpret
	 * this constructor as the base constructor)
	 */
	public Pending(Reply c, String qID, String aID) {
		this(qID, aID, c);
	}
		 
	public Content getContent() {
		return this.content;
	}	
	
	public String getQuestionId(){
		return this.questionId;
	}
	
	public String getAnswerId(){
		return this.answerId;
	}

	public void addContent(Content content) {
		this.content = content;
		
	}
}
