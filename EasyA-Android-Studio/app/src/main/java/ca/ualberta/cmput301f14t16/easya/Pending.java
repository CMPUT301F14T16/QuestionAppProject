package ca.ualberta.cmput301f14t16.easya;

/** Description of Pending  
 *
 * <p>
 * Pending is cache like buffer used to store
 * the unsubmitted Question, Answer and Reply.
 * Once the device get connections, we submit
 * them right away from pending,and clear space
 * for next time.
 * <p>
 * @return qid The question_id for elastic search in database
 * @return aid The answer_id for elastic search in database
 * @return rid The Reply_id for elastic search in database	update the UML later
 * @return content The content that is retrived in the pending
 * @author Lingbo Tang
 * @version 1.0 Build 1000 Oct 21st, 2014.
 */

import java.io.BufferedReader; 
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

import android.util.Log;
import java.lang.reflect.Type;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class Pending {

	
	private int qid;
	private int aid;
	private int rid;
	private Content content;
	private User user;
	private List<Question> unsubQuestion;
	private List<Answer> unsubAnswers;
	protected List<Reply> unsubreplies;
	protected int voteCount;
	protected Boolean favourite; 
	protected Boolean readLater; 
	protected String picture;
	

	/**
	 * Get unsubmitted question id from the device (pending).
	 * <p>
	 * For elastic search, always submit the top item in
	 * pending and release the space.
	 * <p>
	 * @return qid
	 * 
	 */
	public int getQid() {
		return qid;
	}
	/**
	 * Get unsubmitted answer id from the device (pending).
	 * <p>
	 * For elastic search, always submit the top item in
	 * pending and release the space.
	 * <p>
	 * @return aid
	 * 
	 */
	public int getAid() {
		return aid;
	}
	/**
	 * Get unsubmitted reply id from the device (pending).
	 * <p>
	 * For elastic search, always submit the top item in
	 * pending and release the space.
	 * <p>
	 * @return rid
	 * 
	 */
	public int getRid() {
		return rid;
	}
	/**
	 * Get unsubmitted content from the device (pending).
	 * <p>
	 * For elastic search, always submit the top item in
	 * pending and release the space.
	 * <p>
	 * @return content
	 * 
	 */
	public Content getContent() {
		return content;
	}
	
}
