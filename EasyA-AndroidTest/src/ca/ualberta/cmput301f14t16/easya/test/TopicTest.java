package ca.ualberta.cmput301f14t16.easya.test;

import ca.ualberta.cmput301f14t16.easya.Model.Answer;
import ca.ualberta.cmput301f14t16.easya.Model.Question;
import ca.ualberta.cmput301f14t16.easya.Model.Reply;
import ca.ualberta.cmput301f14t16.easya.Model.Topic;
import ca.ualberta.cmput301f14t16.easya.Model.User;
import junit.framework.TestCase;

 //This Test Covers the User case of View Topic and Upvote Topic
 //Tests for Topic functions involving Q and A and R.
 //Since we are using MVC model, if model works, so does view
 //Then we just test if model works.

public class TopicTest extends TestCase {
	
	User u =  new User("test@test.com", "testUsername");
	
	//test for UpVoteTopicsTest
	public void testUpVoteTopics() {
		// Test some basic use cases such as
		// adding Question/Answer/Reply
		// Upvote Question/Answer/Reply
		// View Question/Answer/Reply
		
		// Construct Question,Answer and Reply
		Question question = new Question("Q title test", "Q body test", "Q author id test");
		Answer answer = new Answer("A body test", "A author id test");
		Reply reply = new Reply("R body test", "R author id test");
		
		//Test if upvote works when no one upvotes before
		answer.setUpvote(u.getId());
		question.setUpvote(u.getId());
		assertTrue(answer.getUpVoteCount() == 1);
		assertTrue(question.getUpVoteCount() == 1);
		
		//Test if we are get & view the correct topics
		assertEquals(question.getTitle(),"Q title test");
		assertEquals(answer.getBody(),"A body test");
		assertEquals(reply.getBody(),"R body test");
		
		//Test if the same user can only upvote once
		answer.setUpvote(u.getId());
		question.setUpvote(u.getId());
		assertTrue(answer.getUpVoteCount() == 0);
		assertTrue(question.getUpVoteCount() == 0);
	}
}
