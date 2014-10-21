package ca.ualberta.cmput301f14t16.easya;

import java.util.ArrayList;
import java.util.Date;

public class Question {
	
	ArrayList<Answer> answers;
	int answerCount;
	
	public Question(String title, String body, Date date, User user) {
		
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
