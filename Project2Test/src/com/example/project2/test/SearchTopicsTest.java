package com.example.project2.test;

import java.util.ArrayList;

import junit.framework.TestCase;

public class SearchTopicsTest extends TestCase {
	
	ArrayList<Topics> topicListTest;
	Topic testTopic1 =  new Topic;
	Topic testTopic2 =  new Topic;
	Topic testTopic3 =  new Topic;
	String keyword = "testTopic1";
	
	topicsListTest.add(testTopic1);
	topicsListTest.add(testTopic2);
	topicsListTest.add(testTopic3);
	
	//checks to see if the user input searches correctly.
	
	assertTrue(SearchTopics(keyword, topicsListTest).equals(testTopic1));
}
