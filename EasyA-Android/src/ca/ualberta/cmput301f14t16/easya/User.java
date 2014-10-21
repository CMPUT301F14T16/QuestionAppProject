package ca.ualberta.cmput301f14t16.easya;

import java.util.List;

public class User {
	
		public String userName;
		public String userID;
		public List<Integer> submittedContent;
		
		public User(String userEmail, String userName) {
			this.userID = userEmail;
			this.userName = userName;
		}
		
		public void setUserName(String newName) {
			this.userName = newName;
		}
		
}
