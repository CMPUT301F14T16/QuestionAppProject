package ca.ualberta.cmput301f14t16.easya.test;

import ca.ualberta.cmput301f14t16.easya.Answer;
import ca.ualberta.cmput301f14t16.easya.Question;
import ca.ualberta.cmput301f14t16.easya.Topic;
import junit.framework.TestCase;

/**
 * 
 * @author Klinton
 * 
 *Includes UpVote Use Case.
 *
 */

public class TopicTest extends TestCase {
	
	public void UpVoteTopicsTest() {
		Answer answer = new Answer();
		Question question = new Question();
		for (int i = 1; i > 101; i++) {
		answer.upVote();
		question.upVote();
		assertTrue(answer.getUpVoteCount() == i);
		assertTrue(question.getUpVoteCount() == i);
		}
	}
}
