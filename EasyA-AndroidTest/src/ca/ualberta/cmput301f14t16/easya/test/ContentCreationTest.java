package ca.ualberta.cmput301f14t16.easya.test;

import ca.ualberta.cmput301f14t16.easya.Model.Answer;
import ca.ualberta.cmput301f14t16.easya.Model.Question;
import ca.ualberta.cmput301f14t16.easya.Model.Reply;
import ca.ualberta.cmput301f14t16.easya.Model.User;
import junit.framework.TestCase;

public class ContentCreationTest extends TestCase {
	
	public void testContentCreationTest() {
		//use one topic for the test. this topic will be gotten from a list of topics.
		Question question = new Question("Q title test", "Q body test", "Q author id test");
		Answer answer = new Answer("A body test", "A author id test");
		Reply reply = new Reply("R body test", "R author id test");
		User user = new User();
		//adds the question of the topic to an array of saved questions
		question.addAnswer(answer);
		question.addReply(reply);
		question.setFavourite(user);
		answer.setFavourite(user);
		answer.addReply(reply);
		//tests if answer added to question is correct
		assertTrue(question.getAnswerCount() == 1);
		//smoke test code reply and answers.
		assertTrue(question.getReplies().get(0).getId().equals(reply.getId()));
		assertTrue(question.getAnswers().get(0).equals(answer));
	}

}
