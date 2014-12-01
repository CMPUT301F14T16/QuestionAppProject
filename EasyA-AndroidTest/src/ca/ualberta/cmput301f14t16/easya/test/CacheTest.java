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

/**
 * 
 *  
 * @author Lingbo Tang,Klinton W Shmeid 
 *
 *
 */

//This test is covers use cases of DownloadQuestionDevice
//View my Saved Question and View my submitted Question
//We test cache to implement the practical cases
//Since Cache is the Model which handles the test Cases
//if we can getAllQuestions() from cache correctly
//we have enough confidence to say we pass the test.
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
		// Test if cquestion can be stored in cache.
		Question q1 = new Question("Title Submission Test", "Body of Question", "test@ualberta.ca");
		String testID = q1.getId();
		Cache.getInstance().SaveSingleQuestion(q1);
		questionListTest = Cache.getInstance().getAllQuestions();
		assertFalse(questionListTest.isEmpty());
		assertTrue((Cache.getInstance().getQuestionById(testID).equals(q1)));
	}
}
