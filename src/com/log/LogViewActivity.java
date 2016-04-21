package com.log;

/*When the user clicks on one of the items in the main list in LogApplication,
 * LogViewActivity is called. According to the position of the item in list,
 * LogData object is found out in the ArrayList of LogData objects, and 
 * information from LogData object is passed through intent and bundles
 * to this activity for showing in the TextViews that this activity has.
 * The Remove button adds the functionality that the user can decide
 * to remove the selected log entry if he wants to. Clicking the button
 * will only end this activity and resume LogApplicaton activity but
 * through the use of intent, it will be informed that a particular
 * LogData object needs to be removed
 * Uses viewentry.xml for layout and widgets
 */

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;



public class LogViewActivity extends Activity {

	private TextView dateView;
	private TextView stationView;
	private TextView fuelgView;
	private TextView fuelaView;
	private TextView fuelcView;
	private TextView fuelucView;
	private TextView tripdView;
	
	private Button back;
	private Button remove;
	
	public static String KEY = "key";
	public int position;
	
	

	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.viewentry);

        dateView = (TextView) findViewById(R.id.dateView);
        stationView = (TextView) findViewById(R.id.stationView);
        fuelgView = (TextView) findViewById(R.id.fuelgView);
        fuelaView= (TextView) findViewById(R.id.fuelaView);
        fuelcView = (TextView) findViewById(R.id.fuelcView);
        fuelucView = (TextView) findViewById(R.id.fuelucView);
        tripdView = (TextView) findViewById(R.id.tripdView);
        
        back = (Button) findViewById(R.id.back);
        remove = (Button) findViewById(R.id.remove);
        
        Bundle b = getIntent().getExtras();
        
        String a = Double.toString(b.getDouble(LogApplication.FUEL_A));
        String e = Double.toString(b.getDouble(LogApplication.FUEL_C));
        String c = Double.toString(b.getDouble(LogApplication.FUEL_UC));
        String d = Double.toString(b.getDouble(LogApplication.TRIP_D));
        
        dateView.setText(b.getCharSequence(LogApplication.DATE));
        stationView.setText(b.getCharSequence(LogApplication.STATION));
        fuelgView.setText(b.getCharSequence(LogApplication.FUEL_G));
        fuelaView.setText(a);
        fuelcView.setText(e);
        fuelucView.setText(c);
        tripdView.setText(d);
        position =  b.getInt(LogApplication.POSITION, 10);
        
        
        back.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                Bundle bundle = new Bundle();

                bundle.putInt(KEY, 0);
                
                Intent mIntent = new Intent();
                mIntent.putExtras(bundle);
                setResult(RESULT_OK, mIntent);
                finish();
            }

        });
        
        remove.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                Bundle bundle = new Bundle();

                bundle.putInt(KEY, 1);
                bundle.putInt(LogApplication.POSITION, position);
                
                Intent mIntent = new Intent();
                mIntent.putExtras(bundle);
                setResult(RESULT_OK, mIntent);
                finish();
            }

        });
        
	}
        
}
