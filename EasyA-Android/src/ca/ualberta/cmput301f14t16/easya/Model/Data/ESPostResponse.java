package ca.ualberta.cmput301f14t16.easya.Model.Data;

/**
 * A data object used to read and process the response of an elastic search post request.
 * @author Brett Commandeur (commande)
 */
public class ESPostResponse {
	public String _index;
	public String _type;
	public String _id;
	public int _version;
	public boolean created;
}
