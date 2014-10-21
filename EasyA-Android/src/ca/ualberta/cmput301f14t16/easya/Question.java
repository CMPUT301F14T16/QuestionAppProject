package ca.ualberta.cmput301f14t16.easya;

import java.util.ArrayList;
import java.util.Date;
import java.util.UUID;

public class Question extends Topic {
	
	ArrayList<Answer> answers;
	int answerCount;
	String title;
	
	public Question(String title, String body, Date date, String authorEmail) {
		super(body, date, authorEmail);
		this.title = title;
	}
	
	public int getQuestionID() {
		return 0;
		
	}
	
	public void setQuestionID() {
		
	}
	
	public ArrayList<Answer> getAllAnswer() {
		return answers;
	}
	
	public Answer getAnswerByID(int ID) {
		return answers.get(0);
	}

}
