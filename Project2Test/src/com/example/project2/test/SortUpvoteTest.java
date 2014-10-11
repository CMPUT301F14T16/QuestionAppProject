package com.example.project2.test;

import java.util.ArrayList;

import junit.framework.TestCase;

public class SortUpvoteTest extends TestCase {
	
	ArrayList<Questions> listQuestionsTest;
	
	for(int i = 0; i < ListQuestions.size(); i++){
		listQuestionsTest.add(ListQuestions.get(i));
	}
	
	listQuestionTest = SortUpVote(listQuestionsTest);
	
	for(i = 0; i < ListQuestionsTest.size(); i++){
		assertTrue(listQuestionsTest.get(i).getUpVote() <= listQuestionsTest.get(i++).getUpVote());
	}
}
