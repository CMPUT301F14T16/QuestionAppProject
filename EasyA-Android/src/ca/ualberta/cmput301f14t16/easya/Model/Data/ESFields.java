package ca.ualberta.cmput301f14t16.easya.Model.Data;

/**
 * A data object to extract data from the fields attribute of an elastic search response.
 * @author Brett Commandeur (commande)
 */
public class ESFields {
	long _timestamp;
	public long getTimestamp() {
		return _timestamp;
	}
}
