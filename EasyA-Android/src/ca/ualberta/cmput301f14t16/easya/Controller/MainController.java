package ca.ualberta.cmput301f14t16.easya.Controller;

import java.io.IOException;

import android.content.Context;
import ca.ualberta.cmput301f14t16.easya.Exceptions.NoClassTypeSpecifiedException;
import ca.ualberta.cmput301f14t16.easya.Exceptions.NoInternetException;
import ca.ualberta.cmput301f14t16.easya.Model.Answer;
import ca.ualberta.cmput301f14t16.easya.Model.Content;
import ca.ualberta.cmput301f14t16.easya.Model.Question;
import ca.ualberta.cmput301f14t16.easya.Model.Pending;
import ca.ualberta.cmput301f14t16.easya.Model.Reply;
import ca.ualberta.cmput301f14t16.easya.Model.Data.ESClient;
import ca.ualberta.cmput301f14t16.easya.View.MainActivity;

public abstract class MainController {
	protected Pending pending;
	protected Context ctx;
	public boolean submitedOffline = false;
	
	protected MainController(Pending c){
		this.pending = c;
	}
	
	protected boolean submit() throws NoInternetException{
		ESClient es = new ESClient();
		if (MainActivity.mQueueThread.haveInternetConnection()){
	        try {
	        	Content c = pending.getContent();
	            if(c instanceof Question){
	            	return es.submitQuestion((Question)c);
	            }else if (c instanceof Answer){
	            	return es.submitAnswer((Answer)c, pending.getQuestionId());
	            }else if (c instanceof Reply){
	            	if (pending.getAnswerId() != null && !pending.getAnswerId().isEmpty()){
	            		return es.submitAnswerReply((Reply)c, pending.getQuestionId(), pending.getAnswerId());
	            	}else{
	            		return es.submitQuestionReply((Reply)c, pending.getQuestionId());
	            	}
	            }else{
	            	throw new NoClassTypeSpecifiedException();
	            }
	        }catch(IOException ex){
	        	if (MainActivity.mQueueThread.haveInternetConnection())
	        		return false;
	        	throw new NoInternetException();
	        }catch(NoClassTypeSpecifiedException ex){
	        	return false;
	        }catch(Exception ex){
	        	return false;
	        }
		}else{
			throw new NoInternetException();
		}
	}
	
	protected void submitOffline(){
		MainActivity.mQueueThread.AddPendingToQueue(this.pending);
		submitedOffline = true;
	}
	
	public String getQuestionId(){
		try{
			return this.pending.getQuestionId() == null ? this.pending.getContent().getId() : this.pending.getQuestionId();
		}catch(Exception ex){
			return "";
		}
	}
}