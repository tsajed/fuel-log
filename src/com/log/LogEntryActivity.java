package com.log;

/*LogEntryActivity is called when the user wants to create a new
 * log entry and store them in the file system, later for use if needed.
 * After Confirm button is pressed, it takes all the information from
 * EditText objects and creates a new LogData object that stores the
 * information. It also sends this object for serializatoin in intent
 * so that LogApplicatoin can do that job when it finishes().
 * Uses addentry.xml for layout and widgets.
 */

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class LogEntryActivity extends Activity {
    /** Called when the activity is first created. */
	
	private EditText dateText;
	private EditText stationText;
	private EditText fuelgText;
	private EditText fuelaText;
	private EditText fuelcText;
	private EditText fuelucText;
	private EditText tripdText;
	private Button confirm;
	
	public static String LOG = "log";


	
	@Override
    protected void onCreate(Bundle savedInstanceState) {
		
		 super.onCreate(savedInstanceState);
	     setContentView(R.layout.addentry);
	
	     dateText = (EditText) findViewById(R.id.date);
	     stationText = (EditText) findViewById(R.id.station);
	     fuelgText = (EditText) findViewById(R.id.fuelg);
	     fuelaText = (EditText) findViewById(R.id.fuela);
	     fuelcText = (EditText) findViewById(R.id.fuelc);
	     fuelucText = (EditText) findViewById(R.id.fueluc);
	     tripdText = (EditText) findViewById(R.id.tripd);
	
	     confirm = (Button) findViewById(R.id.confirm);
	
	           
	     confirm.setOnClickListener(new View.OnClickListener() {

	        public void onClick(View view) {
	        	
	        	Bundle bundle = new Bundle();
	        	
	        	LogData newentry = new LogData(dateText.getText().toString(),
	        			stationText.getText().toString(),
	        			fuelgText.getText().toString(), fuelaText.getText().toString(),
	        			fuelcText.getText().toString(),fuelucText.getText().toString(),
	        			tripdText.getText().toString());
	            
	        	bundle.putSerializable(LOG, newentry);
	        	
	            Intent intent = new Intent();
	            intent.putExtras(bundle);
	            setResult(RESULT_OK, intent);
	            finish();
	        }

	     });
	}
	
}