package ca.ualberta.cmput301f14t16.easya.Model;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

import ca.ualberta.cmput301f14t16.easya.Exceptions.NoContentAvailableException;

/**
 * Provides a lightweight implementation of {@link Question}, to be used as a
 * condensed view of a {@link Question} object when several such objects are
 * stored in list form.
 * 
 * @see Question
 * 
 * @author Cauani
 *
 */
public class QuestionList {
	/**
	 * A unique identifier used to refer to the specific instance of the
	 * original {@link Question} object this QuestionList was derived from.
	 */
	private String id;
	/**
	 * A title String for this QuestionList
	 */
	private String title;
	/**
	 * The number of Answers associated to the original {@link Question} object
	 * (as a {@link String}).
	 */
	private String answers;
	/**
	 * An identifier used to refer to the unique creator of the data stored by
	 * the object.
	 */
	private String userid;
	/**
	 * An identifier used to refer to the name of the creator of the data stored by
	 * the object.
	 */
	private String username;
	/**
	 * The number of upvotes given to the original {@link Question} (as a
	 * {@link String}).
	 */
	private String upvotes;
	/**
	 * The date at which the instance of Content was created.
	 */
	private Calendar date;
	/**
	 * True if the original {@link Question} contained an image, False if not.
	 */
	private boolean image;

	/**
	 * Creates an empty QuestionList.
	 */
	public QuestionList() {

	}

	/**
	 * Creates a new QuestionList with the provided parameters.
	 * 
	 * @param id
	 *            Setter for {@link QuestionList#id}
	 * @param title
	 *            Setter for {@link QuestionList#title}
	 * @param username
	 *            Setter for {@link QuestionList#username}
	 * @param userid
	 *     		  Setter for {@link QuestionList#userid}
	 * @param answers
	 *            Setter for {@link QuestionList#answers}
	 * @param upvotes
	 *            Setter for {@link QuestionList#upvotes}
	 * @param image
	 *            Setter for {@link QuestionList#image}
	 */
	public QuestionList(String id, String title, String username, String userid,
			String answers, String upvotes, boolean image, Calendar date) {
		this.id = id;
		this.title = title;
		this.answers = answers;
		this.upvotes = upvotes;
		this.username = username;
		this.userid = userid;
		this.image = image;
		this.date = date;
	}

	/**
	 * @return {@link QuestionList#id}
	 */
	public String getId() {
		return this.id;
	}

	/**
	 * @return {@link QuestionList#title}
	 */
	public String getTitle() {
		return this.title;
	}

	/**
	 * @return {@link QuestionList#answers}
	 */
	public String getAnswers() {
		return this.answers;
	}

	/**
	 * @return {@link QuestionList#upvotes}
	 */
	public String getUpvotes() {
		return this.upvotes;
	}

	/**
	 * @return {@link QuestionList#username}
	 */
	public String getUsername() {
		return this.username;
	}

	/**
	 * @return {@link QuestionList#image}
	 */
	public boolean getImage() {
		return this.image;
	}
	
	/**
	 * @return {@link QuestionList#date}
	 */
	public Calendar getDate() {
		return this.date;
	}


	/**
	 * @return {@link QuestionList#userid}
	 */
	public String getAuthorId() {
		return this.userid;
	}

	public String getAuthorDate(){
		try{
			getDate().add(Calendar.MILLISECOND, TimeZone.getTimeZone(Time.getTimezone()).getOffset(getDate().getTimeInMillis()));
			String auxU = MainModel.getInstance().getUserById(this.getAuthorId()).getUsername();
			SimpleDateFormat sdf = new SimpleDateFormat("MMM dd yyyy hh:mm", Locale.getDefault());
			sdf.setTimeZone(TimeZone.getTimeZone(Time.getTimezone()));
			return sdf.format(getDate().getTime()) + " - @" + auxU;
		}catch(NoContentAvailableException ex){
			return "";
		}
	}
}
