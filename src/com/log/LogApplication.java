package com.log;


/* Main Activity of my application-> LogApplication.class
 * It only ends when the application ends. It is responsible for starting
 * other activities upon user interactions with buttons. The public static
 * variables are required in order to throw data in intents and bundles
 * to other activities for operations. LogApplication is an extension
 * of ListActivity with a ListView in the heart of the interface that
 * shows all the available log entries created by the user. Two buttons
 * one of them lead to showing statistics, started by LogStatActivity,
 * as required by application, the  other leads to creation of new log entries
 * by calling LogEntryActivity through intent. Clicking on list items
 * will allow the user to view existing log entries by starting 
 * LogViewActivity. The user can remove the clicked item in that activity.
 * The Application uses Deserialization and serialization of LogData objects
 * to store and retrieve the essential data in "listObject.sav" file stored in memory
 * that is loaded and updated after user interaction with the applicatoin.
 * "listString.sav" stores what needs to be shown on the lists for users
 * to click on them. It shows the dates of each log entry.
 */


import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;


public class LogApplication extends ListActivity {

	public static final String listString = "listString.sav";
	public static final String listObject = "listObject.sav";
	public static final int LOG_CREATE = 0;
	public static final int LOG_VIEW = 1;
	public static final int STAT_VIEW = 2;
	public static String POSITION = "position";
	public int numberObjects = 1;
	
	public static String DATE = "date";
	public static String STATION = "station";
	public static String FUEL_G  ="fuel_g";
	public static String FUEL_A  =  "fuel_a";
	public static String FUEL_C  = "fuel_c";
	public static String FUEL_UC = "fuel_uc";
	public static String TRIP_D  = "trip_d";
	public static String TOTAL_D = "totaldistance";
	public static String TOTAL_C = "totalcost";
	public static String FUEL_C_RATE = "fuelconsumptionrate";
	
	private Button entryButton;
	private Button statButton;
	
	
	 /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.listview);
        
        entryButton = (Button) findViewById(R.id.newentry);
        statButton = (Button) findViewById(R.id.statistics);
        
        entryButton.setOnClickListener(new View.OnClickListener() {

			public void onClick(View v) {

				createLog();
			}
		});
        
        statButton.setOnClickListener(new View.OnClickListener() {

			public void onClick(View v) {

				viewStat();
			}
		});

        createFiles();
        numberObjects = fill_list();

    }
    
    //CreateLog starts LogEntryActivity and creates a new LogData object
    //at the end of the process.
    
    public void createLog() {
    	
    	Intent i = new Intent(this, LogEntryActivity.class);
    	
    	startActivityForResult(i, LOG_CREATE);
    }
    
    //Called whenever View Statistics button is pushed. It calculates
    //through each LogData object in ArrayList after deserializing it
    //from object input stream
    
    public void viewStat() {
    	
    	ArrayList<LogData> loglist = new ArrayList<LogData>();
    	Intent i = new Intent(this, LogStatActivity.class);
    	
    	double totalDistance = 0;
    	double totalCost = 0;
    	double totalAmount = 0; 
    	double fuelCRate  = 0;
    	
    	try {
    		FileInputStream fin = openFileInput(listObject);
    		ObjectInputStream ois = new ObjectInputStream(fin);
    		
    		loglist = (ArrayList<LogData>) ois.readObject();
	    	
    		if(!loglist.isEmpty()) {
    			
    			for(int a = 0; a <loglist.size(); a++) {
    				
    				totalDistance = totalDistance + loglist.get(a).trip_d;
    				
    				totalCost = totalCost + loglist.get(a).fuel_c;
    				
    				totalAmount = totalAmount + loglist.get(a).fuel_a;
    			}
    			
    			if (totalDistance != 0) {
    				fuelCRate = (totalAmount/totalDistance)*100;
    			}
    		}
    		
    		i.putExtra(TOTAL_D, totalDistance);
    		i.putExtra(TOTAL_C, totalCost);
    		i.putExtra(FUEL_C_RATE, fuelCRate);
    		
	    	ois.close();
	    	
	    	startActivityForResult(i, STAT_VIEW);
		
    	}
    	
    	catch(Exception e) { 
    		System.out.println("Exception during deserialization: " + 
    		e); 
    		System.exit(0); 
    		} 
    	
        startActivityForResult(i, STAT_VIEW);
    }
    
    //fill_list called whenever other activity finishes and LogApplication
    //activity is resumed and the list is updated with all the entries
        
    public int fill_list() {
    	
    	ArrayList<String> logs = new ArrayList<String>();
    	int a = 1;
    	
		try {
			FileInputStream fis = openFileInput(listString);
			BufferedReader in = new BufferedReader(new InputStreamReader(fis));
			String line = in.readLine();
			while (line != null) {
				
				logs.add(line);
				line = in.readLine();
				a++;
			}

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
				R.layout.listitem, logs);
		setListAdapter(adapter);
		
		return a;
        
    }
    
    //When user clicks on list, LogViewActivity is called with intent
    //that contains information about the LogData object the user has
    //selected
    
    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        
        ArrayList<LogData> loglist = null;
        LogData logEntry;
        
        Intent i = new Intent(this, LogViewActivity.class);
        
    	try {
    		FileInputStream fin = openFileInput(listObject);
    		ObjectInputStream ois = new ObjectInputStream(fin);
    		
	    		loglist = (ArrayList<LogData>) ois.readObject();
	    		logEntry = loglist.get(position);
	    		
	    		i.putExtra(DATE, logEntry.date);
	    		i.putExtra(STATION, logEntry.station);
	    		i.putExtra(FUEL_G, logEntry.fuel_g);
	    		i.putExtra(FUEL_A, logEntry.fuel_a);
	    		i.putExtra(FUEL_C, logEntry.fuel_c);
	    		i.putExtra(FUEL_UC, logEntry.fuel_uc);
	    		i.putExtra(TRIP_D, logEntry.trip_d);
	    		i.putExtra(POSITION, position);
	    		
	    		ois.close();
		
    	}
    	
    	catch(Exception e) { 
    		System.out.println("Exception during deserialization: " + 
    		e); 
    		System.exit(0); 
    		} 
        
        startActivityForResult(i, LOG_VIEW);
    }
    

    //Other activites are finished, this method is called 
    
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        Bundle extras = intent.getExtras();
        switch(requestCode) {
            case LOG_CREATE:
  
            	LogData newentry = (LogData) extras.get(LogEntryActivity.LOG);
            	saveObject(newentry);
            	
                numberObjects = fill_list();
                break;
                
            case LOG_VIEW:
            	
            	if(extras.getInt(LogViewActivity.KEY)==1) {
            		
            		int position = extras.getInt(POSITION);
            		removeObject(position);
            	}
	            numberObjects = fill_list();
            	
                break;
               
            case STAT_VIEW:
            	
            	onPause();
            	numberObjects = fill_list();
            	break;
            	
        }
    }
    
    //saveObject after a log has been created, and the LogData object
    //containing the information will be added to ArrayList of objects
    //and the whole arraylist of objects will be serialized to output stream
    
    public void saveObject(LogData newentry) {
    	
    	ArrayList<LogData> loglist = null;
    	
    	if (numberObjects != 1) {
    	
	    	try {
	    		FileInputStream fin = openFileInput(listObject);
	    		ObjectInputStream ois = new ObjectInputStream(fin);
	    		
	    		
		    		loglist = (ArrayList<LogData>) ois.readObject();
		    		loglist.add(newentry);
		    		ois.close();
    		
	    	}
	    	
	    	catch(Exception e) { 
	    		System.out.println("Exception during deserialization: " + 
	    		e); 
	    		System.exit(0); 
	    		} 
    	}
	    	
    	else {
    			loglist = new ArrayList<LogData>();
    			loglist.add(newentry);
    		}
    	
    	
    	
    	
    	try {
	    	FileOutputStream fos = openFileOutput(listObject, Context.MODE_WORLD_WRITEABLE);
	    	ObjectOutputStream oos = new ObjectOutputStream(fos);
	    	
	    	oos.writeObject(loglist);
	    	oos.flush();
	    	oos.close();
    	}
    	
    	catch(Exception e) { 
    		System.out.println("Exception during serialization: " + e); 
    		System.exit(0); 
    		} 
    	
    	try {
    		FileOutputStream fos1 = openFileOutput(listString, Context.MODE_APPEND);
    		fos1.write(new String(newentry.date +" 		| 		Log Entry \n").getBytes());
    		fos1.close(); 		
    	}
    	
    	catch(Exception e) { 
    		System.out.println("Exception during serialization: " + e); 
    		System.exit(0); 
    		} 
    }
    
    //removeObject removes the selected list that the user wishes to remove.
    //He needs to view the entry first and then select remove in LogViewAcitivty
    //Then this activity is resumed and removeObject removes an entry from
    //the listStrings, and listObjects according to the position of the entry
    //in the list.

    public void removeObject(int pos) {
    	
    	ArrayList<LogData> loglist = null;
    	ArrayList<String> logs = new ArrayList<String>();
    	
    	try {
    		FileInputStream fin = openFileInput(listObject);
    		ObjectInputStream ois = new ObjectInputStream(fin);
    		
	    		loglist = (ArrayList<LogData>) ois.readObject();
	    		loglist.remove(pos);
	   
	    		ois.close();
		
    	}
    	
    	catch(Exception e) { 
    		System.out.println("Exception during deserialization: " + 
    		e); 
    		System.exit(0); 
    		} 
    	
    	try {
	    	FileOutputStream fos = openFileOutput(listObject, Context.MODE_WORLD_WRITEABLE);
	    	ObjectOutputStream oos = new ObjectOutputStream(fos);
	    	
	    	oos.writeObject(loglist);
	    	oos.flush();
	    	oos.close();
    	}
    	
    	catch(Exception e) { 
    		System.out.println("Exception during serialization: " + e); 
    		System.exit(0); 
    		} 
    	
    	
    	try {
			FileInputStream fis = openFileInput(listString);
			BufferedReader in = new BufferedReader(new InputStreamReader(fis));
			String line = in.readLine();
			while (line != null) {
				
				logs.add(line);
				line = in.readLine();

			}

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    	logs.remove(pos);
    	int a = 0;
    	
    	try {
    		FileOutputStream fos1 = openFileOutput(listString, Context.MODE_WORLD_WRITEABLE);
    		
    		while(a < logs.size()) {
    			
    			fos1.write(new String(logs.get(a) + "\n").getBytes());
    			a++;
    		}
    		fos1.close(); 		
    	}
    	
    	catch(Exception e) { 
    		System.out.println("Exception during seria: " + e); 
    		System.exit(0); 
    		} 
    	
    }

    //method to create files in memory space when the application first installed
    
    public void createFiles() {
    	
    	try {
    		FileOutputStream fos = openFileOutput(listString, Context.MODE_APPEND);
    		FileOutputStream fos1 = openFileOutput(listObject, Context.MODE_APPEND);
    		
    		fos1.close();
    		fos.close();
    	}
    	
    	catch(Exception e) { 
    		System.out.println("Exception during creation: " + e); 
    		System.exit(0); 
    		} 
    	
    }
}
