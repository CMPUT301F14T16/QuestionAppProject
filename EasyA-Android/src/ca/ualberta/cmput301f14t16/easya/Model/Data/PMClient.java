package ca.ualberta.cmput301f14t16.easya.Model.Data;

import java.lang.reflect.Type;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import ca.ualberta.cmput301f14t16.easya.Exceptions.NoContentAvailableException;
import ca.ualberta.cmput301f14t16.easya.Model.GeneralHelper;
import ca.ualberta.cmput301f14t16.easya.Model.Pending;
import ca.ualberta.cmput301f14t16.easya.Model.User;

/**
 * Phone Memory Client (really silly name)
 * Takes care of accessing the phone memory for saving and retrieving.
 * Will be used by Queue and Cache
 * @author Cauani
 *
 */
public class PMClient {
	public PMClient(){}
	
	public void saveQTitle(Context ctx, String text){
		PMDataParser.saveUserPreference(ctx, GeneralHelper.QTITLE, text);
	}
	
	public String getQTitle(Context ctx){
		return PMDataParser.recoverUserPreference(ctx, GeneralHelper.QTITLE);
	}
	
	public void saveQBody(Context ctx, String text){
		PMDataParser.saveUserPreference(ctx, GeneralHelper.QBODY, text);
	}
	
	public String getQBody(Context ctx){
		return PMDataParser.recoverUserPreference(ctx, GeneralHelper.QBODY);
	}
	
	public void saveABody(Context ctx, String text){
		PMDataParser.saveUserPreference(ctx, GeneralHelper.ABODY, text);
	}
	
	public String getABody(Context ctx){
		return PMDataParser.recoverUserPreference(ctx, GeneralHelper.ABODY);
	}
	
	public void clearQ(Context ctx){
		saveQTitle(ctx,"");
		saveQBody(ctx,"");
	}
	
	public void clearA(Context ctx){
		saveABody(ctx,"");
	}
	
	public void saveUser(Context ctx, User user){
		Gson gson = new Gson();
		PMDataParser.saveUserPreference(ctx, User.USERKEY, gson.toJson(user, User.class));
	}
	
	public User getUser(Context ctx) throws NoContentAvailableException{
		Gson gson = new Gson();
		String aux = PMDataParser.recoverUserPreference(ctx, User.USERKEY);
		if (aux == null|| aux.equals("")){
			throw new NoContentAvailableException();
		}
		return gson.fromJson(aux, User.class);
	}

	public void savePending(Context ctx, Pending p) {
		Gson gson = new Gson();
		List<Pending> aux = getPendings(ctx);
		aux.add(p);
		PMDataParser.saveJson(ctx, PMFilesEnum.QUEUE, gson.toJson(aux));	
	}
	
	public void deletePending(Context ctx, Pending p) {
		Gson gson = new Gson();
		List<Pending> aux = getPendings(ctx);
		if (aux.remove(p))
			PMDataParser.saveJson(ctx, PMFilesEnum.QUEUE, gson.toJson(aux));	
	}
	
	public List<Pending> getPendings(Context ctx){
		Gson gson = new Gson();
		Type listType = new TypeToken<List<Pending>>(){}.getType();
		return gson.fromJson(PMDataParser.loadJson(ctx, PMFilesEnum.QUEUE), listType);
	}
	
	protected final static List<Pending> Sorter(List<Pending> lst)
    {
        //First sort the list by date of creation
        Collections.sort(lst, new Comparator<Pending>() {
            @Override
            public int compare(Pending obj1, Pending obj2) {
                return obj2.getContent().getDate().compareTo(obj1.getContent().getDate());
            }
        });
        return lst;
    }
}
