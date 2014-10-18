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

	public void testQuestionAttach() {
		int i = 0;
		int q_id;
	
	
		try{
			MainModel mainmodel = new MainModel();
	
		
			mainModel.setUserName("123@sample.com","userName");
			mainmodel.addQuestion("title","what is this?");
			String picname = "this.jpg";
			q_id= mainmodel.get(i).getQuestionID();
			mainmodel.getQuestionByID(q_id).addImage(picname);
		}
		catch (Exception e){
			mainModel.addPending("title","this is a question");
		}
		while (mainmodel.getQuestionByID(q_id).getImage().getsizebykb()> 64) {
			try{
				mainModel.pushPending();
			}
			else{
				mainmodel.getQuestionByID(q_id).addImage(picname);
			}
		}
		assertNotNull(mainmodel.getQuestionByID(q_id).getImage());
	}	
	/*question.addImage(picname);
	assertSame(question.getImage().getname(),picname);
	answer.addImage(picname);
	assertSame(answer.getImage().getname(),picname);*/
	
	public void testAnswserAttach() {
		int i = 0;
		int q_id;
		int a_id;
	
		try{
			MainModel mainmodel = new MainModel();
	
		
			mainModel.setUserName("123@sample.com","userName");
			mainmodel.addQuestion("title","what is this?");
			String picname = "this.jpg";
			q_id= mainmodel.get(i).getQuestionID();
			mainmodel.addAnswer(q_id,"This is","Lingbo");
			a_id = mainmodel.get(i).getQuestionByID(q_id).getAnswerID();
			mainmodel.getQuestionByID(q_id).getAnswerByID(a_id).addImage(picname);
		}
		catch (Exception e){
			mainModel.addPending("title","this is a question");
		}
		while (mainmodel.getQuestionByID(q_id).getAnswerByID(a_id)getImage().getsizebykb()> 64) {
			try{
				mainModel.pushPending();
			}
			else{
				mainmodel.getQuestionByID(q_id).getAnswerByID(a_id).addImage(picname);
			}
		}
		assertNotNull(mainmodel.getQuestionByID(q_id).getAnswerByID(a_id).getImage());
	}	
	
}
