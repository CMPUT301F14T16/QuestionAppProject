package android.support.v7.appcompat.test;

import android.test.ActivityInstrumentationTestCase2;
import java.security.Timestamp;
import java.util.Calendar;
import java.util.Date;
import junit.framework.TestCase;
import android.view.View;
import java.util.ArrayList;
import java.io.File;

public class SubmitAnswerTest {
	
	Answer answer = new Answer();
	Calender cal = Calender.getInstance();
	cal.set(Calendar.YEAR, 2014);
	cal.set(Calendar.MONTH, 10);
	cal.set(Calendar.DAY_OF_MONTH, 1);
	cal.set(Calendar.HOUR_OF_DAY, 1);
	cal.set(Calendar.MINUTE, 1);
	cal.set(Calendar.SECOND, 2);
	java.util.Date date = cal.getTime();
	String author = "Lingbo";
	String text = "This is the answer."
	int cid = 123;
	Content Reply = new Content();
	Reply.addDate(date);
	Reply.addAuthor(author);
	Reply.addText(text);
	Reply.addCid(cid);
	answer.addReply(reply);
	assertNotNull(answer.getReplies());
	assertSame(answer.getReplies(),reply);
	
	
	public void TestCache() {
		if (connection == 0) {
			assertTrue(setReconnect());
		}
		
		Cache cache = new Cache();
		
		cache.addAnswer(answer);
		assertNotNull(Cache.getAnswer());
		assertSame(answer.getReplies().getText(),text);
	}

}
