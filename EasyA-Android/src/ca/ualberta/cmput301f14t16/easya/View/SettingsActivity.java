package ca.ualberta.cmput301f14t16.easya.View;

import ca.ualberta.cmput301f14t16.easya.R;
import ca.ualberta.cmput301f14t16.easya.Controller.ATasks.submitAnswerTask;
import ca.ualberta.cmput301f14t16.easya.Model.GeneralHelper;
import ca.ualberta.cmput301f14t16.easya.Model.GeoCoder;
import ca.ualberta.cmput301f14t16.easya.Model.Location;
import ca.ualberta.cmput301f14t16.easya.Model.LocationPreferencesEnum;
import ca.ualberta.cmput301f14t16.easya.Model.Data.PMClient;
import android.content.Context;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class SettingsActivity extends SecureActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.settings_view);
		getActionBar().setDisplayHomeAsUpEnabled(true);
		getActionBar().setHomeButtonEnabled(true);
		
		PMClient pm = new PMClient();
		int aux_id = 0;
		switch(pm.getUserLocationPreference()){
		case OFF:
			aux_id = R.id.settings_location_off;
			break;
		case GPS:
			aux_id = R.id.settings_location_GPS;
			break;
		case SELECT:
			aux_id = R.id.settings_location_select;
			break;
		}
		((RadioButton)findViewById(aux_id)).toggle();
		setupSettings();
    }
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
	    switch (item.getItemId()) {
		    case android.R.id.home:
				onBackPressed();
				return true;
	        default:
	            return super.onOptionsItemSelected(item);
	    }
	}	
	
	private void setupSettings(){
		
		boolean gps_selected = ((RadioButton)findViewById(R.id.settings_location_GPS)).isChecked();
		boolean select_selected = ((RadioButton)findViewById(R.id.settings_location_select)).isChecked();
				
		((RelativeLayout)findViewById(R.id.settings_location_gps_box))
			.setVisibility(!gps_selected ? View.GONE : View.VISIBLE);
		((RelativeLayout)findViewById(R.id.settings_location_select_box))
			.setVisibility(!select_selected ? View.GONE : View.VISIBLE);
				
		if (gps_selected){
			((TextView)findViewById(R.id.settings_location_gps_location))
			.setText(displayLocation(Location.getLocationName()));
		}
		if (select_selected){
			((TextView)findViewById(R.id.settings_location_select_city))
			.setText(displayLocation(Location.getLocationName()));
		}		
	}
	
	public void onSettingsLocationClicked(View v){
		boolean checked = ((RadioButton)v).isChecked();
		LocationPreferencesEnum em = null;
		switch(v.getId()) {
        case R.id.settings_location_off:
            if (checked)
            	em = LocationPreferencesEnum.OFF;
            break;
        case R.id.settings_location_GPS:
            if (checked){
            	em = LocationPreferencesEnum.GPS;
            	Location.forceCheckGPS();
            }
            break;
        case R.id.settings_location_select:
            if (checked)
            	em = LocationPreferencesEnum.SELECT;
            break;
		}
		PMClient pm = new PMClient();
		pm.saveUserLocationPreference(em);		
		setupSettings();
	}
	
	public void onGpsRefreshClicked(View v){
		Location.forceCheckGPS();
		((TextView)findViewById(R.id.settings_location_gps_location))
			.setText(displayLocation(Location.getLocationName()));
		//TODO: make the refresh spin while this is working (may have to use a AsyncTask as well, but i don't quite know)
	}
	
	private String displayLocation(String loc){
		return "Current: " + loc;
	}
	
	public void onSelectAddress(View v){
		EditText txt = (EditText)findViewById(R.id.settings_location_select_input);
		double[] dcoords = GeoCoder.toLatLong(txt.getText().toString());
		if (dcoords[0]==0.0 && dcoords[1]==0.0){
			Toast.makeText(v.getContext(), "Sorry, we were unable to find the address you provided. Try again!", Toast.LENGTH_LONG).show();
		}else{
			PMClient pm = new PMClient();
			pm.saveUserCoordinates(dcoords);
			pm.saveUserLocation(GeoCoder.toAdress(dcoords[0],  dcoords[1]));
			setupSettings();
		}
		txt.setText("");		
		v.getContext();
		InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
		imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
	}	
}
