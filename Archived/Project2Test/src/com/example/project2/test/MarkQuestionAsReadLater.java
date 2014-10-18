package com.example.project2.test;

import android.test.ActivityInstrumentationTestCase2;
import java.security.Timestamp;
import java.util.Calendar;
import java.util.Date;
import junit.framework.TestCase;
import android.view.View;
import java.util.ArrayList;
import java.io.File;

public class MarkQuestionAsReadLater {
	
	
	ReadLater rl = new ReadLater();
	
	Topics topics = new Topics();
	
	Question question  = new Question();
	
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
	question.addDate(date);
	question.addAuthor(author);
	question.addText(text);
	question.addCid(cid);
	
	rl.getquestion(question).markAsReadLater();
	assertTrue(rl.hasAsReadLater());
	assertSame(r1.getQuestion(question),question);
	
	
	
	public void TestPending() {
		if (connection == 0) {
			assertTrue(setReconnect());
		}
		Calender cal = Calender.getInstance();
		cal.set(Calendar.YEAR, 2014);
		cal.set(Calendar.MONTH, 10);
		cal.set(Calendar.DAY_OF_MONTH, 2);
		cal.set(Calendar.HOUR_OF_DAY, 2);
		cal.set(Calendar.MINUTE, 2);
		cal.set(Calendar.SECOND, 2);
		java.util.Date date = cal.getTime();
		String author = "Lingbo";
		String text = "This is the answer."
		int cid = 123;
		question.addDate(date);
		question.addAuthor(author);
		question.addText(text);
		question.addCid(cid);
		rl.getquestion(question).markAsReadLater();
		assertTrue(rl.hasAsReadLater());
		assertSame(r1.getQuestion(question),question);
	}
	
}
