package ca.ualberta.cmput301f14t16.easya.View;

import java.util.ArrayList;
import java.util.List;

public enum SortEnum {
	NEWEST("Most recent"),
	OLDEST("Least recent"),
	MOSTVOTES("Most votes"),
	LEASTVOTES("Least votes"),
	HASPICTURE("Has picture"),
	HASNOPICTURE("Has no picture"),
	CLOSETOME("Close to me"),
	FARFROMME("Far from me");
	private String displayName;
	
	private SortEnum(String s){
		this.displayName = s;
	}
	
	public String getDisplayName(){
		return this.displayName;
	}
	
	public static String[] getList(){
		ArrayList<String> ls = new ArrayList<String>();
		for (SortEnum i : SortEnum.values()){
			ls.add(i.getDisplayName());
		}
		return ls.toArray(new String[ls.size()]);
	}
}
