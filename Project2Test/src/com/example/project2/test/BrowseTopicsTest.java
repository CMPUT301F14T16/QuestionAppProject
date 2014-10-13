package com.example.project2.test;

import java.util.ArrayList;

import android.view.View;
import junit.framework.TestCase;

public class BrowseTopicsTest extends TestCase {
	//use one topic for the test. this topic will be gotten from a list of topics.
	Topics topics =  new Topics();
	Question question = new Question();
	Answer answer = new Answer();
	topics.addQuestion("Test Question");
	topics.addAnswer("Test Answer");
	
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
	
	assertTrue(topics.getAll() == "Test Question" + "Test Answer" + 
			question);
	
	//View Favorites
	assertTrue(favorites.viewAll() == topics);
	
	//View SavedQuestion
	
	assertTrue(savedQuestions.viewAll() == topics);
	
	//Topic Previews 
	//User will choose what topics he/she would like to browse if he has internet connectivity.
	//this is assuming topics is in the database. The user will select the topic he/she
	//would like to browse then the browseTopicPreview will retrieve that topic.
	assertTrue(browseTopicPreviews(topics.getName) == topics);
	
	
	
	
}
