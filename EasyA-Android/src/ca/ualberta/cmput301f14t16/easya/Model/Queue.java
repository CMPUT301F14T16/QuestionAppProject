package ca.ualberta.cmput301f14t16.easya.Model;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.locks.ReentrantLock;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Handler;
import android.os.Looper;
import android.widget.Toast;

import ca.ualberta.cmput301f14t16.easya.Exceptions.NoClassTypeSpecifiedException;
import ca.ualberta.cmput301f14t16.easya.Exceptions.NoInternetException;
import ca.ualberta.cmput301f14t16.easya.Model.Data.Cache;
import ca.ualberta.cmput301f14t16.easya.Model.Data.ContextProvider;
import ca.ualberta.cmput301f14t16.easya.Model.Data.ESClient;
import ca.ualberta.cmput301f14t16.easya.Model.Data.PMClient;

/**
 * Creates a new {@link Thread} that automatically processes new {@link Content}
 * created by the user. The Thread will store the newly received {@link Content}
 * in a pending queue, and regularly check for a new connection. Once one is
 * found, all items in the pending queue will be pushed, and the queue will be
 * cleared. In practice, this will more or less immediately push any new
 * {@link Content} when the device has an active Internet connection, and store
 * any new {@link Content} until one is found if not.
 * 
 * @author Cauani
 */
public class Queue extends Thread {
	private final ReentrantLock lock = new ReentrantLock();
	/**
	 * The amount of time the thread will sleep between every cycle.
	 */
	final long loop_interval = 60000; // in milliseconds

	private static Queue queue;

	private boolean isActive = true;

	/**
	 * A list of {@link Content} objects that are waiting to be pushed.
	 */
	private List<Pending> pendings;

	/**
	 * Creates a new Queue, and reads all saved pending objects from memory,
	 * storing them in {@link Queue#pendings}.
	 */
	protected Queue() {
		this.pendings = GetAllPendings();
	}

	/**
	 * @return A new instance of Queue if none are currently alive, or the
	 *         currently working instance if not.
	 */
	public static Queue getInstance() {
		if (queue == null || (queue != null && !queue.isAlive())) {
			queue = new Queue();
			queue.start();
		}
		return queue;
	}

	/**
	 * @see java.lang.Thread#run()
	 */
	@Override
	public void run() {
		while (isActive) {
			try {
				if (!pendings.isEmpty()) {
					if (InternetCheck.haveInternet()){
						ProcessPendings();
						MainModel.getInstance().notifyViews();
					}
				}
				Thread.sleep(loop_interval);
			} catch (InterruptedException ex) {
			} // Do nothing
			catch (IOException ex) {
				// TODO: deal with IOException or pass it forward
			} catch (Exception ex) {
				// TODO: deal with all other exceptions
				// Currently dealing with them by shutting the Queue System off,
				// may not be the best thing to do
				isActive = false;
				return;
			}
		}
	}

	/**
	 * Adds a new {@link Pending} object to the pending Queue.
	 * 
	 * @param p
	 *            The new object to be added.
	 */
	public void AddPendingToQueue(Pending p) {
		try{
		PMClient pm = new PMClient();
		pm.savePending(p);
		this.pendings.add(p);
		}catch(Exception ex){
		}
	}

	/**
	 * Removes the given pending object both from the Queue, and from memory.
	 * 
	 * @param p
	 *            The {@link Pending} object to be removed.
	 */
	private void RemovePending(Pending p) {
		try{
			PMClient pm = new PMClient();
			pm.deletePending(p);
		}catch(Exception ex){
		}
	}

	/*
	 * Retrieve all pendings from the phone internal memory
	 */
	/**
	 * @return All {@link Pending} objects saved in memory.
	 */
	private List<Pending> GetAllPendings() {
		PMClient pm = new PMClient();
		List<Pending> lst = pm.getPendings();
		if (lst == null)
			lst = new ArrayList<Pending>();
		return lst;
	}

	/*
	 * Method that deals with processing the pending objects list and passing
	 * them forward to the class that will manipulate the elastic search
	 */
	/**
	 * Processes all {@link Pending} objects in the Queue, and pushes them to
	 * the ES database.
	 * 
	 * @throws NoClassTypeSpecifiedException
	 * @throws NoInternetException
	 * @throws IOException
	 */
	public void ProcessPendings() throws NoClassTypeSpecifiedException, NoInternetException, IOException{
        lock.lock();
        try{
        	ESClient esClient = new ESClient();
            int tries = 0;
            int qtP = pendings.size();            
            Iterator<Pending> iterator = pendings.iterator();
            while (iterator.hasNext()) {
            	Pending p = iterator.next();
                try {    			
        			Content c = p.getContent();   
        			c.setDate(Time.getDate());
                    if(c instanceof Question){
                    	if (esClient.submitQuestion((Question) c)){
    						Cache.getInstance().SaveSingleQuestion((Question) c);						
    					}
                    }else if (c instanceof Answer){
                    	esClient.submitAnswer((Answer)c, p.getQuestionId());
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
                	if (!InternetCheck.haveInternet())
                		return;
            		if (tries >3)
            			return;
            		tries++;
                }                            
                RemovePending(p);
    			iterator.remove();                
            }
            
        	final String aux = qtP + " item(s) were uploaded from your pendings.";
        	
        	Handler handler = new Handler(Looper.getMainLooper());
        	handler.post(new Runnable() {
        	     public void run() {
        	    	 Toast.makeText(ContextProvider.get(), aux, Toast.LENGTH_LONG).show();
        	     }
        	});
		}finally{
			lock.unlock();
		}	
    }
	
	/**
	 * Stops the current instance of Queue.
	 */
	public void Stop() {
		this.isActive = false;
		try {
			this.join();
		} catch (InterruptedException ex) {
		}
	}
}
