package ca.ualberta.cmput301f14t16.easya.Model.Data;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Used in processing elastic search query responses.
 * 
 * Reference: <a href="https://github.com/rayzhangcl/ESDemo/blob/master/ESDemo/src/ca/ualberta/cs/CMPUT301/chenlei/Hits.java">https://github.com/rayzhangcl/ESDemo/blob/master/ESDemo/src/ca/ualberta/cs/CMPUT301/chenlei/Hits.java</a>, on Oct 26, 2014.
 * @param <T>
 */
public class ESSearchResponse<T> {
    int took;
    boolean timed_out;
    transient Object _shards;
    ESHits<T> hits;
    boolean exists;    
    public Collection<ESGetResponse<T>> getHits() {
        return hits.getHits();        
    }
    public Collection<T> getSources() {
        Collection<T> out = new ArrayList<T>();
        for (ESGetResponse<T> essrt : getHits()) {
            out.add( essrt.getSource() );
        }
        return out;
    }
    public String toString() {
        return (super.toString() + ":" + took + "," + _shards + "," + exists + ","  + hits);     
    }
}
