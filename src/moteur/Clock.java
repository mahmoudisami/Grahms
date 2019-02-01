 package moteur;


import java.util.concurrent.TimeUnit;


public class Clock  {		//creation of the clock
	private boolean started;
	private int hour;
	private int minute;
	private int day;
	private int month;
	private int year;
	
	
	public Clock(){
		hour = 16;
		minute = 00;
		day = 04;
		month = 01;
		year = 2019;
	}
	
	public Clock(int hH, int mMin, int dD, int mMo, int yYYY) {
		super();
		hour = hH;
		this.minute = mMin;
		day = dD;
		month = mMo;
		year = yYYY;
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


	public int getYear() {		//<-
		return year;
	}


	public void start(){		//start the clock
		started  = true;
		while (started==true){
			this.increment();
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
		return "HOUR : " + getHour() + ":" + getMinute() + " ; DATE : " + getDay() + "/" + getMonth() + "/" + getYear();
	}
	
	public String displayDate() {
		return "DATE : " + getDay() + "/" + getMonth() + "/" + getYear();
	}
	
	public String displayTime() {
		return "HOUR : " + getHour() + ":" + getMinute();
	}
	
	public static void main(String[] args){ //Test 
		Clock clock = new Clock();
		
		boolean bool = true;
		while (bool!=false){
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				System.out.println(e.getMessage());
			}
			clock.increment();
			System.out.println(clock.toString());
		}	
	}
	
}