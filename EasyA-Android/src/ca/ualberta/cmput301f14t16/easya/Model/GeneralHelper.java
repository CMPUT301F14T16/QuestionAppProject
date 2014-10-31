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

	/**
	 * @param c
	 * @return
	 * @throws UnableToGetUserEmailException
	 */
	//TODO Docstring method description
	public String retrieveEmail(Context c) throws UnableToGetUserEmailException{
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
