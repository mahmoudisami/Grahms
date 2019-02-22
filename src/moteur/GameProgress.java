package moteur;

import data.*;
import ihm.*;
import moteur.*;

public class GameProgress {
	
	private static Clock clock;
	private static Money money;
	private static FinancialCalculator fin = new FinancialCalculator();
	private static int tmpMoney;
	private static String historicText ="";
	
	public GameProgress(Clock clock, Money money) {
		this.clock = clock;
		this.money = money;
	}
	
	public void launchGameProgress (Grid grid) {
		if(clock.getDayPos()==7 && clock.getHour().equals("23")) { //Chaque dimanche a 23h
			tmpMoney = fin.districtCalculatorCost(grid.getMapTab(), grid.getMapTab().length);
			money.withDraw(tmpMoney);
			historicText += "------------ "+ clock.getDay() +" "+ clock.getMonthName() +"------------\n";
			historicText += "-" + tmpMoney + " Entretien des quartiers\n";
			tmpMoney = fin.districtCalculatorGain(grid.getMapTab(), grid.getMapTab().length);
			money.addMoney(tmpMoney);
			historicText += "+" + tmpMoney + " Gain des quartiers \n";
			tmpMoney = fin.stationCalculatorCost(grid.getMapTab(), grid.getMapTab().length);
			money.withDraw(tmpMoney);
			historicText += "-" + tmpMoney + " Entretien des stations \n";
		}
		if(clock.getHour().equals("07")) {
			//APPEL fonction habitants parte au travail
		}
	}
	
	public String getHistoricText() {
		return historicText;
	}
}

