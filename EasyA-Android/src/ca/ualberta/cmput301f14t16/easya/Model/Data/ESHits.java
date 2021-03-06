package ca.ualberta.cmput301f14t16.easya.Model.Data;

import java.util.Collection;

/**
 * A data object used in elastic search processing to extract the search results of an elastic search '_search' response.
 * 
 * Reference: <a href="https://github.com/rayzhangcl/ESDemo/blob/master/ESDemo/src/ca/ualberta/cs/CMPUT301/chenlei/Hits.java">https://github.com/rayzhangcl/ESDemo/blob/master/ESDemo/src/ca/ualberta/cs/CMPUT301/chenlei/Hits.java</a>, on Oct 26, 2014.
 *
 */
public class ESHits<T> {
    int total;
    double max_score;
    Collection<ESGetResponse<T>> hits;
    public Collection<ESGetResponse<T>> getHits() {
        return hits;
    }
    public String toString() {
        return (super.toString()+","+total+","+max_score+","+hits);
    }
}
