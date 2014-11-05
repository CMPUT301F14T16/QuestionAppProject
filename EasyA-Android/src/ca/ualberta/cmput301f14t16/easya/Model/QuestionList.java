package ca.ualberta.cmput301f14t16.easya.Model;

import java.util.Date;

/**
 * Lightweight version of Question, for displaying in a list purpose only.
 * @author Cauani
 *
 */
public class QuestionList {
	private String id;
	private String title;
	private String answers;
	private String username;
	private String upvotes;
	private Date date;
	private boolean image;
	public QuestionList(){
		
	}
	
	public QuestionList(String id, String title, String username, String answers, String upvotes, boolean image, Date date){
		this.id = id;
		this.title = title;
		this.answers  = answers;
		this.upvotes = upvotes;
		this.username = username;
		this.image = image;
		this.date = date;
	}

	public String getId() {
		return this.id;
	}

	public String getTitle() {
		return this.title;
	}

	public String getAnswers() {
		return this.answers;
	}

	public String getUpvotes() {
		return this.upvotes;
	}
	
	public String getUsername(){
		return this.username;
	}
	
	public boolean getImage() {
		return this.image;
	}
	public Date getDate() {
		return this.date;
	}
}
