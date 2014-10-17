package com.example.project2.test;

import java.util.ArrayList;

import junit.framework.TestCase;

public class PictureSortTest extends TestCase {
	
	public boolean sortOrder;
	
	ArrayList<Topics> listTopicsTest;
	
	for(int i = 0; i < ListTopics.size(); i++){
		listTopicsTest.add(ListTopics.get(i));
	}
	
	//sorts from Topics with pictures to Topics without.
	if (sortOrder == true){
	listQuestionTest = PictureSort(sortOrder, listTopicsTest);
	//Assuming that the Topics are sorted from has a to doesn't have a picture,
	//assert false if a previous picture doesn't have one but later one does. 
		for(i = 0; i < ListTopicsTest.size(); i++){
			if(listTopicsTest.get(i).hasPicture() == false){
			assertFalse(listTopicsTest.get(i++).hasPicture() == true);
			}
		}
	}
	else {
		for(i = 0; i < ListTopicsTest.size(); i++){
			if(listTopicsTest.get(i).hasPicture() == true){
			assertFalse(listTopicsTest.get(i++).hasPicture() == false);
			}
		}
	}
	
}
	
