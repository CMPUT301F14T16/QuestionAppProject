package ca.ualberta.cmput301f14t16.easya.test;

import java.util.ArrayList;

import ca.ualberta.cmput301f14t16.easya.Sort;
import ca.ualberta.cmput301f14t16.easya.Topic;
import junit.framework.TestCase;

public class SortTest extends TestCase {
	
	public void testSortUpvote() {
		boolean sortOrder;
		ArrayList<Topic> listTopicTest =  new ArrayList<Topic>();

		//highest to lowest sort.
		sortOrder = true;
			Sort.sortUpVote(sortOrder, listTopicTest);
			for(int i = 0; i < listTopicTest.size(); i++){
				assertTrue(listTopicTest.get(i).getUpVoteCount() >= listTopicTest.get(i++).getUpVoteCount());
			}
		//lowest to highest sort.
		sortOrder = false;
			Sort.sortUpVote(sortOrder, listTopicTest);
			
			for(int i = 0; i < listTopicTest.size(); i++){
				assertTrue(listTopicTest.get(i).getUpVoteCount() <= listTopicTest.get(i++).getUpVoteCount());
			
			}
	}
	
	public void DateSortTest() {
		boolean beforeOrAfter;
		ArrayList<Topic> listTopicsTest = new ArrayList<Topic>();
		//sorts from Topics with date most recent to oldest.
		beforeOrAfter = true;
		Sort.dateSort(beforeOrAfter, listTopicsTest);
			for(int i = 0; i < listTopicsTest.size(); i++){
				assertTrue(listTopicsTest.get(i).getDate().before(listTopicsTest.get(i++).getDate()) );
			}
		
		//sorts from oldest to most recent
		beforeOrAfter = false;
		Sort.dateSort(beforeOrAfter, listTopicsTest);
			for(int i = 0; i < listTopicsTest.size(); i++){
				assertTrue(listTopicsTest.get(i).getDate().after(listTopicsTest.get(i++).getDate()) );
			}
		}
	}

