package android.support.v7.appcompat.test;

import junit.framework.TestCase;

public class MarkQuestionAsReadLater {
	
	
	Topics rl = new Topics();
	
	Topics topics = new Topics();
	
	Question question  = topics.addQuestion("What is this?");
	rl.getquestion(question).markAsReadLater();
	assertTrue(rl.hasAsReadLater() == question);
	
	Question question2 = topics.addQuestion("What is this?");
	assertTrue(r1.getquestion(question).makAsReadsLater() == question);
	
	
	public void TestRLCache() {
		if (connection == 0) {
			assertTrue(setReconnect());
		}
		Question newquestion = topics.addQuestion("What is that?");
		r1.markAsReadLater();
		assertTrue(r1.hasAsReadLater().get(3) == newquestion);
	}
	
}
