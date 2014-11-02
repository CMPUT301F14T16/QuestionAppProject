package ca.ualberta.cmput301f14t16.easya.Model;

/**
 * Lightweight version of Question, for displaying in a list purpose only.
 * @author Cauani
 *
 */
public class QuestionList {
	private String id;
	private String title;
	private String answers;
	private String upvotes;
	private boolean image;
	public QuestionList(){
		
	}
	
	public QuestionList(String id, String title, String username, String answers, String upvotes, boolean image){
		this.id = id;
		this.title = title;
		this.answers  = answers;
		this.upvotes = upvotes;
		this.image = image;
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
	
	public boolean getImage() {
		return this.image;
	}
}
