package ca.ualberta.cmput301f14t16.easya.test;

import java.util.ArrayList;

import ca.ualberta.cmput301f14t16.easya.Model.Answer;
import ca.ualberta.cmput301f14t16.easya.Model.Question;
import ca.ualberta.cmput301f14t16.easya.Model.Reply;
import ca.ualberta.cmput301f14t16.easya.Model.Topic;
import ca.ualberta.cmput301f14t16.easya.Model.User;
import android.view.View;
import junit.framework.TestCase;

/**
 * 
 * @author Klinton Shmeit
 * 
 * This test covers tests for the use case Browse Topic Test.
 *
 */
public class BrowseTopicsTest extends TestCase {
	
	public void testBrowseTopicsTest() {
	//use one topic for the test. this topic will be gotten from a list of topics.
	Question question = new Question("Q title test", "Q body test", "Q author id test");
	Answer answer = new Answer("A body test", "A author id test");
	Reply reply = new Reply("R body test", "R author id test");
	User user = new User();
	/**
	 * adds necessary replies, answers, and questions for the test. 
	 */
	question.addAnswer(answer);
	question.addReply(reply);
	question.setFavourite(user);
	answer.setFavourite(user);
	answer.addReply(reply);
	//adds the question of the topic to an array of saved questions
	//checks if the retrieved are the correct ones 
	//that the user requested. 
	//assertTrue(question.getAnswerById("A author id test").equals(answer));
	//redundant code to test reply.
	assertTrue(question.getReplies().get(0).getId().equals(reply.getId()));
	//assertTrue(question.getAnswers().equals(answer));
	//assertTrue(question.getAllSubmittedQuestions().equals(question));
	//assertTrue(Answer.getAllSavedQuestions().equals(question));

	//View topic and topic previews this includes favorites, saved, 
	
	
	//View if Favourited.
	//assertTrue(question.checkFavourite(user) == true);
	//assertTrue(answer.checkFavourite(user) == true);
	
	//Topic Previews 
	//User will choose what topics he/she would like to browse if he has internet connectivity.
	//this is assuming topics is in the database. The user will select the topic he/she
	//would like to browse then the browseTopicPreview will retrieve that topic.
	//TODO Make a browse topic previews part class if we are still implementing this.
	//assertTrue(browseTopicPreviews(topics.getName).equals(topics));
}
}
