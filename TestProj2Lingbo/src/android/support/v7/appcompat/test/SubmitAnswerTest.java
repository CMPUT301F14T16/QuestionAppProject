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
	answer.addDate(date);
	assertNotNull(answer.getBody());
	assertNotNull(answer.getFile());
	assertSame(answer.getBody(),textBody);
	assertSame(answer.getFile().getname(),filename);
	
	
	public void TestCache() {
		if (connection == 0) {
			assertTrue(setReconnect());
		}
		
		Cache cache = new Cache();
		
		cache.addAnswer(answer);
		assertNotNull(Cache.getAnswer());
		assertSame(answer.getttitle(),textBody);
		assertSame(answer.getFile().getname(),filename);
	}

}
