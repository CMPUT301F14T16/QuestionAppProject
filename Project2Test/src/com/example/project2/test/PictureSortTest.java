package com.example.project2.test;

import java.util.ArrayList;

import junit.framework.TestCase;

public class PictureSortTest extends TestCase {
	
	ArrayList<Questions> listQuestionsTest;
	
	for(int i = 0; i < ListQuestions.size(); i++){
		listQuestionsTest.add(ListQuestions.get(i));
	}
	
	//sorts from questions with pictures to questions without.
	listQuestionTest = PictureSort(listQuestionsTest);
	
	//Assuming that the questions are sorted from has a to doesn't have a picture,
	//assert false if a previous picture doesn't have one but later one does. 
	for(i = 0; i < ListQuestionsTest.size(); i++){
		if(listQuestionsTest.get(i).hasPicture() == false){
			assertFalse(listQuestionsTest.get(i++).hasPicture() == true);
		}
	}
}
