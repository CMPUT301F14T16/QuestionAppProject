package com.example.project2.test;

import java.util.ArrayList;

import junit.framework.TestCase;

public class SortUpvoteTest extends TestCase {
	
	public boolean sortOrder;
	
	ArrayList<Topics> listTopicsTest;
	
	for(int i = 0; i < ListTopics.size(); i++){
		listTopicsTest.add(ListTopics.get(i));
	}
	//highest to lowest sort
	if (sortOrder == true){
		listQuestionTest = SortUpVote(sortOrder, listTopicsTest);
	
		for(i = 0; i < ListTopicsTest.size(); i++){
			assertTrue(listTopicsTest.get(i).getUpVote() >= listTopicsTest.get(i++).getUpVote());
	}
	}
	//lowest to highest sort
	else {
		listQuestionTest = SortUpVote(sortOrder, listTopicsTest);
		
		for(i = 0; i < ListTopicsTest.size(); i++){
			assertTrue(listTopicsTest.get(i).getUpVote() <= listTopicsTest.get(i++).getUpVote());
		
	}
}
