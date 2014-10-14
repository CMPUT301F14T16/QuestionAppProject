package com.example.project2.test;

import junit.framework.TestCase;

public class UpVoteTopicsTest extends TestCase {
	
	
	Topics topic = new Topics();
	
	Answer answer = new Answers();
	
	topic.addAnswer(answer);
	
	answer.upVote();
	
	assertTrue(answer.getUpVotes() > 0);

}
