package ca.ualberta.cmput301f14t16.easya.Model.Data;

import java.util.ArrayList;
import java.util.Collection;

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
