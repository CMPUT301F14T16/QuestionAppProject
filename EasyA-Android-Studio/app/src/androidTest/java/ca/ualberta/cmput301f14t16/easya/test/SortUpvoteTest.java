package ca.ualberta.cmput301f14t16.easya.test;

import java.util.ArrayList;

import ca.ualberta.cmput301f14t16.easya.Question;
import junit.framework.TestCase;

public class SortUpvoteTest extends TestCase {
	
	public boolean sortOrder;
	Question question1 = new Question();
	
	ArrayList<Question> listQuestionsTest =  new ArrayList<Question>();
	listQuestionsTest.add(question1);

	//highest to lowest sort
	if (sortOrder == true){
		SortUpVote(sortOrder, listQuestionsTest);
	
		for(int i = 0; i < listQuestionsTest.size(); i++){
			assertTrue(listQuestionsTest.get(i).getUpVoteCount() >= listQuestionsTest.get(i++).getUpVoteCount());
	}
	}
	//lowest to highest sort
	else {
		SortUpVote(sortOrder, listQuestionsTest);
		
		for(int i = 0; i < listQuestionsTest.size(); i++){
			assertTrue(listQuestionsTest.get(i).getUpVoteCount() <= listQuestionsTest.get(i++).getUpVoteCount());
		
		}
	}

	
}
