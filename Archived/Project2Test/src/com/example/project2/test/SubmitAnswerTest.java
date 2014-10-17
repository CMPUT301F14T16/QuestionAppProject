package com.example.project2.test;

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
	String text = "This is the answer.";
	int cid = 123;
	String filename = "lll.jpg";
	answer.addDate(date);
	answer.addAuthor(author);
	answer.addText(text);
	answer.addCid(cid);
	answer.addImage(filename);
	assertNotNull(answer.getText());
	assertNotNull(answer.getImage());
	assertSame(answer.getText(),textBody);
	assertSame(answer.getImage().getname(),filename);
	
	
	public void TestPending() {
		if (connection == 0) {
			assertTrue(setReconnect());
		}
		
		Pending pending = new Pending();
		
		Pending.addAnswer(answer);
		assertNotNull(Pending.getAnswer());
		assertSame(Pending.getAnswer().getText(),text);
		assertSame(Pending.getAnswer().getImage().getname(),filename);
	}

}
