package com.log;

/*LogStatActivity is called when user wants to view statistics from the
 * information he has stored for multiple trips and multiple storage of
 * logs in the application. This activity basically shows the statistics
 * but is not involved in the calculation of the statistics. The calculation
 * has been already done by LogApplicatoin before this activity is called.
 * However there is one issue with this activity, that the mainmenu button
 * requires two clicks before it returns to the LogApplicatoin. Although
 * the click listeners have been used the same way in other activities, this
 * particular activity requires two button clicks, which is unusual.
 * Uses statview.xml for layouts and widgets.
 */

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class LogStatActivity extends Activity {
	
	private TextView totalcost;
	private TextView totaldistance;
	private TextView fuelcrate;
	
	private Button mainmenu;
	
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.statview);
        
        mainmenu  = (Button) findViewById(R.id.mainmenu);
        
        totalcost = (TextView) findViewById(R.id.totalcostview);
        totaldistance = (TextView) findViewById(R.id.totaldistanceview);
        fuelcrate = (TextView) findViewById(R.id.fuelconsumptionrate);

        
        String tc = Double.toString(getIntent().getDoubleExtra(LogApplication.TOTAL_C, 0));
        String td = Double.toString(getIntent().getDoubleExtra(LogApplication.TOTAL_D, 0));
        String fcr = Double.toString(getIntent().getDoubleExtra(LogApplication.FUEL_C_RATE, 0));
        
        totalcost.setText(tc);
        totaldistance.setText(td);
        fuelcrate.setText(fcr);
        
        mainmenu.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
            	
            	Bundle bundle = new Bundle();
                
            	Intent mIntent = new Intent();
                mIntent.putExtras(bundle);
                setResult(RESULT_OK, mIntent);
                finish();
           }

        });
        
                
	}
		
	
}
