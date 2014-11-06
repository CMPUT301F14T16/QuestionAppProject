package ca.ualberta.cmput301f14t16.easya.Model;

import java.util.regex.Pattern;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.content.Context;
import android.util.Patterns;
import ca.ualberta.cmput301f14t16.easya.Exceptions.UnableToGetUserEmailException;

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
	
	/**
	 * @param c
	 * @return
	 * @throws UnableToGetUserEmailException
	 */
	//TODO Docstring method description
	public final static String retrieveEmail(Context c) throws UnableToGetUserEmailException{
		Pattern emailPattern = Patterns.EMAIL_ADDRESS;
		Account[] accounts = AccountManager.get(c).getAccounts();
		for (Account account : accounts) {
		    if (emailPattern.matcher(account.name).matches()) {
		        return account.name;
		    }
		}
		throw new UnableToGetUserEmailException("Unable to get user email");
	}
}
