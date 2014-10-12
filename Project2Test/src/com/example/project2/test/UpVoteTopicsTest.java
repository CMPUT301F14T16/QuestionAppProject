package com.example.project2.test;

import junit.framework.TestCase;

public class UpVoteTopicsTest extends TestCase {
	
	
	Topics topic = new Topics();
	
	topic.upVote();
	
	assertTrue(topic.getUpVotes > 0);

}
