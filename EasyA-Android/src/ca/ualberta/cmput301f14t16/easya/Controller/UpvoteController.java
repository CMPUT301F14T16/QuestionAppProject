package ca.ualberta.cmput301f14t16.easya.Controller;

import java.io.IOException;

import org.apache.http.message.BasicNameValuePair;

import ca.ualberta.cmput301f14t16.easya.Model.MainModel;
import ca.ualberta.cmput301f14t16.easya.Model.User;
import ca.ualberta.cmput301f14t16.easya.Model.Data.ESClient;
import android.content.Context;

public class UpvoteController {
	private String qId;
	private String aId;
	private String uId;
	
	protected UpvoteController(String qId, String aId, String uId){
		this.qId = qId;
		this.aId = aId;
		this.uId = uId;		
	}
	
	public static UpvoteController create(BasicNameValuePair vp, String u){		
		return new UpvoteController(vp.getName(), vp.getValue(), u);
	}
	
	public boolean submit(){
		try{
			ESClient es = new ESClient();
			if (aId == null)			
				return es.setQuestionUpvote(uId, qId);
			else
				return es.setAnswerUpvote(uId, qId, aId);			
		}catch(IOException ex){
			return false;			
		}catch(Exception ex){
			return false;
		}
	}
}
