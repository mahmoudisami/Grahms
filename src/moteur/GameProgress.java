package moteur;

import data.*;
import ihm.*;
import moteur.*;

public class GameProgress {
	
	// private long speed = 1000; � mettre dans gameScreen
	private static Clock clock;
	private static FinancialCalculator fin = new FinancialCalculator();
	private static int tmpMoney;
	private static String historicText ="";
	
	public void launchGameProgress (Clock clock, Grid grid, Money money) {
		if(clock.getDayPos()==7 && clock.getHour().equals("23")) { //exemple
			tmpMoney = fin.districtCalculatorCost(grid.getMapTab(), grid.getMapTab().length);
			money.withDraw(tmpMoney);
			historicText += "------------ "+ clock.getDay() +" "+ clock.getMonthName() +"------------\n";
			historicText += "-" + tmpMoney + " Entretien des quartiers\n";
			tmpMoney = fin.districtCalculatorGain(grid.getMapTab(), grid.getMapTab().length);
			money.addMoney(tmpMoney);
			historicText += "+" + tmpMoney + " Gain des quartiers \n";
		}
		if(clock.getHour().equals("07")) {
			//APPEL fonction habitants parte au travail
		}
	}
	
	public String getHistoricText() {
		return historicText;
	}
}

