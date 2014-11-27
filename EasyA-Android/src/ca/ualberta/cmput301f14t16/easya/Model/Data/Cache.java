package ca.ualberta.cmput301f14t16.easya.Model.Data;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import ca.ualberta.cmput301f14t16.easya.Exceptions.NoContentAvailableException;
import ca.ualberta.cmput301f14t16.easya.Exceptions.NoInternetException;
import ca.ualberta.cmput301f14t16.easya.Model.GeneralHelper;
import ca.ualberta.cmput301f14t16.easya.Model.InternetCheck;
import ca.ualberta.cmput301f14t16.easya.Model.MainModel;
import ca.ualberta.cmput301f14t16.easya.Model.Question;
import ca.ualberta.cmput301f14t16.easya.Model.QuestionList;
import ca.ualberta.cmput301f14t16.easya.Model.Queue;
import ca.ualberta.cmput301f14t16.easya.Model.User;

/**
 * Responsible for determining where to retrieve a content from, and for
 * maintaining a physical copy of a content, for easiness and speed
 * 
 * @author Cauani
 *
 */
public class Cache {
	private static Cache cache;

	protected Cache() {
	}

	public static Cache getInstance() {
		if (cache == null)
			cache = new Cache();
		return cache;
	}

	public void wipeCache() {
		PMDataParser.saveJson(PMFilesEnum.CACHEQUESTIONS, "");
		PMDataParser.saveJson(PMFilesEnum.CACHEUSERS, "");
		PMDataParser.saveJson(PMFilesEnum.CACHEQUESTIONSLIST, "");
	}

	public List<QuestionList> getAllUserQuestions()
			throws NoContentAvailableException {
		return getUserQuestionsFromCache(MainModel.getInstance()
				.getCurrentUser());
	}

	public Question getQuestionById(String id)
			throws NoContentAvailableException {
		if (InternetCheck.haveInternet()) {
			try {
				ESClient es = new ESClient();
				Question aux = es.getQuestionById(id);
				SaveSingleQuestion(aux);
				return aux;
			} catch (IOException ex) {
				return getQuestionFromCache(id);
			}
		} else {
			return getQuestionFromCache(id);
		}
	}

	public User getUserById(String id) throws NoContentAvailableException {
		try {
			return getUserFromCache(id);
		} catch (NoContentAvailableException ex) {
			if (InternetCheck.haveInternet()) {
				try {
					ESClient es = new ESClient();
					User u = es.getUserById(id);
					if (u == null)
						throw new NoContentAvailableException();
					SaveSingleUser(u);
					return u;
				} catch (IOException exx) {
					throw new NoContentAvailableException();
				}
			} else {
				throw new NoContentAvailableException();
			}
		} catch (Exception ex) {
			// Catch breach of security access (internet on main queue)
			throw new NoContentAvailableException();
		}
	}

	public void SaveSingleQuestion(Question q) {
		if (q == null)
			return;
		Gson gson = new Gson();
		Type listType = new TypeToken<List<Question>>() {
		}.getType();
		List<Question> aux = gson.fromJson(
				PMDataParser.loadJson(PMFilesEnum.CACHEQUESTIONS), listType);
		if (aux == null)
			aux = new ArrayList<Question>();
		if (aux.contains(q)) {
			int i = aux.indexOf(q);
			aux.remove(i);
			aux.add(i, q);
		} else {
			aux.add(0, q);
		}
		PMDataParser.saveJson(PMFilesEnum.CACHEQUESTIONS, gson.toJson(aux));
	}

	/**
	 * Use this when the user changes it's own name
	 * 
	 * @param u
	 */
	public void SaveSingleUser(User u) {
		Gson gson = new Gson();
		Type listType = new TypeToken<List<User>>() {
		}.getType();
		List<User> aux = gson.fromJson(
				PMDataParser.loadJson(PMFilesEnum.CACHEUSERS), listType);
		if (aux == null)
			aux = new ArrayList<User>();
		if (aux.contains(u)) {
			int i = aux.indexOf(u);
			aux.remove(i);
			aux.add(i, u);
		} else {
			aux.add(0, u);
		}
		PMDataParser.saveJson(PMFilesEnum.CACHEUSERS, gson.toJson(aux));
	}

	private void UpdateQuestionList(List<QuestionList> lst) {
		Gson gson = new Gson();
		PMDataParser.saveJson(PMFilesEnum.CACHEQUESTIONSLIST, gson.toJson(lst));
	}

	private void UpdateUsers(List<User> lst) {
		Gson gson = new Gson();
		PMDataParser.saveJson(PMFilesEnum.CACHEUSERS, gson.toJson(lst));
	}

	private List<QuestionList> getUserQuestionsFromCache(User u)
			throws NoContentAvailableException {
		Gson gson = new Gson();
		Type listType = new TypeToken<List<QuestionList>>() {
		}.getType();
		List<QuestionList> aux = gson
				.fromJson(
						PMDataParser.loadJson(PMFilesEnum.CACHEQUESTIONSLIST),
						listType);
		if (aux == null || aux.size() <= 0)
			throw new NoContentAvailableException();
		List<QuestionList> lst = new ArrayList<QuestionList>();
		for (QuestionList q : aux) {
			if (q.getAuthorId().equals(u.getId()))
				lst.add(q);
		}
		return lst;
	}

	public List<QuestionList> getQuestionListFromQuestionsCache()
			throws NoContentAvailableException {
		Gson gson = new Gson();
		Type listType = new TypeToken<List<QuestionList>>() {
		}.getType();
		List<QuestionList> aux = gson
				.fromJson(
						PMDataParser.loadJson(PMFilesEnum.CACHEQUESTIONSLIST),
						listType);
		if (aux == null || aux.size() <= 0)
			throw new NoContentAvailableException();
		return aux;
	}

	public List<Question> getSavedQuestions() throws NoContentAvailableException{
		Gson gson = new Gson();
		Type listType = new TypeToken<List<Question>>() {
		}.getType();
		List<Question> aux = gson.fromJson(
				PMDataParser.loadJson(PMFilesEnum.CACHEQUESTIONS), listType);
		if (aux == null || aux.size() <= 0)
			throw new NoContentAvailableException();
		return aux;
	}
	
	public List<QuestionList> getSavedQuestionsList()
			throws NoContentAvailableException {
		List<Question> aux = getSavedQuestions();		
		return GeneralHelper.lqToQuestionlist(aux);
	}

	private Question getQuestionFromCache(String id)
			throws NoContentAvailableException {
		Gson gson = new Gson();
		Type listType = new TypeToken<List<Question>>() {
		}.getType();
		List<Question> lst = gson.fromJson(
				PMDataParser.loadJson(PMFilesEnum.CACHEQUESTIONS), listType);
		if (lst == null)
			throw new NoContentAvailableException();
		for (Question q : lst) {
			if (q.getId().equals(id))
				return q;
		}
		throw new NoContentAvailableException();
	}

	private User getUserFromCache(String id) throws NoContentAvailableException {
		Gson gson = new Gson();
		Type listType = new TypeToken<List<User>>() {
		}.getType();
		List<User> lst = gson.fromJson(
				PMDataParser.loadJson(PMFilesEnum.CACHEUSERS), listType);
		if (lst == null)
			lst = new ArrayList<User>();
		for (User u : lst) {
			if (u.getId().equals(id))
				return u;
		}
		throw new NoContentAvailableException();
	}
	
	public List<User> getUsersListFromCache(){
		Gson gson = new Gson();
		Type listType = new TypeToken<List<User>>() {
		}.getType();
		List<User> lst = gson.fromJson(
				PMDataParser.loadJson(PMFilesEnum.CACHEUSERS), listType);
		if (lst == null)
			lst = new ArrayList<User>();		
		return lst;
	}

	public void updateAllUsers() {
		if (InternetCheck.haveInternet()) {
			ESClient es = new ESClient();
			try {
				UpdateUsers(es.searchUsersByQuery("*", 100));
			} catch (IOException ex) {
				return;
			}
		} else {
			return;
		}
	}

	public List<QuestionList> getAllQuestions()
			throws NoContentAvailableException {
		if (InternetCheck.haveInternet()) {
			ESClient es = new ESClient();
			try {
				System.out.println("I'm in da house! CACHE PEACE LOVE BABY YEAH");
				updateAllUsers();
				List<QuestionList> aux = es
						.searchQuestionListsByQuery("*", 100);
				System.out.println("Qty of questions: " + aux.size());
				UpdateQuestionList(aux);
				return aux;
			} catch (IOException ex) {
				System.out.println("CACHE HAS ERROR! ARGH!");
				throw new NoContentAvailableException();
			}
		} else {
			System.out.println("CACHE HAS ERROR! DEEP ERROR! ARGH!");
			throw new NoContentAvailableException();
		}
	}

	public User getUserByEmail(String email) throws NoInternetException,
			NoContentAvailableException {
		if (InternetCheck.haveInternet()) {
			try {
				ESClient es = new ESClient();
				String userId = es.getUserIdByEmail(email);
				if (userId == null)
					throw new NoContentAvailableException();
				User u = es.getUserById(userId);
				if (u == null)
					throw new NoContentAvailableException();
				SaveSingleUser(u);
				return u;
			} catch (IOException ex) {
				throw new NoContentAvailableException();
			}
		} else {
			throw new NoInternetException();
		}
	}

	public List<QuestionList> getAllUserFavourites()
			throws NoContentAvailableException {
		if (InternetCheck.haveInternet()) {
			try {
				ESClient es = new ESClient();
				List<Question> aux = es.getFavouriteQuestionsByUser(MainModel
						.getInstance().getCurrentUser());
				if (aux == null)
					throw new NoContentAvailableException();
				List<QuestionList> lst = new ArrayList<QuestionList>();
				for (Question q : aux) {
					SaveSingleQuestion(q);
					lst.add(new QuestionList(q.getId(), q.getTitle(), q
							.getAuthorName(), q.getAuthorId(), q
							.getAnswerCountString(), q.getUpVoteCountString(),
							q.hasPicture(), q.getDate(), q.getCoordinate(), q.getLocation()));
				}
				return lst;
			} catch (IOException ex) {
				throw new NoContentAvailableException();
			}
		} else {
			throw new NoContentAvailableException();
		}
	}

	public List<QuestionList> getAllUserFavouritesFromCache()
			throws NoContentAvailableException {
		List<String> userFavs = MainModel.getInstance().getCurrentUser()
				.getFavourites();
		List<QuestionList> aux = getQuestionListFromQuestionsCache();
		List<QuestionList> lst = new ArrayList<QuestionList>();
		if (userFavs == null || userFavs.size() <= 0 || aux == null
				|| aux.size() <= 0)
			throw new NoContentAvailableException();
		for (QuestionList ql : aux) {
			if (userFavs.contains(ql.getId())) {
				lst.add(ql);
			}
		}
		return lst;
	}
}
