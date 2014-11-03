package ca.ualberta.cmput301f14t16.easya.Model;

//TODO: update the javadoc

import java.util.Date;

import ca.ualberta.cmput301f14t16.easya.View.MainActivity;

/**
 *
 * Pending is cache like buffer used to store a {@link Question}, {@link Answer}
 * or {@link Reply} object that has not yet been submitted. Once the device
 * acquires an Internet connection, Any stored data is pushed to the webservice,
 * and Pending is cleared of data.
 * 
 * A Pending object will store all parents of the stored {@link Content}.
 * 
 * @author Lingbo Tang, Cauani
 * @version 1.1 Build 1000 Oct 27st, 2014.
 */

public class Pending {
	/**
	 * The unique ID of the {@link Question} parent (if any) of the Content to
	 * be submitted.
	 */
	private String questionId;
	/**
	 * The unique ID of the {@link Answer} parent (if any) of the Content to be
	 * submitted.
	 */
	private String answerId;
	/**
	 * The {@link Content} that is to be pushed to the ElasticSearch database.
	 */
	private Content content;

	private Date createdOn;

	/**
	 * Creates an empty Pending object.
	 */
	// No args constructor used by deserializers in recreation of content.
	public Pending() {
	}

	/**
	 * Creates a new instance of Pending containing the {@link Content}
	 * provided.
	 * 
	 * @param qID
	 *            Setter for {@link Pending#questionId}
	 * @param aID
	 *            Setter for {@link Pending#answerId}
	 * @param c
	 *            Content stored by the Pending object. Setter for
	 *            {@link Pending#content}
	 */
	private Pending(String qID, String aID, Content c) {
		this.questionId = qID;
		this.answerId = aID;
		this.content = c;
		this.createdOn = new Date();
		
		
	}

	/**
	 * Creates a new Pending object containing a {@link Question}.
	 * 
	 * @param c
	 *            Content stored by the Pending object. Setter for
	 *            {@link Pending#content}
	 */
	public Pending(Question c) {
		this(null, null, c);
	}

	/**
	 * Creates a new Pending object containing an {@link Answer}.
	 * 
	 * @param qID
	 *            Setter for {@link Pending#questionId}
	 * @param c
	 *            Content stored by the Pending object. Setter for
	 *            {@link Pending#content}
	 */
	public Pending(String qID, Answer c) {
		this(qID, null, c);
	}

	/*
	 * New Reply (Had to change the order of the objects for the compiler not to
	 * interpret this constructor as the base constructor)
	 */
	/**
	 * Creates a new Pending object containing a {@link Reply}.
	 * 
	 * @param qID
	 *            Setter for {@link Pending#questionId}
	 * @param aID
	 *            Setter for {@link Pending#answerID}
	 * @param c
	 *            Content stored by the Pending object. Setter for
	 *            {@link Pending#content}
	 */
	public Pending(Reply c, String qID, String aID) {
		this(qID, aID, c);
	}

	/**
	 * @return {@link Pending#content}
	 */
	public Content getContent() {
		return this.content;
	}

	/**
	 * @return {@link Pending#questionId}
	 */
	public String getQuestionId() {
		return this.questionId;
	}

	/**
	 * @return {@link Pending#answerId}
	 */
	public String getAnswerId() {
		return this.answerId;
	}
	
	/**
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj == null || obj.getClass() != this.getClass()) {
            return false;
        }

        Pending p = (Pending) obj;
        return (questionId == p.questionId
                     || (questionId != null && questionId.equals(p.getQuestionId())))
                && (answerId == p.answerId
                     || (answerId != null && answerId.equals(p.getAnswerId())))
                && (content != null && content.getId().equals(p.content.getId()));
    }
   
    /**
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result
                + ((questionId == null) ? 0 : questionId.hashCode());
        result = prime * result
                + ((answerId == null) ? 0 : answerId.hashCode());
        result = prime * result
                + ((content == null) ? 0 : content.getId().hashCode());
        return result;
    }
}
