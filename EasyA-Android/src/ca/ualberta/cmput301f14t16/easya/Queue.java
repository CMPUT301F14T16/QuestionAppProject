package ca.ualberta.cmput301f14t16.easya;


import java.io.IOException;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Cauani on 2014-10-25.
 */
public class Queue extends Thread
{
    //TODO: Handle the logic for updating Cache as well

    final String ping_url = "bing.com"; //TODO: switch the host name to our elastic server
    final long loop_interval = 5000; //in milliseconds
    final int check_for_internet = 300000; //in milliseconds

    private Date lastCheck;
    private boolean haveInternet;
    private boolean isActive = true;
    
    private ESClient escClient = new ESClient();

    //TODO: add a instance of the model that holds all the questions

    private List<Pending> pendings;

    public Queue()
    {
        this.pendings = GetAllPendings();
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
                    this.sleep(loop_interval);
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
        //TODO: add the pending to a file on phone internal memory
        this.pendings.add(p);
    }

    /*
        Remove a processed pending from both phone memory and list
     */
    private void RemovePending(Pending p){
        //Todo: remove the given pending object from the phone internal memory
        this.pendings.remove(p);
    }

    /*
        Retrieve all pendings from the phone internal memory
     */
    private List<Pending> GetAllPendings(){
        //TODO: return all pendings from the phone internal memory
        return new ArrayList<Pending>();
    }


    /*
        Method that deals with processing the pending objects list and passing them forward to the class that will
        manipulate the elastic search
     */
    public void ProcessPendings() throws NoClassTypeSpecifiedException, NoInternetException, IOException{
        for(Pending p : pendings){
        	Content c = p.getContent();
            try {
                if(c instanceof Question){
                	escClient.submitQuestion((Question)c);
                }else if (c instanceof Answer){
                	escClient.submitAnswer((Answer)c, p.getAnswerId());
                }else if (c instanceof Reply){
                	if (p.getAnswerId() != null && !p.getAnswerId().isEmpty()){
                		escClient.submitAnswerReply((Reply)c, p.getQuestionId(), p.getAnswerId());
                	}else{
                		escClient.submitQuestionReply((Reply)c, p.getQuestionId());
                	}
                }else{
                	throw new NoClassTypeSpecifiedException();
                }
            }
            catch(NoClassTypeSpecifiedException ex){
            	throw ex;
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
            InetAddress address = InetAddress.getByName(ping_url);
            if (address.equals("")) {
                return false;
            } else {
                return true;
            }
        } catch (Exception e) {
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
