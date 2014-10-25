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




public class Pending {

	public int qid;
	public int aid;
	public int rid;
	public Content content;
	public int getQid() {
		return qid;
	}
	public int getAid() {
		return aid;
	}
	public int getRid() {
		return rid;
	}
	public Content getContent() {
		return content;
	}
	
}
