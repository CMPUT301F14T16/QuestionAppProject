package ca.ualberta.cmput301f14t16.easya.Model.Data;

/**
 * Lists the databases (filenames) for the PMClient
 * @author Cauani
 *
 */
public enum PMFilesEnum {
    CACHEQUESTIONS("ca.ualberta.cmput301f14t16.easya.CACHEQUESTIONS"),
    CACHEQUESTIONSLIST("ca.ualberta.cmput301f14t16.easya.CACHEQUESTIONSLIST"),
    CACHEUSERS("ca.ualberta.cmput301f14t16.easya.CACHEUSERS"),
    QUEUE("ca.ualberta.cmput301f14t16.easya.QUEUE");
    private String fileName;
    
    PMFilesEnum(String value)
    {
        this.fileName = value;
    }

    public String getArchiveName()
    {
        return this.fileName;
    }
}
