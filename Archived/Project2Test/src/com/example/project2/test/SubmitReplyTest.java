package com.example.project2.test;

import android.test.ActivityInstrumentationTestCase2;
import java.security.Timestamp;
import java.util.Calendar;
import java.util.Date;
import junit.framework.TestCase;
import android.view.View;
import java.util.ArrayList;
import java.io.File;

public class SubmitReplyTest extends TestCase {
	
	public void testSubmitReply() {
		//Initialize the Question Input.
		int i = 0;
		int q_id;
		MainModel mainmodel = new MainModel();
		mainModel.setUserName("123@sample.com","userName");
		mainmodel.addQuestion("title","what is this?");
		q_id = mainmodel.get(i).getQuestionID();
		mainmodel.getQuestionByID(q_id).addReply("This is dog");
		assertTrue(mainmodel.getAllQuestion().getAllReplies().size()==1);
		
	
		/*Calender cal = Calender.getInstance();
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
		//Assert the question is not empty at first
		//To test if we can parse them.
		
		assertNotNull(question.getDate());
		assertNotNull(question.getAuthor());
		assertNotNull(question.getText());
		assertNotNull(question.getCid());
		assertNotNull(question.getImage());*/
		
		//Assert if we can parse the correct value in the 
		//question to submit
		/*assertSame(question.getDate(),date);
		assertSame(question.getAuthor(),author);
		assertSame(question.getText().getTitle(),title);
		assertSame(question.getText().getBody(),text);
		assertSame(question.getCid(),cid);
		assertSame(question.getImage().getname(),filename);*/
	}
	
	//Pending is cache like container for the edited but unsubmitted content
	//Once we have the connection, we will push up content to the view and update
	//I have no idea what to do with the Android Cache
	//So right now it's just a pseudocode like test.
	//We come out the idea to use the quesiton_id,answer_id and reply_id to distinct
	//the content, but since we are still discussing that, we just make it abstract in
	//our test code.
	//If there is anything already inside the list
	public void TestPending() {
		MainModel mainModel= new MainModel();
		mainModel.setUserName("123@sample.com","userName");
		int i = 0;
		int q_id;
		try{
			mainModel.setUser("123@sample.com","userName");
			mainmodel.addQuestion("title","what is this?");
			q_id = mainmodel.get(i).getQuestionID();
			mainmodel.getQuestionByID(q_id).addReply("This is dog");
		}
		catch (Exception e){
			mainModel.addPending("title","this is a question");
			q_id = mainModel.get(i).getQid();
			mainmodel.getQuestionByID(q_id).addReply("This is dog");
		}
		Boolean connect=false;
		while !connect{
			try{
				mainModel.pushPending();
			}
			else{
				connect=true;
			}
		}
		assertEqual(Mainmodel.haspending(), false);
		
	}

}
