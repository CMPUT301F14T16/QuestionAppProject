package ca.ualberta.cmput301f14t16.easya.Model;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.regex.Pattern;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.util.Patterns;
import ca.ualberta.cmput301f14t16.easya.Exceptions.UnableToGetUserEmailException;
import ca.ualberta.cmput301f14t16.easya.Model.Data.ContextProvider;

/**
 * Provides a helper class containing general-use methods for repeated use
 * across several classes.
 * 
 * @author Cauani
 *
 */
public class GeneralHelper {

	public final static String QTITLE = "ca.ualberta.cmput301f14t16.easya.QTITLE";
	public final static String QBODY = "ca.ualberta.cmput301f14t16.easya.QBODY";
	public final static String ABODY = "ca.ualberta.cmput301f14t16.easya.ABODY";
	public static final String AQUESTION_KEY = "ca.ualberta.cmput301f14t16.easya.AQUESTIONKEY";
	public final static String QUESTION_KEY = "ca.ualberta.cmput301f14t16.easya.QUESTIONKEY";
	public final static String USERKEY = "ca.ualberta.cmput301f14t16.easya.USERKEY";
	public static final String ASTATUS = "ca.ualberta.cmput301f14t16.easya.ASTATUS";
	public static final String USERCOORDINATES = "ca.ualberta.cmput301f14t16.easya.USERCOORDINATES";
	public static final String USERLOCATION = "ca.ualberta.cmput301f14t16.easya.USERLOCATION";
	public static final String USERLOCATIONPREFERENCE = "ca.ualberta.cmput301f14t16.easya.USERLOCATIONPREFERENCE";

	// TODO Docstring method description
	/**
	 * Retrieves the first Account in the AccountManager that matches an email
	 * pattern.
	 * 
	 * @return
	 */
	public final static String retrieveEmail() {
		try {
			Pattern emailPattern = Patterns.EMAIL_ADDRESS;
			Account[] accounts = AccountManager.get(ContextProvider.get())
					.getAccounts();
			for (Account account : accounts) {
				if (emailPattern.matcher(account.name).matches()) {
					return account.name;
				}
			}
			return "";
		} catch (Exception ex) {
			return "";
		}
	}

	/**
	 * Takes a list of {@link Question} objects, and converts it into a list of {@link QuestionList} objects.
	 * @param lq The list of {@link Question} objects to convert.
	 * @return The provided list of {@link Question} objects as a list of {@link QuestionList} objects.
	 */
	public static List<QuestionList> lqToQuestionlist(List<Question> lq) {
		List<QuestionList> lst = new ArrayList<QuestionList>();
		for (Question q : lq) {
			lst.add(new QuestionList(q.getId(), q.getTitle(), q
					.getAuthorName(), q.getAuthorId(), q
					.getAnswerCountString(), q.getUpVoteCount(),
					q.hasPicture(), q.getDate(), q.getCoordinate(), q.getLocation()));
		}
		return lst;
	}

	/**
	 * Creates a random username. The username will take the format of a random
	 * word selected from a list, followed by a random number from 1000 to 9999.
	 * The list of possible words is as follows:
	 * 
	 * [Guest, Greenhorn, Inquirer, Newcomer, Newbie]
	 * 
	 * @return a {@link String} containing the randomly generated username.
	 */
	public static String GenerateUserName() {
		Random ran = new Random();
		int name = ran.nextInt(12) + 1;
		int number = ran.nextInt(8999) + 1000;
		String aux = "";
		switch (name) {
		case 1:
			aux = "Guest";
			break;
		case 2:
			aux = "GreenHorn";
			break;
		case 3:
			aux = "Inquirer";
			break;
		case 4:
			aux = "Newcomer";
			break;
		case 5:
			aux = "Newbie";
			break;
		case 6:
			aux = "Unicorn";
			break;
		case 7:
			aux = "Minion";
			break;
		case 8:
			aux = "CookieGuy";
			break;
		case 9:
			aux = "Geek";
			break;
		case 10:
			aux = "CatLover";
			break;
		case 11:
			aux = "Dino";
			break;
		case 12:
			aux = "GoldenBear";
			break;
		default:
			aux = "Guest";
			break;
		}
		return aux + number;
	}
}
