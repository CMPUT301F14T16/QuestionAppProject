package ca.ualberta.cmput301f14t16.easya.test;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import ca.ualberta.cmput301f14t16.easya.Model.GeoCoder;
import ca.ualberta.cmput301f14t16.easya.Model.Question;
import ca.ualberta.cmput301f14t16.easya.Model.QuestionList;
import ca.ualberta.cmput301f14t16.easya.Model.Sort;
import ca.ualberta.cmput301f14t16.easya.Model.Topic;
import junit.framework.TestCase;


/**
 * 
 *  
 * @author Lingbo Tang,Klinton W Shmeid 
 *
 *
 */

//This tests covers the 
//use cases of SortUpVote, 
//DateSort, PictureSort, and 
//sort by location nearest.
//These sorts all belong to
//the Sort class. 
//According to these, 
//Nearby Location and
//Nearby Questions are 
//also covered in sort.

public class SortTest extends TestCase {
	
	public void testSortUpvote() {
		boolean sortOrder;
		Question q = new Question();
		List<QuestionList> listTopicTest =  new ArrayList<QuestionList>();
		double[] dis1={0.0,0.0};
		double[] dis2={1.0,-1.0};
		Calendar c = new GregorianCalendar();
		Calendar c2 = new GregorianCalendar();
		c.set(1993, 10, 10);
		c.set(1994, 10, 10);
		List<QuestionList> questionListTest = new ArrayList<QuestionList>();
		QuestionList first= new QuestionList("id","title","username","uerid",
				"answer",1, false, c, dis1,"location");
		QuestionList second= new QuestionList("id","title","username","uerid",
				"answer",2, false, c2, dis2,"location");
		questionListTest.add(first);
		questionListTest.add(second);
		//highest to lowest sort.
		sortOrder = true;
			listTopicTest = Sort.sortUpVote(sortOrder, listTopicTest);
			for(int i = 0; i < listTopicTest.size(); i++){
				assertTrue(Integer.parseInt(listTopicTest.get(i).getUpvotes()) >= Integer.parseInt(listTopicTest.get(i++).getUpvotes()));
			}
		//lowest to highest sort.
		sortOrder = false;
		listTopicTest = Sort.sortUpVote(sortOrder, listTopicTest);
			
			for(int i = 0; i < listTopicTest.size(); i++){
				assertTrue(listTopicTest.get(i).getUpvotes().length() <= listTopicTest.get(i++).getUpvotes().length());
			
			}
	}
	
	public void DateSortTest() {
		boolean beforeOrAfter;
		double[] dis1={0.0,0.0};
		double[] dis2={1.0,-1.0};
		Calendar c = new GregorianCalendar();
		Calendar c2 = new GregorianCalendar();
		c.set(1993, 10, 10);
		c.set(1994, 10, 10);
		List<QuestionList> questionListTest = new ArrayList<QuestionList>();
		QuestionList first= new QuestionList("id","title","username","uerid",
				"answer",1, false, c, dis1,"location");
		QuestionList second= new QuestionList("id","title","username","uerid",
				"answer",2, false, c2, dis2,"location");
		questionListTest.add(first);
		questionListTest.add(second);
		//sorts from Topics with date most recent to oldest.
		beforeOrAfter = true;
		questionListTest = Sort.sortDate(beforeOrAfter, questionListTest);
			for(int i = 0; i < questionListTest.size(); i++){
				assertTrue(questionListTest.get(i).getDate().before(questionListTest.get(i++).getDate()) );
			}
		
		//sorts from oldest to most recent
		beforeOrAfter = false;
		Sort.sortDate(beforeOrAfter, questionListTest);
			for(int i = 0; i < questionListTest.size(); i++){
				assertTrue(questionListTest.get(i).getDate().after(questionListTest.get(i++).getDate()) );
			}
		}
	
	public void PictureSortTest() {
		boolean sortOrder;
		double[] dis1={0.0,0.0};
		double[] dis2={1.0,-1.0};
		Calendar c = new GregorianCalendar();
		List<QuestionList> questionListTest = new ArrayList<QuestionList>();
		QuestionList first= new QuestionList("id","title","username","uerid",
				"answer",1, false, c, dis1,"location");
		QuestionList second= new QuestionList("id","title","username","uerid",
				"answer",2, false, c, dis2,"location");
		questionListTest.add(first);
		questionListTest.add(second);
		//sorts from Topics with pictures to Topics without.
		sortOrder = true;
		questionListTest = Sort.sortPicture(sortOrder, questionListTest);
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
		
	//tests that Sort by location works by creating 2 questions 
	//with different location and testing if it has been 
	//sorted.
	public void GeolocationTest() {
		boolean sortOrder;
		List<QuestionList> questionListTest = new ArrayList<QuestionList>();
		double[] dis1={0.0,0.0};
		double[] dis2={1.0,-1.0};
		Calendar c = new GregorianCalendar();
		QuestionList first= new QuestionList("id","title","username","uerid",
				"answer",1, false, c, dis1,"location");
		QuestionList second= new QuestionList("id","title","username","uerid",
				"answer",2, false, c, dis2,"location");
		questionListTest.add(first);
		questionListTest.add(second);
		sortOrder = true;
		questionListTest = Sort.sortDistance(sortOrder, questionListTest);
		for(int i = 0; i < questionListTest.size(); i++){
			double[] testDis1={0.0,0.0};
			assertFalse(GeoCoder.toFindDistance(questionListTest.get(i).getCoordinates(), testDis1) > 
					GeoCoder.toFindDistance(questionListTest.get(i++).getCoordinates(), testDis1));
		}
		sortOrder = false;
		for(int i = 0; i < questionListTest.size(); i++){
			double[] testDis1={0.0,0.0};
			assertFalse(GeoCoder.toFindDistance(questionListTest.get(i).getCoordinates(), testDis1) < 
					GeoCoder.toFindDistance(questionListTest.get(i++).getCoordinates(), testDis1));
			}
		}
	}

