package com.example.project2.test;

import java.util.ArrayList;

import junit.framework.TestCase;

public class DateSortTest extends TestCase {
	public boolean beforeOrAfter;
ArrayList<Topics> listTopicsTest;
	
	for(int i = 0; i < ListTopics.size(); i++){
		listTopicsTest.add(ListTopics.get(i));
	}
	//sorts from Topics with date most recent to oldest.
	if(beforeOrAfter == true){
	listQuestionTest = DateSort(beforeOrAfter, listTopicsTest);
		for(i = 0; i < ListTopicsTest.size(); i++){
			assertTrue(listTopicsTest.get(i).getDate().before(listTopicsTest.get(i++).getDate()) );
		}
	}
	//sorts from oldest to most recent
	else{
	listQuestionTest = DateSort(beforeOrAfter, listTopicsTest);
		for(i = 0; i < ListTopicsTest.size(); i++){
			assertTrue(listTopicsTest.get(i).getDate().after(listTopicsTest.get(i++).getDate()) );
		}
	}
}

