 package moteur;


import java.util.concurrent.TimeUnit;


public class Clock  {		//creation of the clock
	private boolean started;
	private int hour;
	private int minute;
	private int day;
	private int month;
	private int year;
	private int dayCpt;
	private int dayPos;	
	public Clock(){
		hour = 16;
		minute = 00;
		day = 04;
		month = 01;
		year = 2019;
		dayCpt = 0;
		dayPos = 1;
	}
	
	public Clock(int hH, int mMin, int dD, int mMo, int yYYY, int dC, int dN) {
		super();
		hour = hH;
		this.minute = mMin;
		day = dD;
		month = mMo;
		year = yYYY;
		dayCpt = dC;
		dayPos = dN;
	}
	
	public String getHour() {		//getters ->
		if (hour<10)
			return "" + 0 + hour;
		else{
			return "" + hour;
		}
	}

	public String getMinute() {
		if (minute<10)
			return "" + 0 + minute;
		else{
			return "" + minute;
		}
	}
 
	public String getDay() {
		if (day<10)
			return "" + 0 + day;
		else{
			return "" + day;
		}
	}


	public String getMonth() {
		if (month<10)
			return "" + 0 + month;
		else{
			return "" + month;
		}
	}
	
	public int getMonthPos() {
		return month;
	}

	public int getYear() {		//<-
		return year;
	}
	
	public int getDayCpt() {
		return dayCpt;
	}
	
	public int getDayPos() {
		return dayPos;
	}
	
	public void start(){		//start the clock
		started  = true;
		while (started==true){
			this.increment();
		}
	}
	
	public String calculDayName(int a) { //calcul day name
		int num = a;
		String name = null;
		switch (num) {
			case 1: name = "Monday";
					break;
			case 2: name = "Tuesday";
					break;
			case 3: name = "Wednesday";
					break;
			case 4: name = "Thursday";
					break;
			case 5: name = "Friday";
					break;
			case 6: name = "Saturday";
					break;
			case 7: name = "Sunday";
					break;
			default: name = "Invalid day";
            break;
		}
		return name;
	}
	
	public String getDayName() {
		String todayName = calculDayName(getDayPos());
		return todayName;
	}
	
	public String calculMonthName(int a) { //calcul day name
		int month = a;
		String monthString = null;
        switch (month) {
            case 1:  monthString = "January";
                     break;
            case 2:  monthString = "February";
                     break;
            case 3:  monthString = "March";
                     break;
            case 4:  monthString = "April";
                     break;
            case 5:  monthString = "May";
                     break;
            case 6:  monthString = "June";
                     break;
            case 7:  monthString = "July";
                     break;
            case 8:  monthString = "August";
                     break;
            case 9:  monthString = "September";
                     break;
            case 10: monthString = "October";
                     break;
            case 11: monthString = "November";
                     break;
            case 12: monthString = "December";
                     break;
            default: monthString = "Invalid month";
                     break;
        }
		return monthString;
	}
	
	public String getMonthName() {
		String todayMonth = calculMonthName(getMonthPos());
		return todayMonth;
	}
	private boolean isWeekend(int a) {
		int today = a;
		if(today == 6 || today == 7) {
			return true;
		}
		else {
			return false;
		}
	}
	
	public String getWeekend() {
		if (isWeekend(getDayPos())) {
			return "Yes";
		}
		else {
			return "No";
		}
	}
	
	public void increment(){	//increment the clock by 1 minute
		if (minute + 1 == 60){
			minute = 0;
			if(hour + 1 == 24){
				hour = 0;
				if((day + 1 == 29 && isFev()) || (day + 1 == 31 && is30()) || (day + 1 == 32 && is31()) ){
					day = 01;
					if(month + 1 == 13){
						month = 01;
						year += 1;
					}
					else{
						month += 1;
					}
				}
				else{
					day += 1;
					dayCpt++;
					if (dayPos < 7) {
						dayPos++;
					}
					else {
						dayPos = 1;
					}
				}
			}
			else{
				hour += 1;
			}
		}
		else{
			minute += 1;
		}
	}
	
	private boolean is30() {		//return true if the current month has 30 days
		if(month==04||month==06||month==9||month==11){
			return true;
		}else{
			return false;			
		}
	}


	private boolean isFev() {		//return true if the current month is february
		if(month == 02){
			return true;
		}else{
			return false;			
		}
	}


	private boolean is31() {		//return true if the current month has 31 days
		if(month==01||month==03||month==05||month==07||month==8||month==10||month==11||month==12){
			return true;
		}else{
			return false;			
		}
	}

	public String toString() {
		return "HOUR : " + getHour() + ":" + getMinute() + " ; DATE : " + getDay() + "/" + getMonth() + "/" + getYear() + " ; Day gone : " + getDayCpt() + " ; Today is : " + getDayName() + " ; Month : " + getMonthName() +" ; Week end : " + getWeekend();
	}
	
	public String displayDate() {
		return "DATE : " + getDay() + "/" + getMonth() + "/" + getYear();
	}
	
	public String displayTime() {
		return "HOUR : " + getHour() + ":" + getMinute();
	}
	
	public String displayDayCpt() {
		return "Day : " + getDayCpt();
	}
	public static void main(String[] args){ //Test 
		Clock clock = new Clock();
		
		boolean bool = true;
		while (bool!=false){
			try {
				Thread.sleep(5);
			} catch (InterruptedException e) {
				System.out.println(e.getMessage());
			}
			clock.increment();
			System.out.println(clock.toString());
		}	
	}
	
}