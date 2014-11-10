package ca.ualberta.cmput301f14t16.easya.Model.Data;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import ca.ualberta.cmput301f14t16.easya.Exceptions.NoContentAvailableException;
import ca.ualberta.cmput301f14t16.easya.Exceptions.NoInternetException;
import ca.ualberta.cmput301f14t16.easya.Model.Question;
import ca.ualberta.cmput301f14t16.easya.Model.QuestionList;
import ca.ualberta.cmput301f14t16.easya.Model.Queue;
import ca.ualberta.cmput301f14t16.easya.Model.User;

/**
 * Responsible for determining where to retrieve a content from,
 * and for maintaining a physical copy of a content, for easiness and speed 
 * @author Cauani
 *
 */
public class Cache{	
	private static Cache cache;
	
	protected Cache(){
	}
	
	public static Cache getInstance(){
		if (cache == null)
			cache = new Cache();
		return cache;
	}
	
	public void wipeCache(){
		PMDataParser.saveJson(PMFilesEnum.CACHEQUESTIONS, "");
		PMDataParser.saveJson(PMFilesEnum.CACHEUSERS, "");		
	}
	
	public List<QuestionList> getUserQuestions(User u){
		if (Queue.getInstance().haveInternetConnection()){
			try{
				ESClient es = new ESClient();
				List<QuestionList> lst = es.searchQuestionListsByQuery("*", 100);
				List<QuestionList> lstUser = new ArrayList<QuestionList>();
				if (lst == null)
					lst = new ArrayList<QuestionList>();
				for (QuestionList q : lst){
					if (q.getUsername().equals(u.getUsername()))
						lstUser.add(q);
				}
				return lstUser;
			}catch(IOException ex){
				return getUserQuestionsFromQuestionsCache(u);
			}
		}else{
			return getUserQuestionsFromQuestionsCache(u);				
		}
	}
	
	public Question getQuestionById(String id) throws NoContentAvailableException{
		if (Queue.getInstance().haveInternetConnection()){
			 try{
				 ESClient es = new ESClient();			 
				 Question aux = es.getQuestionById(id);
				 SaveSingleQuestion(aux);
				 return aux;
			 }catch(IOException ex){
				 return getQuestionFromCache(id);
			 }
		 }else{
			 return getQuestionFromCache(id);				
		 }
	}
	
	public User getUserById(String id) throws NoContentAvailableException{
		try{
			return getUserFromCache(id);
		}catch(NoContentAvailableException ex){
			if (Queue.getInstance().haveInternetConnection()){
				try{
					ESClient es = new ESClient();
					User u = es.getUserById(id);
					if (u == null)
						throw new NoContentAvailableException();
					SaveSingleUser(u);
					return u;
				}catch(IOException exx){
					throw new NoContentAvailableException();
				}
			 }else{
				 throw new NoContentAvailableException();
			 }
		}
	}
		
	public void SaveSingleQuestion(Question q){
		if (q==null)
			return;
		Gson gson = new Gson();
		Type listType = new TypeToken<List<Question>>(){}.getType();
		List<Question> aux = gson.fromJson(PMDataParser.loadJson(PMFilesEnum.CACHEQUESTIONS), listType);
		if (aux == null)
			aux = new ArrayList<Question>();
		if (aux.contains(q)){
			int i = aux.indexOf(q);
			aux.remove(i);
			aux.add(i, q);
		}else{
			aux.add(0,q);
		}
		PMDataParser.saveJson(PMFilesEnum.CACHEQUESTIONS, gson.toJson(aux));
		
		//MainActivity.mm.notifyViews();
	}

	/**
	 * Use this when the user changes it's own name
	 * @param ctx
	 * @param u
	 */
	public void SaveSingleUser(User u){
		Gson gson = new Gson();
		Type listType = new TypeToken<List<User>>(){}.getType();
		List<User> aux = gson.fromJson(PMDataParser.loadJson(PMFilesEnum.CACHEUSERS), listType);
		if (aux == null)
			aux = new ArrayList<User>();
		if (aux.contains(u)){
			int i = aux.indexOf(u);
			aux.remove(i);
			aux.add(i, u);
		}else{
			aux.add(0,u);
		}
		PMDataParser.saveJson(PMFilesEnum.CACHEUSERS, gson.toJson(aux));
		
		//MainActivity.mm.notifyViews();
	}
	
	private void UpdateUsers(List<User> lst){
		Gson gson = new Gson();
		PMDataParser.saveJson(PMFilesEnum.CACHEUSERS, gson.toJson(lst));
		
		//MainActivity.mm.notifyViews();
	}
		
	private List<QuestionList> getUserQuestionsFromQuestionsCache(User u){
		Gson gson = new Gson();
		Type listType = new TypeToken<List<Question>>(){}.getType();
		List<Question> aux = gson.fromJson(PMDataParser.loadJson(PMFilesEnum.CACHEQUESTIONS), listType);
		if (aux == null)
			aux = new ArrayList<Question>();
		List<QuestionList> lst = new ArrayList<QuestionList>();
		for (Question q : aux){
			if (q.getAuthorId().equals(u.getId()))
				lst.add(new QuestionList(q.getId(), q.getTitle(), q.getAuthorName(), q.getAnswerCountString(), q.getUpVoteCountString(), q.hasPicture(), q.getDate()));
		}
		return lst;
	}
	
	private List<QuestionList> getQuestionListFromQuestionsCache() throws NoContentAvailableException{
		Gson gson = new Gson();
		Type listType = new TypeToken<List<Question>>(){}.getType();
		List<Question> aux = gson.fromJson(PMDataParser.loadJson(PMFilesEnum.CACHEQUESTIONS), listType);
		if (aux == null)
			aux = new ArrayList<Question>();
		List<QuestionList> lst = new ArrayList<QuestionList>();
		for (Question q : aux){
			lst.add(new QuestionList(q.getId(), q.getTitle(), q.getAuthorName(), q.getAnswerCountString(), q.getUpVoteCountString(), q.hasPicture(), q.getDate()));
		}
		if (lst.size() <= 0)
			throw new NoContentAvailableException();
		return lst;
	}
		
	private Question getQuestionFromCache(String id) throws NoContentAvailableException{
		Gson gson = new Gson();
		Type listType = new TypeToken<List<Question>>(){}.getType();
		List<Question> lst = gson.fromJson(PMDataParser.loadJson(PMFilesEnum.CACHEQUESTIONS), listType);
		if (lst == null)
			throw new NoContentAvailableException();
		for (Question q : lst){
			if (q.getId().equals(id))
			return q;
		}
		throw new NoContentAvailableException();
	}
	
	private User getUserFromCache(String id) throws NoContentAvailableException{
		Gson gson = new Gson();
		Type listType = new TypeToken<List<User>>(){}.getType();
		List<User> lst = gson.fromJson(PMDataParser.loadJson(PMFilesEnum.CACHEUSERS), listType);
		if (lst == null)
			lst = new ArrayList<User>();
		for (User u : lst){
			if (u.getId().equals(id))
			return u;
		}
		throw new NoContentAvailableException();
	}

	public void updateAllUsers(){
		if (Queue.getInstance().haveInternetConnection()){
			ESClient es = new ESClient();
			try{
				UpdateUsers(es.searchUsersByQuery("*", 100));			
			}catch(IOException ex){
				return;
			}	
		 }else{
			 return;				
		 }
	}
	
	public List<QuestionList> getAllQuestions() throws NoContentAvailableException {
		if (Queue.getInstance().haveInternetConnection()){
			ESClient es = new ESClient();
			try{
				updateAllUsers();
				return es.searchQuestionListsByQuery("*", 100);			
			}catch(IOException ex){
				return getQuestionListFromQuestionsCache();
			}	
		 }else{
			 return getQuestionListFromQuestionsCache();				
		 }
	}

	public User getUserByEmail(String email) throws NoInternetException, NoContentAvailableException {
		if (Queue.getInstance().haveInternetConnection()){
			try{
				ESClient es = new ESClient();
				String userId = es.getUserIdByEmail(email);
				if (userId == null)
					throw new NoContentAvailableException();
				User u = es.getUserById(userId);
				if (u == null)
					throw new NoContentAvailableException();
				SaveSingleUser(u);
				return u;
			}catch(IOException ex){
				throw new NoContentAvailableException();
			}
		 }else{
			 throw new NoInternetException();
		 }
	}
}
