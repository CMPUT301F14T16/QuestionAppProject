package com.example.project2.test;

import junit.framework.TestCase;

public class BrowseTopicPreviewsTest extends TestCase {
	//Topic Previews 
		//User will choose what topics he/she would like to browse if he has internet connectivity.
		//this is assuming topics is in the database. The user will select the topic he/she
		//would like to browse then the browseTopicPreview will retrieve that topic.
		assertTrue(browseTopicPreviews(topics.getName) == topics);
}
