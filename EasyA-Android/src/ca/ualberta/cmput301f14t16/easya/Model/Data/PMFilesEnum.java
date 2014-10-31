package ca.ualberta.cmput301f14t16.easya.Model.Data;

/**
 * Lists the databases (filenames) for the PMClient
 * @author Cauani
 *
 */
public enum PMFilesEnum {
    CACHE("cache"),
    QUEUE("queue");
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
