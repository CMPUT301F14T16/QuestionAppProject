package ca.ualberta.cmput301f14t16.easya.Model;


import java.io.IOException;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import ca.ualberta.cmput301f14t16.easya.Exceptions.NoClassTypeSpecifiedException;
import ca.ualberta.cmput301f14t16.easya.Exceptions.NoInternetException;
import ca.ualberta.cmput301f14t16.easya.Model.Data.ContextProvider;
import ca.ualberta.cmput301f14t16.easya.Model.Data.ESClient;
import ca.ualberta.cmput301f14t16.easya.Model.Data.PMClient;

/**
 * Created by Cauani on 2014-10-25.
 * @author Cauani
 */
public class Queue extends Thread{
    final long loop_interval = 5000; //in milliseconds
    final int check_for_internet = 300000; //in milliseconds 5 minutes
    
    private static Queue queue;
    
    public Date lastCheck; // TODO made public because test case uses it.
    private boolean haveInternet;
    private boolean isActive = true;

    private List<Pending> pendings;

    protected Queue()
    {
        this.pendings = GetAllPendings();
    }
    
    public static Queue getInstance(){
    	if (queue == null || (queue != null && !queue.isAlive())) {
            queue = new Queue();
            queue.start();
        }
    	return queue;
    }

    @Override
    public void run()
    {
        while(isActive)
        {
            try {
                if (!pendings.isEmpty()){
                    if (haveInternetConnection())
                        ProcessPendings();
                }
                    Thread.sleep(loop_interval);
            }
            catch(InterruptedException ex) {} //Do nothing
            catch(IOException ex){
            	//TODO: deal with IOException or pass it forward
            }catch(Exception ex){
                //TODO: deal with all other exceptions
            	//Currently dealing with them by shutting the Queue System off, may not be the best thing to do
                isActive = false;
                return;
            }
        }
    }

    /*
        Add new pending items to the queue
     */
    public void AddPendingToQueue(Pending p){
    	PMClient pm = new PMClient();
    	pm.savePending(p);
        this.pendings.add(p);
    }

    /*
        Remove a processed pending from both phone memory and list
     */
    private void RemovePending(Pending p){
    	PMClient pm = new PMClient();
    	pm.deletePending(p);
        this.pendings.remove(p);
    }

    /*
        Retrieve all pendings from the phone internal memory
     */
    private List<Pending> GetAllPendings(){
    	PMClient pm = new PMClient();
        return pm.getPendings();
    }


    /*
        Method that deals with processing the pending objects list and passing them forward to the class that will
        manipulate the elastic search
     */
    public void ProcessPendings() throws NoClassTypeSpecifiedException, NoInternetException, IOException{
        ESClient esClient = new ESClient();
        int tries = 0;
    	for(Pending p : pendings){
        	Content c = p.getContent();
            try {
                if(c instanceof Question){
                	esClient.submitQuestion((Question)c);
                }else if (c instanceof Answer){
                	esClient.submitAnswer((Answer)c, p.getAnswerId());
                }else if (c instanceof Reply){
                	if (p.getAnswerId() != null && !p.getAnswerId().isEmpty()){
                		esClient.submitAnswerReply((Reply)c, p.getQuestionId(), p.getAnswerId());
                	}else{
                		esClient.submitQuestionReply((Reply)c, p.getQuestionId());
                	}
                }else{
                	throw new NoClassTypeSpecifiedException();
                }
            }
            catch(NoClassTypeSpecifiedException ex){
            	throw ex;
            }catch(IOException ex){
            	if (!haveInternetConnection())
            		return;
        		if (tries >3)
        			return;
        		tries++;
            }finally {            
                RemovePending(p);
            }
        }
    }

    /*
        Checks whether the phone can reach a certain url or not
        (so even if the user have internet, if our server can't be reached, assume something is wrong)
     */
    private boolean checkForInternet()
    {
        try {
        	ConnectivityManager cm =
        	        (ConnectivityManager)ContextProvider.get().getSystemService(Context.CONNECTIVITY_SERVICE);
        	 
        	NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        	return activeNetwork != null &&
        	                      activeNetwork.isConnectedOrConnecting();
        } catch (Exception ex) {
            return false;
        }
    }

    public boolean haveInternetConnection()
    {
        if (this.lastCheck == null || ((new Date()).getTime() - this.lastCheck.getTime()) >= check_for_internet)
        {
            this.lastCheck = new Date();
            this.haveInternet = checkForInternet();
        }
        return this.haveInternet;
    }

    public void Stop(){
       this.isActive = false;
        try {
            this.join();
        }catch(InterruptedException ex){}
    }

    public boolean ForceCheckInternet(){
        this.lastCheck = null;
        return haveInternetConnection();
    }
}
