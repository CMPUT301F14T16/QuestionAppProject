package ca.ualberta.cmput301f14t16.easya.Model.Data;

/**
 * A data object used to extract elastic search index object from an elastic search response.
 * 
 * @reference https://github.com/rayzhangcl/ESDemo/blob/master/ESDemo/src/ca/ualberta/cs/CMPUT301/chenlei/ElasticSearchResponse.java on Oct 23, 2014
 *
 * @param <T>	Type of object in _source field of response.
 */
public class ESGetResponse<T> {
    String _index;
    String _type;
    String _id;
    int _version;
    boolean exists;
    T _source;
    ESFields fields;
    double max_score;
    public T getSource() {
        return _source;
    }
    public ESFields getFields() {
    	return fields;
    }
}


