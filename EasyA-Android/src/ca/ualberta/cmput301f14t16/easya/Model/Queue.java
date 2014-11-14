package ca.ualberta.cmput301f14t16.easya.Model;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Handler;
import android.os.Looper;
import android.widget.Toast;

import ca.ualberta.cmput301f14t16.easya.Exceptions.NoClassTypeSpecifiedException;
import ca.ualberta.cmput301f14t16.easya.Exceptions.NoInternetException;
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
	/**
	 * The amount of time the thread will sleep between every cycle.
	 */
	final long loop_interval = 5000; // in milliseconds
	/**
	 * The thread will not attempt to check for an active Internet connection
	 * unless at least this amount of time has passed since the last check.
	 */
	final int check_for_internet = 300000; // in milliseconds 5 minutes

	private static Queue queue;

	/**
	 * The time at which the last check for an active Internet connection was
	 * made.
	 */
	public Date lastCheck; // TODO made public because test case uses it.
	/**
	 * True if an active Internet has been found.
	 */
	private boolean haveInternet;
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
					if (haveInternetConnection())
						ProcessPendings();
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
		PMClient pm = new PMClient();
		pm.savePending(p);
		this.pendings.add(p);
	}

	/**
	 * Removes the given pending object both from the Queue, and from memory.
	 * 
	 * @param p
	 *            The {@link Pending} object to be removed.
	 */
	private void RemovePending(Pending p) {
		PMClient pm = new PMClient();
		pm.deletePending(p);
		this.pendings.remove(p);
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
        ESClient esClient = new ESClient();
        int tries = 0;
        int qtP = pendings.size();
    	for(Pending p : pendings){
    		try {
    			Content c = p.getContent();            
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
    	final String aux = qtP + " item(s) were uploaded from your pendings.";
    	
    	Handler handler = new Handler(Looper.getMainLooper());
    	handler.post(new Runnable() {
    	     public void run() {
    	    	 Toast.makeText(ContextProvider.get(), aux, Toast.LENGTH_LONG).show();
    	     }
    	});    	    	
    }

	/**
	 * @return True if an active Internet connection is found.
	 */
	private boolean checkForInternet() {
		try {
			ConnectivityManager cm = (ConnectivityManager) ContextProvider
					.get().getSystemService(Context.CONNECTIVITY_SERVICE);

			NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
			return activeNetwork != null
					&& activeNetwork.isConnectedOrConnecting();
		} catch (Exception ex) {
			return false;
		}
	}

	/**
	 * Will call {@link Queue#checkForInternet()} if enough time has passed
	 * since the last check. It will assign the results to
	 * {@link Queue#haveInternet}.
	 * 
	 * @return {@link Queue#haveInternet} after the method has completed its
	 *         function.
	 */
	public boolean haveInternetConnection() {
		if (this.lastCheck == null
				|| ((new Date()).getTime() - this.lastCheck.getTime()) >= check_for_internet) {
			this.lastCheck = new Date();
			this.haveInternet = checkForInternet();
		}
		return this.haveInternet;
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

	/**
	 * Will call {@link Queue#haveInternetConnection()} as though enough time
	 * has passed for it to check the connection, regardless of whether or not
	 * enough time has actually elapsed.
	 * 
	 * @return The result of {@link Queue#haveInternetConnection()}
	 */
	public boolean ForceCheckInternet() {
		this.lastCheck = null;
		return haveInternetConnection();
	}
}
