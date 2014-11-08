package ca.ualberta.cmput301f14t16.easya.test;

import java.util.ArrayList;

import ca.ualberta.cmput301f14t16.easya.Model.Question;
import ca.ualberta.cmput301f14t16.easya.Model.QuestionList;
import ca.ualberta.cmput301f14t16.easya.Model.Sort;
import ca.ualberta.cmput301f14t16.easya.Model.Topic;
import junit.framework.TestCase;

//This tests covers the 
//use cases of SortUpVote, 
//DateSort, and PictureSort.
//These sorts all belong to
//the Sort class. 

public class SortTest extends TestCase {
	
	public void testSortUpvote() {
		boolean sortOrder;
		Question q = new Question();
		ArrayList<QuestionList> listTopicTest =  new ArrayList<QuestionList>();

		//highest to lowest sort.
		sortOrder = true;
			Sort.sortUpVote(sortOrder, listTopicTest);
			for(int i = 0; i < listTopicTest.size(); i++){
				assertTrue(Integer.parseInt(listTopicTest.get(i).getUpvotes()) >= Integer.parseInt(listTopicTest.get(i++).getUpvotes()));
			}
		//lowest to highest sort.
		sortOrder = false;
			Sort.sortUpVote(sortOrder, listTopicTest);
			
			for(int i = 0; i < listTopicTest.size(); i++){
				assertTrue(listTopicTest.get(i).getUpvotes().length() <= listTopicTest.get(i++).getUpvotes().length());
			
			}
	}
	
	public void DateSortTest() {
		boolean beforeOrAfter;
		ArrayList<QuestionList> questionListTest = new ArrayList<QuestionList>();
		//sorts from Topics with date most recent to oldest.
		beforeOrAfter = true;
		Sort.dateSort(beforeOrAfter, questionListTest);
			for(int i = 0; i < questionListTest.size(); i++){
				assertTrue(questionListTest.get(i).getDate().before(questionListTest.get(i++).getDate()) );
			}
		
		//sorts from oldest to most recent
		beforeOrAfter = false;
		Sort.dateSort(beforeOrAfter, questionListTest);
			for(int i = 0; i < questionListTest.size(); i++){
				assertTrue(questionListTest.get(i).getDate().after(questionListTest.get(i++).getDate()) );
			}
		}
	
	public void PictureSortTest() {
		boolean sortOrder;
		
		ArrayList<QuestionList> questionListTest = new ArrayList<QuestionList>();
		
		//sorts from Topics with pictures to Topics without.
		sortOrder = true;
		Sort.pictureSort(sortOrder, questionListTest);
		//Assuming that the Topics are sorted from has a to doesn't have a picture,
		//assert false if a previous picture doesn't have one but later one does. 
			for(int i = 0; i < questionListTest.size(); i++){
				if(questionListTest.get(i).getImage() == false){
				assertFalse(questionListTest.get(i++).getImage() == true);
				}
			}
		sortOrder = false;
			for(int i = 0; i < questionListTest.size(); i++){
				if(questionListTest.get(i).getImage() == true){
				assertFalse(questionListTest.get(i++).getImage() == false);
				}
			}
		}
		
	}

