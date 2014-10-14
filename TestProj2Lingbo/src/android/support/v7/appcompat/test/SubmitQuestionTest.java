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
	
	Calender cal = Calender.getInstance();
	cal.set(Calendar.YEAR, 2014);
	cal.set(Calendar.MONTH, 10);
	cal.set(Calendar.DAY_OF_MONTH, 1);
	cal.set(Calendar.HOUR_OF_DAY, 1);
	cal.set(Calendar.MINUTE, 1);
	cal.set(Calendar.SECOND, 2);
	java.util.Date date = cal.getTime();
	String author = "Lingbo";
	String text = "This is the answer.";
	int cid = 123;
	String filename = "lll.jpg";
	question.addDate(date);
	question.addAuthor(author);
	question.addText(text);
	question.addCid(cid);
	question.addImage(filename);
	assertNotNull(question.getText());
	assertNotNull(question.getImage());
	assertSame(question.getText(),textBody);
	assertSame(question.getImage().getname(),filename);
	
	String title = "what is this?";
	question.addtitle(title);
	String textBody = "I have a question for this.";
	question.addBody(textBody);
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
