package com.example.project2.test;

import java.util.ArrayList;

import android.view.View;
import junit.framework.TestCase;

public class BrowseTopicsTest extends TestCase {
	
	Topics topics =  new Topics();
	Question question = topics.addAnswer("Test Question");
	Answer answer = topics.addAnswer("Test Answer");
	
	//Assuming they are all strings adds replies and submits a question
	Question.addReply("Test ReplyQ");
	Answer.addReply("Test ReplyA");
	topics.submitQuestion(question);
	//adds the topic to an array of favorites
	topics.addFavorite();
	//adds the question of the topic to an array of saved questions
	topics.saveQuestion(question);
	//checks if the retrieved are the correct ones 
	//that the user requested. 
	assertTrue(topics.getAllQuestions() == "Test Question");
	assertTrue(topics.getAllAnswers() == "Test Answer");
	assertTrue(question.getAllReplies() == "Test ReplyQ");
	assertTrue(answer.getAllReplies() == "Test ReplyA");
	assertTrue(topics.getAllAnswers() == "Test Answer");
	assertTrue(topics.getAllSubmittedQuestions() == question);
	assertTrue(Answer.getAllSavedQuestions() == question);

	//View topic and topic previews this includes favorites, saved, 
	
	assertTrue(topics.viewAll() == "Test Question" + "Test Answer" + 
			question + "Test Submit");
	
	//View Favorites
	assertTrue(favorites.viewAll() == topics);
	
	//View SavedQuestion
	
	assertTrue(savedQuestions.viewAll() == topics);
	
	//Topic Previews will be here
}
