package com.example.project2.test;

import junit.framework.TestCase;

public class MarkTopicAsFavoriteTest extends TestCase {
	
	Topics topic = new Topics();
	
	//adds topic to favorites;
	
	topic.markAsFavorite();
	
	//checks if topics has been added to favorites.
	
	assertTrue(User.getFavorites == topic);

}
