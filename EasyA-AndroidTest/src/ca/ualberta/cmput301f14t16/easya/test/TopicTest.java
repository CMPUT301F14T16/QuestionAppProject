package ca.ualberta.cmput301f14t16.easya.test;

import ca.ualberta.cmput301f14t16.easya.Model.Answer;
import ca.ualberta.cmput301f14t16.easya.Model.Question;
import ca.ualberta.cmput301f14t16.easya.Model.Topic;
import ca.ualberta.cmput301f14t16.easya.Model.User;
import junit.framework.TestCase;


 //Tests for Topic functions involving Q and A.

public class TopicTest extends TestCase {
	
	User u =  new User();
	
	//test for UpVoteTopicsTest
	public void testUpVoteTopics() {
		Answer answer = new Answer();
		Question question = new Question();
		answer.setUpvote(u);
		question.setUpvote(u);
		assertTrue(answer.getUpVoteCount() == 1);
		assertTrue(question.getUpVoteCount() == 1);
		answer.setUpvote(u);
		question.setUpvote(u);
		assertTrue(answer.getUpVoteCount() == 0);
		assertTrue(question.getUpVoteCount() == 0);
	}
}
