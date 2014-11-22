package ca.ualberta.cmput301f14t16.easya.Model.Data;

/**
 * enum that contains the location (file name) of various databases saved in
 * local memory.
 * 
 * @author Cauani
 *
 */
public enum PMFilesEnum {
	CACHEQUESTIONS("ca.ualberta.cmput301f14t16.easya.CACHEQUESTIONS"),
	CACHEQUESTIONSLIST("ca.ualberta.cmput301f14t16.easya.CACHEQUESTIONSLIST"),
	CACHEUSERS("ca.ualberta.cmput301f14t16.easya.CACHEUSERS"),
	QUEUE("ca.ualberta.cmput301f14t16.easya.QUEUE");
	private String fileName;

	PMFilesEnum(String value) {
		this.fileName = value;
	}

	public String getArchiveName() {
		return this.fileName;
	}
}
