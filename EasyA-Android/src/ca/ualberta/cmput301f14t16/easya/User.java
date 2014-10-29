package ca.ualberta.cmput301f14t16.easya;

import java.util.Date;
import java.util.List; 
import java.util.UUID;

public class User {
	
		private String username;
		private String email;
		private String userid;
		private Date date;
		private List<Integer> NsubmittedContent;
		
		public User(String email, String username,List<Integer> NSC) {
			this.email = email;
			this.username = username;
			this.userid = UUID.randomUUID().toString();
			this.date = new Date();
			this.NsubmittedContent = NSC;
		}
		
		public void setUserName(String newName) {
			this.username = newName;
		}
		
		
		public String getEmail() {
			return email;
		}
		
		public String getUserName() {
			return username;
		}
		
		public String getUserId()  {
			return userid;
		}
		
		public List<Integer> getNsubmittedContent() {
			return NsubmittedContent;
		}
		
}

