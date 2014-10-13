package android.support.v7.appcompat.test;

import android.test.ActivityInstrumentationTestCase2;
import java.security.Timestamp;
import java.util.Calendar;
import java.util.Date;
import junit.framework.TestCase;
import android.view.View;
import java.util.ArrayList;
import java.io.File;

public class SubmitQuestionTest {
	
	Question question = new Question();
	String title = "what is this?";
	question.addtitle(title);
	String textBody = "I have a question for this.";
	question.addBody(textBody);
	String filename = "kkk.jpg";
	question.addFile(filename);
	assertNotNull(question.gettitle());
	assertNotNull(question.getBody());
	assertNotNull(question.getFile());
	assertSame(answer.gettitle(),title);
	assertSame(answer.getBody(),textBody);
	assertSame(answer.getFile().getname(),filename);
	
	public void TestCache() {
		if (connection == 0) {
			assertTrue(setReconnect());
		}
		
		Cache cache = new Cache();
		
		cache.addQuestion(question);
		assertNotNull(Cache.getQuestion(question));
		assertSame(answer.gettitle(),title);
		assertSame(answer.getBody(),textBody);
		assertSame(answer.getFile().getname(),filename);
	}

}
