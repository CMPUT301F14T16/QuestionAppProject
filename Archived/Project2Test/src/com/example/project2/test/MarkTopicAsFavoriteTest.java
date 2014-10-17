package com.example.project2.test;

import junit.framework.TestCase;

public class MarkTopicAsFavoriteTest extends TestCase {
	
	Topics topic = new Topics();
	
	//adds topic to favorites;
	
	Favorite favorite = new Favorite();
	
	favorite.markAsFavorite(topic);
	
	
	//checks if topics has been added to favorites.
	
	assertTrue(favorite.getFavorites().equals(topic));

}
