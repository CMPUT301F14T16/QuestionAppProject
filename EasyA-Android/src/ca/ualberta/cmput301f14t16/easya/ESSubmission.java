package ca.ualberta.cmput301f14t16.easya;

import java.lang.reflect.Type;

public class ESSubmission {
	String type;
	Object object; // Question, Answer, or Reply
	String questionId;
	String answerId;
	/**
	 * No args constructor, used by deserializers in recreation of object.
	 */
	public ESSubmission() {
		// no args constructor
	}
	/**
	 * Args constructor for first creating an ESSubmission from scratch.
	 * 
	 * @param type
	 * @param object
	 * @param questionId
	 * @param answerId
	 */
	public ESSubmission(String type, Content object, String questionId, String answerId) {
		this.type = type;
		this.object = object;
		this.questionId = questionId;
		this.answerId = answerId;
	}
	/**
	 * @return the questionId
	 */
	public String getQuestionId() {
		return questionId;
	}
	/**
	 * @return the answerId
	 */
	public String getAnswerId() {
		return answerId;
	}
	/**
	 * @return the type
	 */
	public String getType() {
		return type;
	}
	/**
	 * @return the object
	 */
	public Object getObject() {
		return object;
	}
}
