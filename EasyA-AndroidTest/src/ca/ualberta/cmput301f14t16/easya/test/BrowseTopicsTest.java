package ca.ualberta.cmput301f14t16.easya.test;

import java.util.ArrayList;

import ca.ualberta.cmput301f14t16.easya.Model.Answer;
import ca.ualberta.cmput301f14t16.easya.Model.Question;
import ca.ualberta.cmput301f14t16.easya.Model.Reply;
import ca.ualberta.cmput301f14t16.easya.Model.Topic;
import ca.ualberta.cmput301f14t16.easya.Model.User;
import ca.ualberta.cmput301f14t16.easya.View.MainView;
import android.content.Context;
import android.view.View;
import junit.framework.TestCase;


//This test covers tests for the use case Browse Topic Test.
//Simply tests if views are visible.

public class BrowseTopicsTest extends TestCase {
	
	public <V> void testBrowseTopicsTest() {
	//tests if views are shown (need to add list of topics or other objects to the view)
	Context ctx = null;
	View v = new View(ctx);
	assertTrue(v.isShown());
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
	//Topic Previews (not yet tested for part 3 as browse is not yet implemented)
	//User will choose what topics he/she would like to browse if he has Internet connectivity.
	//this is assuming topics is in the database. The user will select the topic he/she
	//would like to browse then the browseTopicPreview will retrieve that topic.
}
}
