package com.example.project2.test;

import android.test.ActivityInstrumentationTestCase2;
import java.security.Timestamp;
import java.util.Calendar;
import java.util.Date;
import junit.framework.TestCase;
import android.view.View;
import java.util.ArrayList;
import java.io.File;

public class AttachPictureTest extends TestCase {
	
	Question question = new Question();
	Answer answer = new Answer();
	String picname = "this.jpg";
	if (picname.getsizebykb()> 64) {
		assertNotNull(question.errorNotification());
	}
	question.addImage(picname);
	assertSame(question.getImage().getname(),picname);
	answer.addImage(picname);
	assertSame(answer.getImage().getname(),picname);
	
	
	
}
