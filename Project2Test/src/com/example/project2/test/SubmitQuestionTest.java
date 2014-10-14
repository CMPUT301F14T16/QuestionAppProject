package com.example.project2.test;

import android.test.ActivityInstrumentationTestCase2;
import java.security.Timestamp;
import java.util.Calendar;
import java.util.Date;
import junit.framework.TestCase;
import android.view.View;
import java.util.ArrayList;
import java.io.File;

public class SubmitQuestionTest {
	
	
	//Initialize the Question Input.
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
	String title = "What is this?";
	String text = "This is the answer.";
	int cid = 123;
	String filename = "lll.jpg";
	question.addDate(date);
	question.addAuthor(author);
	question.addTitle(title);
	question.addText(text);
	question.addCid(cid);
	question.addImage(filename);
	assertNotNull(question.getDate());
	assertNotNull(question.getAuthor());
	assertNotNull(question.getText());
	assertNotNull(question.getCid());
	assertNotNull(question.getImage());
	assertSame(question.getDate(),date);
	assertSame(question.getAuthor(),author);
	assertSame(question.getText().getTitle(),title);
	assertSame(question.getText().getBody(),text);
	assertSame(question.getCid(),cid);
	assertSame(question.getImage().getname(),filename);
	
	public void TestPending() {
		if (connection == 0) {
			assertTrue(setReconnect());
		}
		
		Pending pending = new Pending();
		
		pending.addQuestion(question);
		assertNotNull(pending.getQuestion(question));
		assertSame(question.getDate(),date);
		assertSame(question.getText().getTitle(),title);
		assertSame(question.getText().getBody(),textBody);
		assertSame(question.getFile().getname(),filename);
	}

}
