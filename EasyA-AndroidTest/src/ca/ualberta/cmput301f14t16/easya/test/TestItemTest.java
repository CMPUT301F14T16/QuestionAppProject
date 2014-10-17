package ca.ualberta.cmput301f14t16.easya.test;

import ca.ualberta.cmput301f14t16.easya.MainActivity;
import ca.ualberta.cmput301f14t16.easya.data.TestItem;
import android.test.ActivityInstrumentationTestCase2;

public class TestItemTest extends ActivityInstrumentationTestCase2<MainActivity> {

	public TestItemTest() {
		super(MainActivity.class);
	}
	
	public void testTestItemCreation () {
		String testName = "Brett";
		
		TestItem testItem = new TestItem("Brett");
		assertEquals(testItem.name, testName);
	}
	
}