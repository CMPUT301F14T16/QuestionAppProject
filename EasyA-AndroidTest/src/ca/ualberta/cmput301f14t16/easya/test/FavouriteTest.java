package ca.ualberta.cmput301f14t16.easya.test;

import java.util.Date;
import java.util.ArrayList;
import java.util.List;

import ca.ualberta.cmput301f14t16.easya.Model.Answer;
import ca.ualberta.cmput301f14t16.easya.Model.MainModel;
import ca.ualberta.cmput301f14t16.easya.Model.Question;
import ca.ualberta.cmput301f14t16.easya.Model.Reply;
import ca.ualberta.cmput301f14t16.easya.Model.User;
import junit.framework.TestCase;

/**
 * 
 *  
 * @author Lingbo Tang,Klinton W Shmeid 
 *
 *
 */

//This test covers the use case of MarkQuestionAsFavourites
//and ViewMyFavourites

public class FavouriteTest extends TestCase {
	
	//Set all the needed test object
	private String picture;
	private List<Reply> replies;
	private List<String> favourites;
	private List<String> upVotes;
	private User user;
	private String QId;
	private String AId;
	private String RId;
	private String username;
	private String email;
	private String id;
	private Date createOn;
	private List<String> createdContent;
	public final static String USERKEY = "ca.ualberta.cmput301f14t16.easya.USERKEY";
	
	//Tests to see if a Topic can be set as favorite
	public void testFavouriteTest() {
		
		// Intialize the test object
		Question q1 = new Question("Title Submission Test", "Body of Question", "test@ualberta.ca");
		Answer a1 = new Answer("Body of answer", "someone@ualberta.ca");
		q1.addAnswer(a1);
		Reply r1 = new Reply("Body of reply", "reply@ualberta.ca");
		a1.addReply(r1);
		q1.addReply(r1);
		QId = q1.getId();
		AId = a1.getId();
		RId = r1.getId();
		Question q2 = new Question("Title Submission Test2","Body of Question2","test2@ualberta.ca");
		email = "lingbo19.tang@gmail.com";
		username = "lingbo747";
		
		
		user = new User(email,username);
		
		// Test set Question as Favourite
		try {
			user.setFavourite(q1.getId());
			assertTrue(favourites.contains(q1));
		} catch (Exception ex) {
			assertFalse(false);
		}
		
		// Test set Answer as Favourite
		try {
			user.setFavourite(a1.getId());
			assertTrue(favourites.contains(a1));
		} catch (Exception ex) {
			assertFalse(false);
		}
		
		// Test if favourites matches with user
		assertNotNull(user.getFavourites());	
		assertTrue(q1.checkFavourite(user));
		assertTrue(a1.checkFavourite(user));
		assertFalse(q2.checkFavourite(user));
		
	}
	
}
