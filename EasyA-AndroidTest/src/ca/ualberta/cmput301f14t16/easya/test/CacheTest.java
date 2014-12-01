package ca.ualberta.cmput301f14t16.easya.test;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import ca.ualberta.cmput301f14t16.easya.Exceptions.NoContentAvailableException;
import ca.ualberta.cmput301f14t16.easya.Model.Question;
import ca.ualberta.cmput301f14t16.easya.Model.QuestionList;
import ca.ualberta.cmput301f14t16.easya.Model.Data.Cache;
import junit.framework.TestCase;

public class CacheTest extends TestCase {
	//tests if content is saved in the cache. 
	
	public void testSavedContent() throws NoContentAvailableException {
		//create test question to save to cache.
		//Cache cache =  new Cache();
		double[] dis1={0.0,0.0};
		Calendar c = new GregorianCalendar();
		c.set(1993, 10, 10);
		c.set(1994, 10, 10);
		List<QuestionList> questionListTest = new ArrayList<QuestionList>();
		Question testSavedq= new Question();
		String testID = testSavedq.getId();
		Cache.getInstance().SaveSingleQuestion(testSavedq);
		assertTrue(questionListTest.isEmpty());
		questionListTest = Cache.getInstance().getAllQuestions();
		assertFalse(questionListTest.isEmpty());
		//assertTrue((Cache.getInstance().getQuestionById(testID).equals(testSavedq)));
		
		
	}

}
