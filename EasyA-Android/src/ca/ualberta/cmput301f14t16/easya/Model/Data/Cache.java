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
 * Provides simplified access to {@link Content} objects stored as json data on
 * the device's local memory. This is effectively the offline cache for any data
 * seen or saved by the user.
 * 
 * @author Cauani
 *
 */
public class Cache {
	private static Cache cache;

	/**
	 * Creates a new Cache object.
	 */
	protected Cache() {
	}

	/**
	 * @return An instance of cache.
	 */
	public static Cache getInstance() {
		if (cache == null)
			cache = new Cache();
		return cache;
	}

	/**
	 * Overwrites all entries in the cache with empty data.
	 */
	public void wipeCache() {
		PMDataParser.saveJson(PMFilesEnum.CACHEQUESTIONS, "");
		PMDataParser.saveJson(PMFilesEnum.CACHEUSERS, "");
		PMDataParser.saveJson(PMFilesEnum.CACHEQUESTIONSLIST, "");
	}

	/**
	 * @return All Question objects from the Cache who's author matches the
	 *         application's current user.
	 * @throws NoContentAvailableException
	 *             If no relevant content is found in the Cache.
	 */
	public List<QuestionList> getAllUserQuestions()
			throws NoContentAvailableException {
		return getUserQuestionsFromCache(MainModel.getInstance()
				.getCurrentUser());
	}

	/**
	 * Returns a {@link Question} from the Cache with an ID matching the one
	 * provided.
	 * 
	 * @param id
	 *            A {@link Question} object's unique ID.
	 * @return A {@link Question} object in the Cache with a matching ID.
	 * @throws NoContentAvailableException
	 *             If no relevant content was found.
	 */
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

	/**
	 * Returns a {@link User} from the Cache with an ID matching the one
	 * provided.
	 * 
	 * @param id
	 *            A {@link User} object's unique ID.
	 * @return A {@link User} object in the Cache with a matching ID.
	 * @throws NoContentAvailableException
	 *             If no relevant content was found.
	 */
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

	/**
	 * Saves the provided {@link Question} object in the Cache.
	 * 
	 * @param q
	 *            The {@link Question} object to be saved in the Cache.
	 */
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
	 * Saves a {@link User} object to the Cache. Overwrites any pre-existing
	 * identical {@link User} objects. Use this when the user changes their
	 * username.
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

	/**
	 * Replaces the saved list of {@link Question} objects with the provided new
	 * one.
	 * 
	 * @param lst
	 *            The list of {@link Question} objects to replace the old one.
	 */
	private void UpdateQuestionList(List<QuestionList> lst) {
		Gson gson = new Gson();
		PMDataParser.saveJson(PMFilesEnum.CACHEQUESTIONSLIST, gson.toJson(lst));
	}

	/**
	 * Replaces the saved list of {@link User} objects with the provided new
	 * one.
	 * 
	 * @param lst
	 *            The list of {@link User} objects to replace the old one.
	 */
	private void UpdateUsers(List<User> lst) {
		Gson gson = new Gson();
		PMDataParser.saveJson(PMFilesEnum.CACHEUSERS, gson.toJson(lst));
	}

	/**
	 * @param u
	 *            The user to attempt to match.
	 * @return All Question objects from the Cache who's author matches the
	 *         provided user.
	 * @throws NoContentAvailableException
	 *             If no relevant content is found in the Cache.
	 */
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

	/**
	 * @return All {@link QuestionList} objects from the Cache.
	 * @throws NoContentAvailableException
	 *             If no relevant content was found in the Cache.
	 */
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

	/**
	 * @return All {@link Question} objects from the Cache.
	 * @throws NoContentAvailableException
	 *             If no relevant content was found in the Cache.
	 */
	public List<Question> getSavedQuestions()
			throws NoContentAvailableException {
		Gson gson = new Gson();
		Type listType = new TypeToken<List<Question>>() {
		}.getType();
		List<Question> aux = gson.fromJson(
				PMDataParser.loadJson(PMFilesEnum.CACHEQUESTIONS), listType);
		if (aux == null || aux.size() <= 0)
			throw new NoContentAvailableException();
		return aux;
	}

	/**
	 * @return All {@link QuestionList} objects from the Cache and returns them
	 *         in {@link QuestionList} form.
	 * @throws NoContentAvailableException
	 *             If no relevant content was found in the Cache.
	 */
	public List<QuestionList> getSavedQuestionsList()
			throws NoContentAvailableException {
		List<Question> aux = getSavedQuestions();
		return GeneralHelper.lqToQuestionlist(aux);
	}

	/**
	 * Returns a {@link Question} from the Cache with an ID matching the one
	 * provided.
	 * 
	 * @param id
	 *            A {@link Question} object's unique ID.
	 * @return A {@link Question} object in the Cache with a matching ID.
	 * @throws NoContentAvailableException
	 *             If no relevant content was found.
	 */
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

	/**
	 * Returns a {@link User} from the Cache with an ID matching the one
	 * provided.
	 * 
	 * @param id
	 *            A {@link User} object's unique ID.
	 * @return A {@link User} object in the Cache with a matching ID.
	 * @throws NoContentAvailableException
	 *             If no relevant content was found.
	 */
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

	/**
	 * @return All {@link User} objects from the Cache.
	 * @throws NoContentAvailableException
	 *             If no relevant content was found in the Cache.
	 */
	public List<User> getUsersListFromCache() {
		Gson gson = new Gson();
		Type listType = new TypeToken<List<User>>() {
		}.getType();
		List<User> lst = gson.fromJson(
				PMDataParser.loadJson(PMFilesEnum.CACHEUSERS), listType);
		if (lst == null)
			lst = new ArrayList<User>();
		return lst;
	}

	/**
	 * Updates all {@link User} objects in the Cache by the ones acquired from
	 * the elastic search database.
	 */
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

	/**
	 * Updates the Cache with {@link QuestionList} objects from the elastic
	 * search database.
	 * 
	 * @return The new list of {@link QuestionList} objects.
	 * @throws NoContentAvailableException
	 */
	public List<QuestionList> getAllQuestions()
			throws NoContentAvailableException {
		if (InternetCheck.haveInternet()) {
			ESClient es = new ESClient();
			try {
				System.out
						.println("I'm in da house! CACHE PEACE LOVE BABY YEAH");
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

	/**
	 * Updates a {@link User} object in the Cache from its entry in the elastic
	 * search database.
	 * 
	 * @param email
	 *            The id of the {@link User} to update.
	 * @return The updated {@link User} entry.
	 * @throws NoInternetException
	 *             If no Internet connection was found.
	 * @throws NoContentAvailableException
	 *             If no relevant content was found.
	 */
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

	/**
	 * Acquires all {@link Question} objects from the elastic search database
	 * marked as favourite by the current user, and addes them to the Cache.
	 * 
	 * @return A list of all {@link QuestionList} objects marked as the user's
	 *         favourites.
	 * @throws NoContentAvailableException
	 *             If no relevant content was found.
	 */
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
							q.hasPicture(), q.getDate(), q.getCoordinate(), q
									.getLocation()));
				}
				return lst;
			} catch (IOException ex) {
				throw new NoContentAvailableException();
			}
		} else {
			throw new NoContentAvailableException();
		}
	}

	/**
	 * Gets a list of {@link QuestionList} objects containing all questions
	 * marked as favourite by the user.
	 * 
	 * @return All {@link QuestionList} objects from the Cache that have been
	 *         marked as favourite by the current user.
	 * @throws NoContentAvailableException
	 */
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
