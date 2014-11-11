package ca.ualberta.cmput301f14t16.easya.Model;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.UUID;

import ca.ualberta.cmput301f14t16.easya.Exceptions.NoContentAvailableException;

/**
 * The Content class is an abstract class that provides several utility methods
 * to its subclasses. The entirety of its functionality involves getters and
 * setters for its members.
 * 
 * Content subclasses exist to package and store information including and
 * relevant to a {@link String}, {@link Content#body}, as a framework for a
 * question and response system.
 */
public abstract class Content {
	/**
	 * The main collection of data stored by the Content subclass.
	 */
	private String body;
	/**
	 * An identifier used to refer to the unique creator of the data stored as
	 * {@link Content#body}.
	 */
	private String userId;
	/**
	 * A unique identifier used to refer to a specific instance of Content.
	 */
	private String id;
	/**
	 * The date at which the instance of Content was created.
	 */
	private Date createdOn;

	/**
	 * Creates an empty {@link Content} object.
	 */
	// No args constructor used by deserializers in recreation of content.
	public Content() {

	}
	
	/**
	 * Creates a new instance of Content containing the body text provided, and
	 * associated with the given author ID. {@link Content#date} and
	 * {@link Content#id} will be automatically generated.
	 * 
	 * @param body
	 *            {@link String} stored by the new instance of Content. Setter
	 *            for {@link Content#body}
	 * @param userId
	 *            Unique identifier for author of content. Setter for
	 *            {@link Content#userId}
	 */
	public Content(String body, String userId) {
		this.body = body;
		this.userId = userId;
		this.createdOn = new Date();
		this.id = UUID.randomUUID().toString(); //TODO: unify the creation of ID's method
	}
	
	
	/**
	 * @return {@link Content#body}
	 */
	public String getBody(){
		return body;
	}
	
	/**
	 * @return {@link Content#userId}
	 */
	public String getAuthorId() {
		return this.userId;
	}
	
	public String getAuthorName(){
		try{
			return (MainModel.getInstance().getUserById(this.userId)).getUsername();
		}catch(Exception ex){
			return "";
		}
	}

	/**
	 * @return {@link Content#createdOn}
	 */
	public Date getDate() {
		return createdOn;
	}

	/**
	 * @return {@link Content#id}
	 */
	public String getId(){
		return id;
	}
	
	/**
	 * Sets {@link Content#id}
	 * 
	 * @param newId
	 *            ID to set
	 */
	public void setId(String newId) {
		this.id = newId;
	}
	
	public String getAuthorDate(){
		try{
			String auxU = MainModel.getInstance().getUserById(this.getAuthorId()).getUsername();
			SimpleDateFormat sdf = new SimpleDateFormat("MMM dd yyyy hh:mm", Locale.CANADA);
	        String dateAsString = sdf.format(this.getDate());
			return dateAsString + " - " + auxU;
		}catch(NoContentAvailableException ex){
			return "";
		}
	}
}
