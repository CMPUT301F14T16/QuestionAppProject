package ca.ualberta.cmput301f14t16.easya;

import java.util.List;

public class User {
	
		public String username;
		public String email;
		public List<Integer> submittedContent;
		
		public User(String email, String username) {
			this.email = email;
			this.username = username;
		}
		
		public void setUserName(String newName) {
			this.username = newName;
		}
		
		public String getEmail() {
			return email;
		}
		
}
