package ca.ualberta.cmput301f14t16.easya;

import java.util.ArrayList;
import java.util.Date;

public class Question {
	
	ArrayList<Answer> answers;
	int answerCount;
	
	//I will add in the User as one of the parameters when it is created.
	public Question(String title, String body, Date date) {
		
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
