package ca.ualberta.cmput301f14t16.easya.test;

import java.util.ArrayList;
import java.util.List;

import ca.ualberta.cmput301f14t16.easya.Exceptions.NoContentAvailableException;
import ca.ualberta.cmput301f14t16.easya.Model.Answer;
import ca.ualberta.cmput301f14t16.easya.Model.MainModel;
import ca.ualberta.cmput301f14t16.easya.Model.Question;
import ca.ualberta.cmput301f14t16.easya.Model.QuestionList;
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
	
	public void testBrowseTopicsTest() {
	//tests if topics are there.
	MainModel mm = MainModel.getInstance();
	List<QuestionList> ql = null;
	try {
		ql = mm.getAllQuestions();
	} catch (NoContentAvailableException e) {
		fail("Could not recieve content");
		e.printStackTrace();
	}
	assertNotNull(ql);
	//Topic Previews (not yet tested for part 3 as browse is not yet implemented)
	//User will choose what topics he/she would like to browse if he has Internet connectivity.
	//this is assuming topics is in the database. The user will select the topic he/she
	//would like to browse then the browseTopicPreview will retrieve that topic.
}
}
