package ca.ualberta.cmput301f14t16.easya.Model.Data;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import ca.ualberta.cmput301f14t16.easya.Exceptions.NoContentAvailableException;
import ca.ualberta.cmput301f14t16.easya.Model.Question;
import ca.ualberta.cmput301f14t16.easya.Model.QuestionList;
import ca.ualberta.cmput301f14t16.easya.Model.User;
import ca.ualberta.cmput301f14t16.easya.View.MainActivity;
import android.content.Context;

/**
 * Responsible for determining where to retrieve a content from,
 * and for maintaining a physical copy of a content, for easiness and speed 
 * @author Cauani
 *
 */
public class Cache {	
	public static List<QuestionList> getQuestionList(Context ctx){
		 if (MainActivity.mQueueThread.haveInternetConnection()){
			 //TODO: return questionslist from the ESClient
			 return new ArrayList<QuestionList>();
		 }else{
			 return getQuestionListFromQuestionsCache(ctx);				
		 }
	}
	
	public static List<QuestionList> getUserQuestions(Context ctx, User u){
		if (MainActivity.mQueueThread.haveInternetConnection()){
			//TODO: return all questionslist from the ESClient,
			// then loop thru them and only return the ones that matches
			// the user
			 return new ArrayList<QuestionList>();
		 }else{
			 return getQuestionListFromQuestionsCache(ctx);				
		 }
	}
	
	public static Question getQuestionById(Context ctx, String id) throws NoContentAvailableException{
		if (MainActivity.mQueueThread.haveInternetConnection()){
			//TODO: get it from ESClient
			// Save the returned object in cache
			// SaveSingleQuestion(ctx, q);
			 return new Question();
		 }else{
			 return getQuestionFromCache(ctx, id);				
		 }
	}
	
	public static User getUserById(Context ctx, String id) throws NoContentAvailableException{
		if (MainActivity.mQueueThread.haveInternetConnection()){
			//TODO: get it from ESClient
			// Save the returned object in cache
			// SaveSingleUser(ctx, u);
			 return new User();
		 }else{
			 return getUserFromCache(ctx, id);
		 }
	}
		
	public static void SaveSingleQuestion(Context ctx, Question q){
		Gson gson = new Gson();
		Type listType = new TypeToken<List<Question>>(){}.getType();
		List<Question> aux = gson.fromJson(PMDataParser.loadJson(ctx, PMFilesEnum.CACHEQUESTIONS), listType);
		if (aux.contains(q)){
			int i = aux.indexOf(q);
			aux.remove(i);
			aux.add(i, q);
		}else{
			aux.add(0,q);
		}
		PMDataParser.saveJson(ctx, PMFilesEnum.CACHEQUESTIONS, gson.toJson(aux));
		
		//TODO: call MM and tell it to update
	}

	/**
	 * Use this when the user changes it's own name
	 * @param ctx
	 * @param u
	 */
	public static void SaveSingleUser(Context ctx, User u){
		Gson gson = new Gson();
		Type listType = new TypeToken<List<User>>(){}.getType();
		List<User> aux = gson.fromJson(PMDataParser.loadJson(ctx, PMFilesEnum.CACHEUSERS), listType);
		if (aux.contains(u)){
			int i = aux.indexOf(u);
			aux.remove(i);
			aux.add(i, u);
		}else{
			aux.add(0,u);
		}
		PMDataParser.saveJson(ctx, PMFilesEnum.CACHEUSERS, gson.toJson(aux));
		
		//TODO: call MM and tell it to update
	}
	
	private static void UpdateUsers(Context ctx, List<User> lst){
		Gson gson = new Gson();
		PMDataParser.saveJson(ctx, PMFilesEnum.CACHEUSERS, gson.toJson(lst));
		
		//TODO: call MM and tell it to update
	}
		
	private static List<QuestionList> getUserQuestionsFromQuestionsCache(Context ctx, User u){
		Gson gson = new Gson();
		Type listType = new TypeToken<List<Question>>(){}.getType();
		List<Question> aux = gson.fromJson(PMDataParser.loadJson(ctx, PMFilesEnum.CACHEQUESTIONS), listType);
		List<QuestionList> lst = new ArrayList<QuestionList>();
		for (Question q : aux){
			if (q.getAuthorId().equals(u.getId()))
				lst.add(new QuestionList(q.getId(), q.getTitle(), q.getAuthorName(), q.getAnswerCount(), q.getUpVoteCount(), q.hasPicture()));
		}
		return lst;
	}
	
	private static List<QuestionList> getQuestionListFromQuestionsCache(Context ctx){
		Gson gson = new Gson();
		Type listType = new TypeToken<List<Question>>(){}.getType();
		List<Question> aux = gson.fromJson(PMDataParser.loadJson(ctx, PMFilesEnum.CACHEQUESTIONS), listType);
		List<QuestionList> lst = new ArrayList<QuestionList>();
		for (Question q : aux){
			lst.add(new QuestionList(q.getId(), q.getTitle(), q.getAuthorName(), q.getAnswerCount(), q.getUpVoteCount(), q.hasPicture()));
		}
		return lst;
	}
	
	private static List<User> getUserListFromCache(Context ctx){
		Gson gson = new Gson();
		Type listType = new TypeToken<List<User>>(){}.getType();
		return gson.fromJson(PMDataParser.loadJson(ctx, PMFilesEnum.CACHEUSERS), listType);		
	}	
	
	private static Question getQuestionFromCache(Context ctx, String id) throws NoContentAvailableException{
		Gson gson = new Gson();
		Type listType = new TypeToken<List<Question>>(){}.getType();
		List<Question> lst = gson.fromJson(PMDataParser.loadJson(ctx, PMFilesEnum.CACHEQUESTIONS), listType);
		for (Question q : lst){
			if (q.getId().equals(id))
			return q;
		}
		throw new NoContentAvailableException();
	}
	
	private static User getUserFromCache(Context ctx, String id) throws NoContentAvailableException{
		Gson gson = new Gson();
		Type listType = new TypeToken<List<User>>(){}.getType();
		List<User> lst = gson.fromJson(PMDataParser.loadJson(ctx, PMFilesEnum.CACHEUSERS), listType);
		for (User u : lst){
			if (u.getId().equals(id))
			return u;
		}
		throw new NoContentAvailableException();
	}	
}
