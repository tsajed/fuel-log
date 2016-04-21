package com.log;

/* LogData Object to store the information in every log entry.
 * It receives information via the constructor in the form of strings
 * It needs to convert some of the strings to double and some of them
 * will stay strings. It needs to implement Serializable since the object
 * will written and read to outputstream in file, and inputstream from file
 * respectively
 */

import java.io.Serializable;
import java.text.DecimalFormat;

public class LogData implements Serializable {


	private static final long serialVersionUID = 5683196535238820114L;
	
		public String date;
		public String station;
		public String fuel_g;
		public double fuel_a;
		public double fuel_c;
		public double fuel_uc;
		public double trip_d;
		
		public LogData(String d, String s, String fg,
				String fa, String fc, String fuc, String td) {
			
			this.date = d;
			this.station = s;
			this.fuel_g = fg;
			
			if(fa.contentEquals("")) {
				this.fuel_a = 0;
			}
			
			else {
				this.fuel_a = Double.parseDouble(fa);
			}
			
			if (fc.contentEquals("")) {
				this.fuel_c = 0;
			}
			
			else {
				this.fuel_c = Double.parseDouble(fc);
			}
			
			if (fuc.contentEquals("")) {
				this.fuel_uc = 0;
			}
			
			else {
				this.fuel_uc = Double.parseDouble(fuc);
			}
			
			if (td.contentEquals("")) {
				this.trip_d = 0;
			}
			
			else {
				this.trip_d = Double.parseDouble(td);
			}
			
			
			
		}
}
