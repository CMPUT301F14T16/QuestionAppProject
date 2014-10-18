package com.example.project2.test;

import java.util.ArrayList;

import android.view.View;
import junit.framework.TestCase;


public class TestForProject extends TestCase{

	public void testUpVoteQuestion(){
		MainModel mainModel=new MainModel();
		mainModel.setUserName("1234@example.email","theUser");
		MainModel.addQuestion("title","This is a question");
		int index=0;
		mainModel.getQuestionList().get(index).upVote();
		assertTrue(mainModel.getAllQuestionList().get(index).getUpVote()==1);
	}
	public void testUpVoteAnswer(){
		MainModel mainModel=new MainModel();
		mainModel.setUserName("1234@example.email","theUser");
		MainModel.addQuestion("title","This is a question");
		int index=0;
		int index2=0;
		MainModel.addQuestion("title","This is a question");
		int ID;
		ID=mainModel.getQuestionList().get(index).getID();
		mainModel.addAnswer(ID,"title","This is a question");
		assertTrue(mainModel.getAllQuestionList().get(index).getAnswer(index2).getUpVote()==1);
	}
	public void testSave(){
		MainModel mainModel=new MainModel();
		mainModel.setUserName("1234@example.email","theUser");
		MainModel.addQuestion("title","This is a question");
		int index=0;
		mainModel.getQuestionList().get(index).addFavourite();
		assertTrue(mainModel.getAllSavedQuestion().size()==1);
	}
	
}


