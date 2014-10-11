package com.example.project2.test;

import java.util.ArrayList;

import junit.framework.TestCase;

public class DateSortTest extends TestCase {
	
ArrayList<Questions> listQuestionsTest;
	
	for(int i = 0; i < ListQuestions.size(); i++){
		listQuestionsTest.add(ListQuestions.get(i));
	}
	//sorts from questions with pictures to questions without.
	listQuestionTest = DateSort(listQuestionsTest);
	
	for(i = 0; i < ListQuestionsTest.size(); i++){
			assertTrue(listQuestionsTest.get(i).getDate().before(listQuestionsTest.get(i++).getDate()) );
	}
}

