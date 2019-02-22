package moteur;

import data.*;
import ihm.*;
import moteur.*;

public class GameProgress {
	
	// private long speed = 1000; � mettre dans gameScreen
	private static Clock clock;
	private static FinancialCalculator fin = new FinancialCalculator();
	private static int tmpMoney;
	
	public void launchGameProgress (Clock clock, Grid grid) {
		if(clock.getDayPos()==7 && clock.getHour().equals("23")) { //exemple
			tmpMoney = fin.districtCalculatorCost(grid.getMapTab(), grid.getMapTab().length);
			System.out.println(tmpMoney);
		}
		if(clock.getHour().equals("07")) {
			//APPEL fonction habitants parte au travail
		}
	}
}

