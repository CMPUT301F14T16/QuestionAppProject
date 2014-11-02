package ca.ualberta.cmput301f14t16.easya.test;

import java.util.ArrayList;

import ca.ualberta.cmput301f14t16.easya.Model.Question;
import ca.ualberta.cmput301f14t16.easya.Model.Sort;
import ca.ualberta.cmput301f14t16.easya.Model.Topic;
import junit.framework.TestCase;

/**
 * 
 * @author Klinton Shmeit
 * 
 * this tests covers the 
 * use cases of SortUpVote, 
 * DateSort, and PictureSort.
 * 
 */
public class SortTest extends TestCase {
	
	public void testSortUpvote() {
		boolean sortOrder;
		Question q = new Question();
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
	
	public void PictureSortTest() {
		boolean sortOrder;
		
		ArrayList<Topic> listTopicsTest = new ArrayList<Topic>();
		
		//sorts from Topics with pictures to Topics without.
		sortOrder = true;
		Sort.pictureSort(sortOrder, listTopicsTest);
		//Assuming that the Topics are sorted from has a to doesn't have a picture,
		//assert false if a previous picture doesn't have one but later one does. 
			for(int i = 0; i < listTopicsTest.size(); i++){
				if(listTopicsTest.get(i).hasPicture() == false){
				assertFalse(listTopicsTest.get(i++).hasPicture() == true);
				}
			}
		sortOrder = false;
			for(int i = 0; i < listTopicsTest.size(); i++){
				if(listTopicsTest.get(i).hasPicture() == true){
				assertFalse(listTopicsTest.get(i++).hasPicture() == false);
				}
			}
		}
		
	}

