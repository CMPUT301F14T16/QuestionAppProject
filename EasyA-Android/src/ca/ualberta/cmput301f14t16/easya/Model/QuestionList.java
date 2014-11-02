package ca.ualberta.cmput301f14t16.easya.Model;

/**
 * Lightweight version of Question, for displaying in a list purpose only.
 * @author Cauani
 *
 */
public class QuestionList {
	private String id;
	private String title;
	private String comments;
	private int upvotes;
	private boolean image;
	public QuestionList(){
		
	}
	
	public QuestionList(String id, String title, String username, String comments, int upvotes, boolean image){
		this.id = id;
		this.title = title;
		this.comments = comments;
		this.upvotes = upvotes;
		this.image = image;
	}

	public String getId() {
		return this.id;
	}

	public String getTitle() {
		return this.title;
	}

	public String getComments() {
		return this.comments;
	}

	public int getUpvotes() {
		return this.upvotes;
	}
	
	public boolean getImage() {
		return this.image;
	}
}
