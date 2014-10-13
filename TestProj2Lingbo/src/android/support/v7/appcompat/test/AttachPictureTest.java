package android.support.v7.appcompat.test;

import android.test.ActivityInstrumentationTestCase2;
import java.security.Timestamp;
import java.util.Calendar;
import java.util.Date;
import junit.framework.TestCase;
import android.view.View;
import java.util.ArrayList;
import java.io.File;

public class AttachPictureTest {
	
	Question question = new Question();
	Answer answer = new Answer();
	String picname = "this.jpg";
	if (picname.getsizebykb()> 64) {
		assertNotNull(question.errorNotification());
	}
	question.addfile(picname);
	assertSame(question.getfile().getname(),picname);
	answer.addfile(picname);
	assertSame(answer.getfile().getname(),picname);
	
	
	
}
