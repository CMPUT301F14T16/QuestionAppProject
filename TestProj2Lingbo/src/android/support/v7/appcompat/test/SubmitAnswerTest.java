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
	String textBody = "I have an Answer for this.";
	answer.addBody(textBody);
	String filename = "kkk.jpg";
	answer.addFile(filename);
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