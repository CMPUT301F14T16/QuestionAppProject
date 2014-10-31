package ca.ualberta.cmput301f14t16.easya.Model;

import java.util.regex.Pattern;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.content.Context;
import android.util.Patterns;
import ca.ualberta.cmput301f14t16.easya.Exceptions.UnableToGetUserEmailException;

/**
 * Responsible for holding methods that will be used across the entire application,
 * or are too generic to be within a class
 * @author Cauani
 *
 */
public class GeneralHelper {

	public String retrieveEmail(Context c) throws UnableToGetUserEmailException{
		Pattern emailPattern = Patterns.EMAIL_ADDRESS;
		Account[] accounts = AccountManager.get(c).getAccounts();
		for (Account account : accounts) {
		    if (emailPattern.matcher(account.name).matches()) {
		        return account.name;
		    }
		}
		throw new UnableToGetUserEmailException("Unable to get an user email");
	}
}
